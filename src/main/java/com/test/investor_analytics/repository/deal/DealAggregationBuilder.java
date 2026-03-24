package com.test.investor_analytics.repository.deal;

import com.test.investor_analytics.graphql.dto.common.SortInput;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.AggregationPipeline;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static com.test.investor_analytics.graphql.dto.common.SortDirection.ASC;

public class DealAggregationBuilder {

    public static List<AggregationOperation> build(
            Criteria criteria,
            String investorId,
            PaginationInput pagination,
            SortInput sort
    ) {

        AggregationPipeline pipeline = AggregationPipeline.builder().build();

        // apply base criteria if provided
        if (criteria != null) {
                pipeline.add(Aggregation.match(criteria));
        }

        // unwind to filter by investorId if provided
        pipeline.addIf(
                investorId != null,
                Aggregation.unwind("investorDealAnalytics")
        );

        // filter by investorId if provided
        if (investorId != null) {
             pipeline.add(
                     Aggregation.match(
                             Criteria.where("investorDealAnalytics.investor._id")
                                     .is(new ObjectId(investorId))
                     )
             );
        }

        // group back if investorId was provided
        if (investorId != null) {
            pipeline.add(
                    Aggregation.group("_id")
                            .first(Aggregation.ROOT).as("doc")
                            .push("investorDealAnalytics").as("investorDealAnalytics")
            );
        }

        // merge back the investorDealAnalytics into the root document
        if(investorId != null){
                pipeline.add(
                        Aggregation.replaceRoot(
                                AggregationOperationContext -> new Document("$mergeObjects",
                                        List.of("$doc", new Document("investorDealAnalytics", "$investorDealAnalytics"))
                                )
                        )
                );
        }

        if (sort != null) {
                pipeline.add(
                        Aggregation.sort(
                                sort.getDirection() == ASC
                                        ? Sort.by(sort.getField()).ascending()
                                        : Sort.by(sort.getField()).descending()
                        )
                );
        } else {
                // default sort by pricingDate desc
                pipeline.add(
                        Aggregation.sort(Sort.by("pricingDate", "_id").descending())

                );
        }


        // apply pagination
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
