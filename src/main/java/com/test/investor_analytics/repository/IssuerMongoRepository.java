package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.Issuer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssuerMongoRepository extends MongoRepository<Issuer, String>, IssuerRepositoryCustom {
}
