package com.test.investor_analytics.repository;

import com.test.investor_analytics.entity.InvestorAnalytic;

import java.util.List;

public interface InvestorAnalyticCustomRepository {
    List<InvestorAnalytic> getInvestorAnalytics();
}
