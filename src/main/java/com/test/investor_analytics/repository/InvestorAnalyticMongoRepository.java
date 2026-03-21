package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.InvestorAnalytic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestorAnalyticMongoRepository extends MongoRepository<InvestorAnalytic, String>, InvestorAnalyticCustomRepository {

}
