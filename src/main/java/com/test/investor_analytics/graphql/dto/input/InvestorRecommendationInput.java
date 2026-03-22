package com.test.investor_analytics.graphql.dto.input;

import lombok.Data;

@Data
public class InvestorRecommendationInput {
    private String sector;
    private String region;
    private Integer limit;
}
