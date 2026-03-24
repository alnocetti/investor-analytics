package com.test.investor_analytics.repository;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class AggregationPipeline {

    @Builder.Default
    private List<AggregationOperation> operations = new ArrayList<>();

    public void addIf(boolean condition, AggregationOperation op) {
        if (condition) operations.add(op);
    }

    public void add(AggregationOperation op) {
        operations.add(op);
    }
}
