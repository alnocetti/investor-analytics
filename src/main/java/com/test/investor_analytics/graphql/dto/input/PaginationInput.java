package com.test.investor_analytics.graphql.dto.input;

import lombok.Data;

@Data
public class PaginationInput {
    private int page;
    private int size;
}
