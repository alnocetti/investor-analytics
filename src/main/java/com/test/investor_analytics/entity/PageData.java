package com.test.investor_analytics.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {
    private List<T> content;
    private Long total;
    private int page;
    private int pageSize;
}
