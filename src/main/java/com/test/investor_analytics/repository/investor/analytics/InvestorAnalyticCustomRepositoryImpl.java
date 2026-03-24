package com.test.investor_analytics.repository.investor.analytics;

import com.test.investor_analytics.entity.InvestorAnalytic;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.common.SortInput;
import com.test.investor_analytics.graphql.dto.input.DealFilterInput;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.BaseRepository;
import com.test.investor_analytics.utils.FilterUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvestorAnalyticCustomRepositoryImpl extends BaseRepository<InvestorAnalytic> implements InvestorAnalyticCustomRepository {

    public InvestorAnalyticCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate, InvestorAnalytic.class);
    }

    @Override
    public PageData<InvestorAnalytic> getInvestorAnalytics(DealFilterInput filter, PaginationInput pagination, SortInput sort) {
        Criteria criteria = FilterUtils.buildDealFilters(filter);

        List<AggregationOperation> operations =
                InvestorAnalyticAggregationBuilder.build(
                        criteria,
                        filter != null ? filter.getInvestorId() : null,
                        pagination,
                        sort
                );

        return executeAggregation(
                operations,
                pagination
        );
    }
}