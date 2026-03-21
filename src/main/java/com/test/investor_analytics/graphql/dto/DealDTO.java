package com.test.investor_analytics.graphql.dto;

import lombok.Data;

import java.util.List;

@Data
public class DealDTO {
    private String id;
    private String pricingDate;
    private String type;
    private String sector;
    private String subSector;
    private String country;
    private String region;
    private Float size;
    private Float totalAllocation;
    private Float totalDemand;
    private IssuerDTO issuer;
    private List<InvestorDealAnalyticDTO> investorDealAnalytics;
}
