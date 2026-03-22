package com.test.investor_analytics.graphql.mapper;

import com.test.investor_analytics.entity.Issuer;
import com.test.investor_analytics.entity.embedded.IssuerEmbedded;
import com.test.investor_analytics.graphql.dto.IssuerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ObjectIdMapper.class})
public interface IssuerMapper {
    IssuerDTO toDto(IssuerEmbedded issuer);
    IssuerDTO toDto(Issuer issuer);
}
