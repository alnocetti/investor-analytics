package com.test.investor_analytics.graphql.mapper;

import com.test.investor_analytics.entity.Deal;
import com.test.investor_analytics.graphql.dto.DealDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {InvestorDealAnalyticMapper.class,
                InvestorMapper.class,
                IssuerMapper.class,
                ObjectIdMapper.class
})
public interface DealMapper {
    DealDTO toDto(Deal deal);
}
