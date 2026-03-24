package com.test.investor_analytics.graphql.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortInput {
    private String field;
    private SortDirection direction;
}


