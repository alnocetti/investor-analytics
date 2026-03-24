package com.test.investor_analytics.utils;

import com.test.investor_analytics.graphql.dto.input.DateRangeInput;
import com.test.investor_analytics.graphql.dto.input.DealFilterInput;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
    public static Criteria buildDealFilters(DealFilterInput filter) {

        if (filter == null) {
            return new Criteria(); // match all
        }

        List<Criteria> criteriaList = new ArrayList<>();

        if (filter.getRegion() != null) {
            criteriaList.add(Criteria.where("region").is(filter.getRegion()));
        }

        if (filter.getSector() != null) {
            criteriaList.add(Criteria.where("sector").is(filter.getSector()));
        }

        if (filter.getSubSector() != null) {
            criteriaList.add(Criteria.where("subSector").is(filter.getSubSector()));
        }

        if (filter.getCountry() != null) {
            criteriaList.add(Criteria.where("country").is(filter.getCountry()));
        }

        if (filter.getType() != null) {
            criteriaList.add(Criteria.where("type").is(filter.getType()));
        }

        if (filter.hasInvestorId()) {
            criteriaList.add(
                Criteria.where("investorDealAnalytics")
                        .elemMatch(Criteria.where("investor._id").
                                is(new ObjectId(filter.getInvestorId())))
            );
        }

        if (filter.getPricingDateRange() != null) {
            DateRangeInput range = filter.getPricingDateRange();

            if (range.getStartDate() != null || range.getEndDate() != null) {
                Criteria dateCriteria = Criteria.where("pricingDate");

                if (range.getStartDate() != null) {
                    dateCriteria.gte(LocalDate.parse(range.getStartDate()));
                }

                if (range.getEndDate() != null) {
                    dateCriteria.lte(LocalDate.parse(range.getEndDate()));
                }

                criteriaList.add(dateCriteria);
            }
        }

        return criteriaList.isEmpty()
                ? new Criteria()
                : new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
    }
}
