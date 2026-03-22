package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.Issuer;
import com.test.investor_analytics.repository.IssuerMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssuerService {

    @Autowired
    private IssuerMongoRepository issuerMongoRepository;

    public List<Issuer> getAllIssuers() {
        return issuerMongoRepository.findAll();
    }
}
