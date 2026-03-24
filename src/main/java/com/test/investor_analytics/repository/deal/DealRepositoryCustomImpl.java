package com.test.investor_analytics.repository.deal;

import com.test.investor_analytics.entity.Deal;
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
public class DealRepositoryCustomImpl extends BaseRepository<Deal> implements DealRepositoryCustom {

    public DealRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Deal.class);
    }

    @Override
    public PageData<Deal> find(DealFilterInput filter, PaginationInput pagination, SortInput sort) {

        Criteria criteria = FilterUtils.buildDealFilters(filter);

        List<AggregationOperation> operations = DealAggregationBuilder.build(criteria,
                        filter != null && filter.hasInvestorId() ? filter.getInvestorId() : null,
                        pagination,
                        sort);


        return executeAggregation(
                operations,
                pagination
        );
    }
}
