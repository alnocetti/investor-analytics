package com.test.investor_analytics.graphql.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationInput {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100; //TODO implement max size validation

    private Integer page;
    private Integer size;

    public int resolvePage() {
        return page != null ? page : DEFAULT_PAGE;
    }

    public int resolveSize() {
        return size != null ? size : DEFAULT_SIZE;
    }

    public long resolveSkip() {
        return (long) resolvePage() * resolveSize();
    }
}
