package com.test.investor_analytics.repository.investor.analytics;

//import com.test.investor_analytics.graphql.dto.input.PaginationInput;
//import com.test.investor_analytics.repository.AggregationPipeline;
//import org.bson.types.ObjectId;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.aggregation.Aggregation;
//import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
//import org.springframework.data.mongodb.core.query.Criteria;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class InvestorAnalyticAggregationBuilder {
//
//    public static List<AggregationOperation> build(
//            Criteria criteria,
//            String investorId,
//            PaginationInput pagination
//    ) {
//
//        List<AggregationOperation> ops = new ArrayList<>();
//
//        if (criteria != null) {
//            ops.add(Aggregation.match(criteria));
//        }
//
//        ops.add(Aggregation.unwind("investorDealAnalytics"));
//
//        if (investorId != null) {
//            ops.add(Aggregation.match(
//                    Criteria.where("investorDealAnalytics.investor._id")
//                            .is(new ObjectId(investorId))
//            ));
//        }
//
//        ops.add(Aggregation.group("investorDealAnalytics.investor._id")
//                .first("investorDealAnalytics.investor").as("investor")
//                .sum("investorDealAnalytics.allocation").as("totalAllocation")
//                .sum("investorDealAnalytics.demand").as("totalDemand")
//                .avg("investorDealAnalytics.allocationRank").as("averageAllocationRank")
//                .avg("investorDealAnalytics.demandRank").as("averageDemandRank")
//        );
//
//        ops.add(Aggregation.project()
//                .andExclude("_id")
//                .andInclude(
//                        "investor",
//                        "totalAllocation",
//                        "totalDemand",
//                        "averageAllocationRank",
//                        "averageDemandRank"
//                )
//        );
//
//        ops.add(Aggregation.sort(Sort.Direction.DESC, "totalAllocation"));
//
//        ops.add(
//                Aggregation.facet(
//                                Aggregation.skip(pagination.resolveSkip()),
//                                Aggregation.limit(pagination.resolveSize())
//                        ).as("data")
//                        .and(Aggregation.count().as("total")).as("count")
//        );
//
//        return ops;
//    }
//}

import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.AggregationPipeline;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class InvestorAnalyticAggregationBuilder {

    public static List<AggregationOperation> build(
            Criteria criteria,
            String investorId,
            PaginationInput pagination
    ) {

        AggregationPipeline pipeline = AggregationPipeline.builder().build();

        // match base
        if (criteria != null) {
            pipeline.add(Aggregation.match(criteria));
        }

        // unwind
        pipeline.add(Aggregation.unwind("investorDealAnalytics"));

        // investorFilter
        if (investorId != null) {
            pipeline.add(
                    Aggregation.match(
                            Criteria.where("investorDealAnalytics.investor._id")
                                    .is(new ObjectId(investorId))
                    )
            );
        }


        // group (analytics)
        pipeline.add(
                Aggregation.group("investorDealAnalytics.investor._id")
                        .first("investorDealAnalytics.investor").as("investor")
                        .count().as("participation")
                        .sum("investorDealAnalytics.allocation").as("totalAllocation")
                        .sum("investorDealAnalytics.demand").as("totalDemand")
                        .avg("investorDealAnalytics.allocationRank").as("averageAllocationRank")
                        .avg("investorDealAnalytics.demandRank").as("averageDemandRank")
        );

        // project
        pipeline.add(
                Aggregation.project()
                        .andExclude("_id")
                        .andInclude(
                                "investor",
                                "participation",
                                "totalAllocation",
                                "totalDemand",
                                "averageAllocationRank",
                                "averageDemandRank"
                        )
        );

        // sort
        pipeline.add(Aggregation.sort(Sort.Direction.DESC, "totalAllocation"));

        // facet (pagination)
        pipeline.add(
                Aggregation.facet(
                                Aggregation.skip(pagination.resolveSkip()),
                                Aggregation.limit(pagination.resolveSize())
                        ).as("data")
                        .and(Aggregation.count().as("total")).as("count")
        );

        return pipeline.getOperations();
    }
}
