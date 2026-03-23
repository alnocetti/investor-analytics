package com.test.investor_analytics.graphql.resolver;

import com.test.investor_analytics.entity.*;
import com.test.investor_analytics.graphql.dto.*;
import com.test.investor_analytics.graphql.dto.input.*;
import com.test.investor_analytics.graphql.mapper.DealMapper;
import com.test.investor_analytics.graphql.mapper.InvestorAnalyticMapper;
import com.test.investor_analytics.graphql.mapper.InvestorMapper;
import com.test.investor_analytics.graphql.mapper.IssuerMapper;
import com.test.investor_analytics.service.*;
import com.test.investor_analytics.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.test.investor_analytics.utils.PaginationUtils.buildPageInfo;

@Controller
public class QueryResolver {

    @Autowired
    private DealService dealService;

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private InvestorService investorService;

    @Autowired
    private InvestorMapper investorMapper;

    @Autowired
    private InvestorAnalyticService investorAnalyticService;

    @Autowired
    private InvestorAnalyticMapper investorAnalyticMapper;

    @Autowired
    private IssuerService issuerService;

    @Autowired
    private IssuerMapper issuerMapper;

    @Autowired
    private InvestorRecommendationService investorRecommendationService;

    @QueryMapping
    public PageResponseDTO<DealDTO> getDeals(
            @Argument GetDealsInput input) {

        PaginationInput pagination = input != null ? input.getPagination() : null;
        DealFilterInput filter = input != null ? input.getFilter() : null;

        PageData<Deal> result = dealService.getAllDeals(pagination, filter);

        List<DealDTO> content = result.getContent().stream()
                .map(dealMapper::toDto)
                .toList();

        return PageResponseDTO.<DealDTO>builder()
                .content(content)
                .paginationInfo(buildPageInfo(result.getTotal(), result.getPage(), result.getPageSize()))
                .build();
    }

    @QueryMapping
    public PageResponseDTO<InvestorDTO> getInvestors(
            @Argument GetInvestorsInput input) {

        PaginationInput pagination = PaginationUtils.resolvePagination(
                input,
                i -> ((GetInvestorsInput) i).getPagination()
        );
        PageData<Investor> result = investorService.getAllInvestors(pagination);

        List<InvestorDTO> content = result.getContent().stream()
                .map(investorMapper::toDto)
                .toList();

        return PageResponseDTO.<InvestorDTO>builder()
                .content(content)
                .paginationInfo(buildPageInfo(result.getTotal(), result.getPage(), result.getPageSize()))
                .build();
    }

    @QueryMapping
    public PageResponseDTO<InvestorAnalyticDTO> getInvestorAnalytics(
            @Argument GetInvestorAnalyticsInput input) {

        PaginationInput pagination = PaginationUtils.resolvePagination(
                input,
                i -> ((GetInvestorAnalyticsInput) i).getPagination()
        );
        PageData<InvestorAnalytic> result = investorAnalyticService.getInvestorAnalytics(pagination);

        List<InvestorAnalyticDTO> content = result.getContent().stream()
                .map(investorAnalyticMapper::toDto)
                .toList();

        return PageResponseDTO.<InvestorAnalyticDTO>builder()
                .content(content)
                .paginationInfo(buildPageInfo(result.getTotal(), result.getPage(), result.getPageSize()))
                .build();
    }

    @QueryMapping
    public PageResponseDTO<IssuerDTO> getIssuers(
            @Argument GetIssuersInput input) {

        PaginationInput pagination = PaginationUtils.resolvePagination(
                input,
                i -> ((GetIssuersInput) i).getPagination()
        );
        PageData<Issuer> result = issuerService.getAllIssuers(pagination);

        List<IssuerDTO> content =  result.getContent().stream()
                .map(issuerMapper::toDto)
                .toList();

        return PageResponseDTO.<IssuerDTO>builder()
                .content(content)
                .paginationInfo(buildPageInfo(result.getTotal(), result.getPage(), result.getPageSize()))
                .build();
    }

    @QueryMapping
    public List<InvestorDTO> investorRecommendations(
            @Argument InvestorRecommendationsInput input) {

        List<InvestorEmbedding> docs = investorRecommendationService.recommend(
                input.getRegion(),
                input.getSector(),
                input.getLimit() == null ? 10 : input.getLimit()
        );

        return docs.stream()
                .map(investorMapper::toDto)
                .toList();
    }
}
