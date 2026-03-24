📊 Investor Analytics & Recommendation Engine

A backend system for analyzing investor behavior and generating recommendations based on historical deal data, using Spring Boot, MongoDB, and vector similarity techniques.


**Project structure**

- `entity` → MongoDB models  
- `repository` → Data access layer (Mongo + custom)  
- `service` → Business logic  
- `graphql/dto` → API layer  
- `mapper` → MapStruct (Entity → DTO)

**Main architecture**
```text
controller / graphql
        ↓
service
        ↓
repository (custom)
        ↓
MongoTemplate + Aggregations
```

**Main components**
- BaseRepository
	- Generic queries and aggregations handling
 	- Reusable pagination
- InvestorAnalyticCustomRepository
	- Complex query building
	- Mongo pipelines usage
- InvestorAnalyticsAggregationBuilder
    - Pipeline logic encapsulated
	- Dynamic filtering

**Data model**
Main entity - Deal, contains information about each deal and analytics per investor on that deal
```text
{
  "_id": "...",
  "issuer": {...},
  "investorDealAnalytics": [
    {
      "investor": {...},
      "allocation": 100,
      "demand": 200,
      "allocationRank": 1,
      "demandRank": 2
    }
  ]
}
```

**Analytic response**
Investor Analytic aggregation
```text
{
  "investor": {...},
  "totalAllocation": 1000,
  "totalDemand": 2000,
  "averageAllocationRank": 1.5,
  "averageDemandRank": 2.1
}
```


**Tech stack**
- Spring Boot
- Spring Data MongoDB
- GraphQL
- MapStruct
- Lombok


**How to run this project locally**
1- Create your Mongo Atlas DB

2- Update application properties
	b. Update mongo db URI.
	c. Set app.embeddings.rebuild to true, to create embeddings.

3- Run mock-data-generator.js in your Mongo instance to populate collections.

4- start app
	./mvnw spring-boot:run

**Local Graphql playground**
http://localhost:8080/graphiql?path=/graphql

**Diagnose endpoint**
http://localhost:8080/api/diagnose

	

