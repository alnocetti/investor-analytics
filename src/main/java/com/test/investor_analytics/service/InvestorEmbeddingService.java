package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.InvestorEmbedding;
import com.test.investor_analytics.repository.InvestorEmbeddingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestorEmbeddingService {

    @Autowired
    private InvestorEmbeddingRepository repository;

    @Value("${app.embeddings.rebuild:false}")
    private boolean rebuild;

    @PostConstruct
    public void init() {
        if (rebuild) {
            log.info("Starting embeddings rebuild...");
            rebuildEmbeddings();
        }
    }

    public void rebuildEmbeddings() {

        List<Document> results = repository.aggregateFromDeals();
        log.info("Aggregation result size: {}", results.size());

        List<InvestorEmbedding> embeddings = results.stream()
                .map(this::toEmbedding)
                .toList();
        log.info("Generated embeddings: {}", embeddings.size());

        repository.bulkUpsert(embeddings);
        log.info("✅ Bulk upsert completed");
    }

    private InvestorEmbedding toEmbedding(Document doc) {

        List<Double> vector = buildVector(doc);

        InvestorEmbedding investorEmbedding = new InvestorEmbedding();
        investorEmbedding.setInvestorId(doc.getObjectId("_id"));
        investorEmbedding.setName(doc.getString("name"));
        investorEmbedding.setType(doc.getString("type"));
        investorEmbedding.setEmbedding(normalize(vector));

        return investorEmbedding;
    }

    private List<Double> buildVector(Document doc) {

        List<String> regions = (List<String>) doc.get("regions");
        List<String> sectors = (List<String>) doc.get("sectors");

        return List.of(
                score(regions, "North America"),
                score(regions, "Europe"),
                score(regions, "South America"),

                score(sectors, "Energy"),
                score(sectors, "Technology"),
                score(sectors, "Finance"),
                score(sectors, "Healthcare")
        );
    }

    private double score(List<String> list, String value) {
        if (list == null || list.isEmpty()) return 0;
        return (double) list.stream()
                .filter(v -> v.equals(value))
                .count() / list.size();
    }

    private List<Double> normalize(List<Double> vector) {

        double norm = Math.sqrt(
                vector.stream()
                        .mapToDouble(x -> x * x)
                        .sum()
        );

        if (norm == 0) return vector;

        return vector.stream()
                .map(x -> x / norm)
                .toList();
    }
}