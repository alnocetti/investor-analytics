package com.test.investor_analytics.graphql.dto;

import lombok.Data;

@Data
public class InvestorDealAnalyticDTO {
    private InvestorDTO investor;

    private Float allocation;
    private Float demand;
    private Integer allocationRank;
    private Integer demandRank;
}
