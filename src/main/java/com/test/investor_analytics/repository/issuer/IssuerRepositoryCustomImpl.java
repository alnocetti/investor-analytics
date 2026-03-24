package com.test.investor_analytics.repository.issuer;

import com.test.investor_analytics.entity.Issuer;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import com.test.investor_analytics.repository.BaseRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IssuerRepositoryCustomImpl extends BaseRepository<Issuer> implements IssuerRepositoryCustom {

    public IssuerRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Issuer.class);
    }

    @Override
    public PageData<Issuer> find(PaginationInput paginationInput) {
        return find(Issuer.class, null, paginationInput);
    }
}
