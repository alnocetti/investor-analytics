package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.InvestorEmbedding;
import org.bson.Document;

import java.util.List;

public interface InvestorEmbeddingCustomRepository {
    List<InvestorEmbedding> vectorSearch(List<Double> queryVector, int limit);

    void bulkUpsert(List<InvestorEmbedding> embeddings);

    List<Document> aggregateFromDeals();
}
