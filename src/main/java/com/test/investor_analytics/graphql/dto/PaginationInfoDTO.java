package com.test.investor_analytics.graphql.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationInfoDTO {
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
}
