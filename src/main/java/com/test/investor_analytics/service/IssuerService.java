package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.Issuer;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.IssuerMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IssuerService {

    @Autowired
    private IssuerMongoRepository issuerMongoRepository;

    public PageData<Issuer> getAllIssuers(PaginationInput pagination) {
        return issuerMongoRepository.find(pagination);
    }
}
