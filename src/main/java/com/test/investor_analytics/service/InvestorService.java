package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.Investor;
import com.test.investor_analytics.repository.InvestorMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvestorService {

    private InvestorMongoRepository investorMongoRepository;

    public List<Investor> getAllInvestors() {
        List<Investor> investors = investorMongoRepository.findAll();
        System.out.println("getAllInvestors(): " + investors);
        return investorMongoRepository.findAll();
    }
}
