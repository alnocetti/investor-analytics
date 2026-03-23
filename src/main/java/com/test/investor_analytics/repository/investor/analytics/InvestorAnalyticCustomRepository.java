package com.test.investor_analytics.repository.investor.analytics;

import com.test.investor_analytics.entity.InvestorAnalytic;
import com.test.investor_analytics.entity.PageData;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;

public interface InvestorAnalyticCustomRepository {
    PageData<InvestorAnalytic> getInvestorAnalytics(PaginationInput pagination);
}
