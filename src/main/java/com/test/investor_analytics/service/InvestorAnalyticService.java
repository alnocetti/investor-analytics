package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.InvestorAnalytic;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.InvestorAnalyticMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvestorAnalyticService {

    @Autowired
    private InvestorAnalyticMongoRepository investorAnalyticMongoRepository;

    public PageData<InvestorAnalytic> getInvestorAnalytics(PaginationInput pagination) {
        return investorAnalyticMongoRepository.getInvestorAnalytics(pagination);
    }
}
