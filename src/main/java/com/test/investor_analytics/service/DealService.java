package com.test.investor_analytics.service;

import com.test.investor_analytics.entity.Deal;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.common.SortInput;
import com.test.investor_analytics.graphql.dto.input.DealFilterInput;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.deal.DealMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DealService {

    @Autowired
    private DealMongoRepository dealMongoRepository;

    public PageData<Deal> getAllDeals(PaginationInput pagination, DealFilterInput filter, SortInput sort) {
        return dealMongoRepository.find(filter, pagination, sort);
    }
}
