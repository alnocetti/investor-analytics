package com.test.investor_analytics.repository.investor.embeddings;

import com.test.investor_analytics.entity.InvestorEmbedding;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestorEmbeddingRepository
        extends MongoRepository<InvestorEmbedding, String>,
        InvestorEmbeddingCustomRepository{
}