package com.test.investor_analytics.utils;

import com.test.investor_analytics.graphql.dto.PaginationInfoDTO;
import com.test.investor_analytics.graphql.dto.input.PaginationInput;

import java.util.Optional;
import java.util.function.Function;

public class PaginationUtils {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    public static PaginationInput resolvePagination(Object input, Function<Object, PaginationInput> extractor) {
        if (input == null) return null;
        return extractor.apply(input);
    }

    public static int resolvePage(PaginationInput pagination) {
        return Optional.ofNullable(pagination)
                .map(PaginationInput::getPage)
                .orElse(DEFAULT_PAGE);
    }

    public static int resolveSize(PaginationInput pagination) {
        return Optional.ofNullable(pagination)
                .map(PaginationInput::getSize)
                .map(size -> Math.min(size, MAX_SIZE))
                .orElse(DEFAULT_SIZE);
    }

    public static long resolveSkip(PaginationInput pagination) {
        return (long) resolvePage(pagination) * resolveSize(pagination);
    }

    public static PaginationInfoDTO buildPageInfo(long total, int page, int size) {
        int totalPages = (int) Math.ceil((double) total / size);

        return PaginationInfoDTO.builder()
                .totalElements(total)
                .totalPages(totalPages)
                .page(page)
                .size(size)
                .build();
    }
}
