package com.test.investor_analytics.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="issuers")
public class Issuer {
    @Id
    private ObjectId id;
    private String name;
}
