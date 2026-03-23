package com.test.investor_analytics.repository.issuer;

import com.test.investor_analytics.entity.Issuer;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;

public interface IssuerRepositoryCustom {
    PageData<Issuer> find(PaginationInput paginationInput);
}
