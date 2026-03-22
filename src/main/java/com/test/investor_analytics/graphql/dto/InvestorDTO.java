package com.test.investor_analytics.graphql.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvestorDTO {
    private String id;
    private String name;
    private String type;
}
