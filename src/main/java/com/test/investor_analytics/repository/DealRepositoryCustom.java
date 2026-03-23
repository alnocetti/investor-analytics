package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.Deal;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.DealFilterInput;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;

public interface DealRepositoryCustom {
    PageData<Deal> find(DealFilterInput filter, PaginationInput paginationInput);
}
