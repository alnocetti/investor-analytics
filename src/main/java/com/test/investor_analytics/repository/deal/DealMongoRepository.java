package com.test.investor_analytics.repository.deal;

import com.test.investor_analytics.entity.Deal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DealMongoRepository extends MongoRepository<Deal, String>, DealRepositoryCustom {

}
