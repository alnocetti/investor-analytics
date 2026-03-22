package com.test.investor_analytics.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "investor_embeddings")
@Getter
@Setter
public class InvestorEmbedding {

    @Id
    private ObjectId id;
    private ObjectId investorId;
    private String name;
    private String type;

    private List<Double> embedding;
}