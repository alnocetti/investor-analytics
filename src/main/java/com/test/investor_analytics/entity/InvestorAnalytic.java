package com.test.investor_analytics.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestorAnalytic {
    private InvestorEmbedded investor;
    private Float totalAllocation;
    private Float totalDemand;
    private Float averageAllocationRank;
    private Float averageDemandRank;
}
