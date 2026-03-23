package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.Investor;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.investor.InvestorMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvestorService {

    private InvestorMongoRepository investorMongoRepository;

    public PageData<Investor> getAllInvestors(PaginationInput paginationInput) {
        return investorMongoRepository.find(paginationInput);
    }
}
