package com.test.investor_analytics.graphql.resolver;

import com.test.investor_analytics.entity.*;
import com.test.investor_analytics.graphql.dto.DealDTO;
import com.test.investor_analytics.graphql.dto.InvestorAnalyticDTO;
import com.test.investor_analytics.graphql.dto.InvestorDTO;
import com.test.investor_analytics.graphql.dto.IssuerDTO;
import com.test.investor_analytics.graphql.dto.common.PageResponseDTO;
import com.test.investor_analytics.graphql.dto.common.PaginationInfoDTO;
import com.test.investor_analytics.graphql.dto.common.SortInput;
import com.test.investor_analytics.graphql.dto.input.*;
import com.test.investor_analytics.graphql.mapper.DealMapper;
import com.test.investor_analytics.graphql.mapper.InvestorAnalyticMapper;
import com.test.investor_analytics.graphql.mapper.InvestorMapper;
import com.test.investor_analytics.graphql.mapper.IssuerMapper;
import com.test.investor_analytics.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

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
        SortInput sort = input != null ? input.getSort() : null;

        PageData<Deal> result = dealService.getAllDeals(pagination, filter, sort);

        List<DealDTO> content = result.getContent().stream()
                .map(dealMapper::toDto)
                .toList();

        PaginationInfoDTO paginationInfo = PaginationInfoDTO.builder()
                .size(result.getPageSize())
                .page(result.getPage())
                .totalElements(result.getTotal())
                .totalPages(result.getTotalPages())
                .build();

        return PageResponseDTO.<DealDTO>builder()
                .content(content)
                .paginationInfo(paginationInfo)
                .build();
    }

    @QueryMapping
    public PageResponseDTO<InvestorDTO> getInvestors(
            @Argument GetInvestorsInput input) {

        PaginationInput pagination = input != null ? input.getPagination() : null;

        PageData<Investor> result = investorService.getAllInvestors(pagination);

        List<InvestorDTO> content = result.getContent().stream()
                .map(investorMapper::toDto)
                .toList();

        PaginationInfoDTO paginationInfo = PaginationInfoDTO.builder()
                .size(result.getPageSize())
                .page(result.getPage())
                .totalElements(result.getTotal())
                .totalPages(result.getTotalPages())
                .build();

        return PageResponseDTO.<InvestorDTO>builder()
                .content(content)
                .paginationInfo(paginationInfo)
                .build();
    }

    @QueryMapping
    public PageResponseDTO<InvestorAnalyticDTO> getInvestorAnalytics(
            @Argument GetInvestorAnalyticsInput input) {

        PaginationInput pagination = input != null ? input.getPagination() : null;
        DealFilterInput filter = input != null ? input.getFilter() : null;
        SortInput sort = input != null ? input.getSort() : null;

        PageData<InvestorAnalytic> result = investorAnalyticService.getInvestorAnalytics(filter, pagination, sort);

        List<InvestorAnalyticDTO> content = result.getContent().stream()
                .map(investorAnalyticMapper::toDto)
                .toList();

        PaginationInfoDTO paginationInfo = PaginationInfoDTO.builder()
                .size(result.getPageSize())
                .page(result.getPage())
                .totalElements(result.getTotal())
                .totalPages(result.getTotalPages())
                .build();

        return PageResponseDTO.<InvestorAnalyticDTO>builder()
                .content(content)
                .paginationInfo(paginationInfo)
                .build();
    }

    @QueryMapping
    public PageResponseDTO<IssuerDTO> getIssuers(
            @Argument GetIssuersInput input) {

        PaginationInput pagination = input != null ? input.getPagination() : null;

        PageData<Issuer> result = issuerService.getAllIssuers(pagination);

        List<IssuerDTO> content =  result.getContent().stream()
                .map(issuerMapper::toDto)
                .toList();

        PaginationInfoDTO paginationInfo = PaginationInfoDTO.builder()
                .size(result.getPageSize())
                .page(result.getPage())
                .totalElements(result.getTotal())
                .totalPages(result.getTotalPages())
                .build();

        return PageResponseDTO.<IssuerDTO>builder()
                .content(content)
                .paginationInfo(paginationInfo)
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
