package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.Investor;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvestorRepositoryCustomImpl extends BaseRepository implements InvestorRepositoryCustom {

    public InvestorRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public PageData<Investor> find(PaginationInput paginationInput) {
        return find(Investor.class, null, paginationInput);
    }
}

