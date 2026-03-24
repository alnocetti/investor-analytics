package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.Deal;
import com.test.investor_analytics.entity.InvestorAnalytic;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public abstract class BaseRepository<T> {

    protected final MongoTemplate mongoTemplate;
    private final Class<T> entityClass;

    protected BaseRepository(MongoTemplate mongoTemplate, Class<T> entityClass) {
        this.mongoTemplate = mongoTemplate;
        this.entityClass = entityClass;
    }

    protected String getCollectionName() {
        //TODO: revisit this logic if we have more analytic entities in the future
        if(entityClass.equals(InvestorAnalytic.class)) {
            return mongoTemplate.getCollectionName(Deal.class);
        }
        return mongoTemplate.getCollectionName(entityClass);
    }

    protected <T> PageData<T> find(Class<T> clazz, Criteria criteria, PaginationInput pagination) {

        int page = pagination.resolvePage();
        int size = pagination.resolveSize();

        Query baseQuery = buildQuery(criteria);

        List<T> data = mongoTemplate.find(
                baseQuery.skip(pagination.resolveSkip()).limit(size),
                clazz
        );

        long total = mongoTemplate.count(buildQuery(criteria), clazz);

        return new PageData<>(data, total, page, size);
    }

    protected <T> PageData<T> executeAggregation(
            List<AggregationOperation> operations,
            PaginationInput pagination
    ) {

        int page = pagination.resolvePage();
        int size = pagination.resolveSize();

        Aggregation aggregation = Aggregation.newAggregation(operations);

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                getCollectionName(),
                Document.class
        );

        Document result = results.getUniqueMappedResult();

        if (result == null) {
            return new PageData<>(List.of(), 0L, page, size);
        }

        List<Document> dataDocs = (List<Document>) result.get("data");

        List<T> data = (List<T>) dataDocs.stream()
                .map(doc -> mongoTemplate.getConverter().read(entityClass, doc))
                .toList();

        List<Document> countDocs = (List<Document>) result.get("count");

        long total = 0;
        if (countDocs != null && !countDocs.isEmpty()) {
            total = ((Number) countDocs.getFirst().get("total")).longValue();
        }

        return new PageData<>(data, total, page, size);
    }

    private Query buildQuery(Criteria criteria) {
        return (criteria == null || criteria.getCriteriaObject().isEmpty())
                ? new Query()
                : new Query(criteria);
    }
}