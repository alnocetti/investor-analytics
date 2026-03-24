package com.test.investor_analytics.graphql.dto.input;

import com.test.investor_analytics.graphql.dto.common.SortInput;
import lombok.Data;

@Data
public class GetInvestorAnalyticsInput {
    private PaginationInput pagination;
    private DealFilterInput filter;
    private SortInput sort;
}
