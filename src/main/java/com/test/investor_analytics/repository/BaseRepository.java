package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static com.test.investor_analytics.utils.PaginationUtils.*;

@RequiredArgsConstructor
public abstract class BaseRepository {

    protected final MongoTemplate mongoTemplate;

    protected <T> PageData<T> find(Class<T> clazz, Criteria criteria, PaginationInput paginationInput) {

        int page = resolvePage(paginationInput);
        int size = resolveSize(paginationInput);

        Query baseQuery = buildQuery(criteria);

        List<T> data = mongoTemplate.find(
                baseQuery.skip(resolveSkip(paginationInput)).limit(size),
                clazz
        );

        long total = mongoTemplate.count(buildQuery(criteria), clazz);

        return new PageData<>(data, total, page, size);
    }

    private Query buildQuery(Criteria criteria) {
        return (criteria == null || criteria.getCriteriaObject().isEmpty())
                ? new Query()
                : new Query(criteria);
    }
}