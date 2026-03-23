package com.test.investor_analytics.graphql.dto.input;

import lombok.Data;

@Data
public class GetDealsInput {
    private PaginationInput pagination;
    private DealFilterInput filter;
}
