package com.test.investor_analytics.graphql.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ObjectIdMapper {
    default String map(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }}
