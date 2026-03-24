package com.test.investor_analytics.repository.investor;

import com.test.investor_analytics.entity.Investor;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.BaseRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvestorRepositoryCustomImpl extends BaseRepository<Investor> implements InvestorRepositoryCustom {

    public InvestorRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Investor.class);
    }

    @Override
    public PageData<Investor> find(PaginationInput paginationInput) {
        return find(Investor.class, null, paginationInput, null);
    }
}

