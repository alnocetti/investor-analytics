package com.test.investor_analytics.graphql.mapper;

import com.test.investor_analytics.entity.Investor;
import com.test.investor_analytics.entity.embedded.InvestorEmbedded;
import com.test.investor_analytics.entity.InvestorEmbedding;
import com.test.investor_analytics.graphql.dto.InvestorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ObjectIdMapper.class})
public interface InvestorMapper {

    @Mapping(source = "investorId", target = "id")
    InvestorDTO toDto(InvestorEmbedding investor);
    InvestorDTO toDto(InvestorEmbedded investor);
    InvestorDTO toDto(Investor investor);
}
