package com.test.investor_analytics.graphql.dto.input;

import lombok.Data;

@Data
public class DealFilterInput {
    private String sector;
    private String subSector;
    private String region;
    private String country;
    private String type;
    private DateRangeInput pricingDateRange;
    private String investorId;
}
