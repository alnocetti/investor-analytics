package com.test.investor_analytics.graphql.resolver;

import com.test.investor_analytics.graphql.dto.DealDTO;
import com.test.investor_analytics.graphql.dto.InvestorAnalyticDTO;
import com.test.investor_analytics.graphql.mapper.DealMapper;
import com.test.investor_analytics.graphql.dto.InvestorDTO;
import com.test.investor_analytics.graphql.mapper.InvestorAnalyticMapper;
import com.test.investor_analytics.graphql.mapper.InvestorMapper;
import com.test.investor_analytics.service.DealService;
import com.test.investor_analytics.service.InvestorAnalyticService;
import com.test.investor_analytics.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class QueryResolver {

    @Autowired
    private DealService dealService;

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private InvestorService investorService;

    @Autowired
    private InvestorMapper investorMapper;

    @Autowired
    private InvestorAnalyticService investorAnalyticService;

    @Autowired
    private InvestorAnalyticMapper investorAnalyticMapper;

    @QueryMapping
    public DealDTO getDeal(String id) {
        return dealMapper.toDto(dealService.getDealById(id));
    }

    @QueryMapping
    public List<DealDTO> getDeals() {
        return dealService.getAllDeals().stream().map(dealMapper::toDto).toList();
    }

    @QueryMapping
    public List<InvestorDTO> getInvestors() {
        return investorService.getAllInvestors().stream().map(investorMapper::toDto).toList();
    }

    @QueryMapping
    public List<InvestorAnalyticDTO> getInvestorAnalytics() {
        return investorAnalyticService.getInvestorAnalytics().stream().map(investorAnalyticMapper::toDto).toList();
    }
}
