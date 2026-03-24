package com.test.investor_analytics.graphql.dto.input;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateRangeInput {
    private LocalDate startDate;
    private LocalDate endDate;
}
