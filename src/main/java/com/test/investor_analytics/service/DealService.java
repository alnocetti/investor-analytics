package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.Deal;
import com.test.investor_analytics.repository.DealMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DealService {

    @Autowired
    private DealMongoRepository dealMongoRepository;

    public Deal getDealById(String dealId){
        return dealMongoRepository.findById(dealId).orElse(null);
    }

    public List<Deal> getAllDeals(){
        return dealMongoRepository.findAll();
    }
}
