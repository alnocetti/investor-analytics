package com.test.investor_analytics.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestorDealAnalytic {

    private InvestorEmbedded investor;

    private Float allocation;
    private Float demand;
    private Integer allocationRank;
    private Integer demandRank;
}
