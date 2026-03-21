package com.test.investor_analytics.graphql.mapper;

import com.test.investor_analytics.entity.InvestorDealAnalytic;
import com.test.investor_analytics.graphql.dto.InvestorDealAnalyticDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses =  {InvestorMapper.class})
public interface InvestorDealAnalyticMapper {
    InvestorDealAnalyticDTO toDto(InvestorDealAnalytic investorDealAnalytic);
}
