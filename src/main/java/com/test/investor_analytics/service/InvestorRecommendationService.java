package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.InvestorEmbedding;
import com.test.investor_analytics.repository.InvestorEmbeddingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestorRecommendationService {

    @Autowired
    private InvestorEmbeddingRepository investorEmbeddingRepository;

    public List<InvestorEmbedding> recommend(String region, String sector, int limit) {
        log.info("Recommend request → region={} sector={} limit={}", region, sector, limit);

        List<Double> queryVector = buildQuery(region, sector);
        log.info("Query vector size: {}", queryVector.size());

        List<InvestorEmbedding> result = investorEmbeddingRepository.vectorSearch(queryVector, limit);
        log.info("Recommendations found: {}", result.size());

        return result;
    }

    private List<Double> buildQuery(String region, String sector) {

        return normalize(List.of(
                region.equals("North America") ? 1.0 : 0.0,
                region.equals("Europe") ? 1.0 : 0.0,
                region.equals("South America") ? 1.0 : 0.0,

                sector.equals("Energy") ? 1.0 : 0.0,
                sector.equals("Technology") ? 1.0 : 0.0,
                sector.equals("Finance") ? 1.0 : 0.0,
                sector.equals("Healthcare") ? 1.0 : 0.0
        ));
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