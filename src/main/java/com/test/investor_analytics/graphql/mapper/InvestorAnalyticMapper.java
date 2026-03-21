package com.test.investor_analytics.graphql.mapper;

import com.test.investor_analytics.entity.InvestorAnalytic;
import com.test.investor_analytics.graphql.dto.InvestorAnalyticDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses =  {InvestorMapper.class})
public interface InvestorAnalyticMapper {
    InvestorAnalyticDTO toDto(InvestorAnalytic investorAnalytic);
}
