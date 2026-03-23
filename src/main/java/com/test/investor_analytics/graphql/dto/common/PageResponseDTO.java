package com.test.investor_analytics.graphql.dto.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponseDTO <T>{
    private List<T> content;
    private PaginationInfoDTO paginationInfo;
}
