package com.test.investor_analytics.repository.investor.analytics;

import com.test.investor_analytics.entity.InvestorAnalytic;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.test.investor_analytics.utils.PaginationUtils.*;

@Repository
public class InvestorAnalyticCustomRepositoryImpl implements InvestorAnalyticCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PageData<InvestorAnalytic> getInvestorAnalytics(PaginationInput pagination) {

        long skip = resolveSkip(pagination);
        int page = resolvePage(pagination);
        int size = resolveSize(pagination);

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.unwind("investorDealAnalytics"),

                Aggregation.group("investorDealAnalytics.investor._id")
                        .first("investorDealAnalytics.investor").as("investor")
                        .sum("investorDealAnalytics.allocation").as("totalAllocation")
                        .sum("investorDealAnalytics.demand").as("totalDemand")
                        .avg("investorDealAnalytics.allocationRank").as("averageAllocationRank")
                        .avg("investorDealAnalytics.demandRank").as("averageDemandRank"),

                Aggregation.project()
                        .andExclude("_id")
                        .andInclude(
                                "investor",
                                "totalAllocation",
                                "totalDemand",
                                "averageAllocationRank",
                                "averageDemandRank"
                        ),

                Aggregation.facet(
                                Aggregation.sort(Sort.Direction.DESC, "totalAllocation"),
                                Aggregation.skip(skip),
                                Aggregation.limit(size)
                        ).as("data")
                        .and(Aggregation.count().as("total")).as("count")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                "deals",
                Document.class
        );

        Document result = results.getUniqueMappedResult();

        if (result == null) {
            return new PageData<>(List.of(), 0L, page, size);
        }

        List<Document> dataDocs = (List<Document>) result.get("data");

        List<InvestorAnalytic> data = dataDocs.stream()
                .map(doc -> mongoTemplate.getConverter().read(InvestorAnalytic.class, doc))
                .toList();

        List<Document> countDocs = (List<Document>) result.get("count");

        long total = 0;
        if (countDocs != null && !countDocs.isEmpty()) {
            total = ((Number) countDocs.getFirst().get("total")).longValue();
        }

        return new PageData<>(data, total, page, size);
    }
}