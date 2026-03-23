package com.test.investor_analytics.repository.deal;

import com.test.investor_analytics.entity.Deal;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.DealFilterInput;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.BaseRepository;
import com.test.investor_analytics.utils.FilterUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class DealRepositoryCustomImpl extends BaseRepository implements DealRepositoryCustom {

    public DealRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public PageData<Deal> find(DealFilterInput filter, PaginationInput paginationInput) {
        Criteria criteria = FilterUtils.buildDealFilters(filter);
        return find(Deal.class, criteria, paginationInput);
    }
}
