package com.test.investor_analytics.graphql.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DealFilterInput {
    private String dealId;
    private String sector;
    private String subSector;
    private String region;
    private String country;
    private String type;
    private DateRangeInput pricingDateRange;
    private String investorId;

    public boolean hasInvestorId() {
        return investorId != null;
    }
}
