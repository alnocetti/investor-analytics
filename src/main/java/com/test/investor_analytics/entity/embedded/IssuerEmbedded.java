package com.test.investor_analytics.entity.embedded;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class IssuerEmbedded {
    private ObjectId id;
    private String name;
}
