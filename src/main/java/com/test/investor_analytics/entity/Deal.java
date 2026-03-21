package com.test.investor_analytics.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "deals")
public class Deal {
    @Id
    private ObjectId id;
    private Date pricingDate;
    private String type;
    private String sector;
    private String subSector;
    private String country;
    private String region;
    private Float size;
    private Float totalAllocation;
    private Float totalDemand;

    private IssuerEmbedded issuer;

    private List<InvestorDealAnalytic> investorDealAnalytics;
}
