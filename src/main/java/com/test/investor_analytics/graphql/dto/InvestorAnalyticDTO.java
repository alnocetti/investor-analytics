package com.test.investor_analytics.graphql.dto;

import lombok.Data;

@Data
public class InvestorAnalyticDTO {
    private InvestorDTO investor;
    private Integer participation;
    private Float totalAllocation;
    private Float totalDemand;
    private Float averageAllocationRank;
    private Float averageDemandRank;
}
