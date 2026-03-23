package com.test.investor_analytics.repository.investor;

import com.test.investor_analytics.entity.Investor;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;

public interface InvestorRepositoryCustom {
    PageData<Investor> find(PaginationInput paginationInput);
}
