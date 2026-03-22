package com.test.investor_analytics.graphql.mapper;

import com.test.investor_analytics.entity.Investor;
import com.test.investor_analytics.entity.InvestorEmbedded;
import com.test.investor_analytics.entity.InvestorEmbedding;
import com.test.investor_analytics.graphql.dto.InvestorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ObjectIdMapper.class})
public interface InvestorMapper {
    InvestorDTO toDto(InvestorEmbedding investor);
    InvestorDTO toDto(InvestorEmbedded investor);
    InvestorDTO toDto(Investor investor);
}
