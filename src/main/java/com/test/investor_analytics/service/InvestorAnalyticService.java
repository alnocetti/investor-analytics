package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.InvestorAnalytic;
import com.test.investor_analytics.repository.InvestorAnalyticMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvestorAnalyticService {

    @Autowired
    private InvestorAnalyticMongoRepository investorAnalyticMongoRepository;

    public List<InvestorAnalytic> getInvestorAnalytics() {
        return investorAnalyticMongoRepository.getInvestorAnalytics();
    }
}
