package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.InvestorAnalytic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvestorAnalyticCustomRepositoryImpl implements InvestorAnalyticCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<InvestorAnalytic> getInvestorAnalytics() {

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
                        )
        );

        return mongoTemplate.aggregate(
                aggregation,
                "deals",
                InvestorAnalytic.class
        ).getMappedResults();
    }
}