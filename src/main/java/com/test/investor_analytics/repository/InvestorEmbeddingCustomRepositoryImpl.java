package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.InvestorEmbedding;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InvestorEmbeddingCustomRepositoryImpl
        implements InvestorEmbeddingCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<InvestorEmbedding> vectorSearch(List<Double> queryVector, int limit) {

        Document vectorSearch = new Document("$vectorSearch",
                new Document("index", "vector_index")
                        .append("path", "embedding")
                        .append("queryVector", queryVector)
                        .append("numCandidates", 200)
                        .append("limit", limit)
        );

        Aggregation agg = Aggregation.newAggregation(context -> vectorSearch);

        return mongoTemplate.aggregate(
                agg,
                "investor_embeddings",
                InvestorEmbedding.class
        ).getMappedResults();
    }

    @Override
    public List<Document> aggregateFromDeals() {

        Aggregation agg = Aggregation.newAggregation(

                Aggregation.unwind("investorDealAnalytics"),

                Aggregation.group("investorDealAnalytics.investor._id")
                        .first("investorDealAnalytics.investor.name").as("name")
                        .first("investorDealAnalytics.investor.type").as("type")
                        .push("region").as("regions")
                        .push("sector").as("sectors")
        );

        return mongoTemplate.aggregate(
                agg,
                "deals",
                Document.class
        ).getMappedResults();
    }

    @Override
    public void bulkUpsert(List<InvestorEmbedding> embeddings) {

        BulkOperations bulkOps = mongoTemplate.bulkOps(
                BulkOperations.BulkMode.UNORDERED,
                "investor_embeddings"
        );

        for (InvestorEmbedding embedding : embeddings) {

            Query query = new Query(
                    Criteria.where("investorId").is(embedding.getInvestorId())
            );

            Update update = new Update()
                    .set("name", embedding.getName())
                    .set("type", embedding.getType())
                    .set("embedding", embedding.getEmbedding());

            bulkOps.upsert(query, update);
        }

        bulkOps.execute();
    }
}