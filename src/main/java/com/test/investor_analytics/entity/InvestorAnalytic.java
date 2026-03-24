package com.test.investor_analytics.entity;

import com.test.investor_analytics.entity.embedded.InvestorEmbedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestorAnalytic {
    private InvestorEmbedded investor;
    private Integer participation;
    private Float totalAllocation;
    private Float totalDemand;
    private Float averageAllocationRank;
    private Float averageDemandRank;
}
