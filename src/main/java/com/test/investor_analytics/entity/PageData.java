package com.test.investor_analytics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageData<T> {
    private List<T> content;
    private Long total;
    private int page;
    private int pageSize;

    public int getTotalPages() {
        return (int) Math.ceil((double) total / pageSize);
    }

}
