package com.test.investor_analytics.repository.investor;

import com.test.investor_analytics.entity.Investor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestorMongoRepository extends MongoRepository<Investor, String>, InvestorRepositoryCustom {
}
