package com.test.investor_analytics.utils;

import com.test.investor_analytics.graphql.dto.common.SortDirection;
import com.test.investor_analytics.graphql.dto.common.SortInput;
import org.springframework.data.domain.Sort;

public class SortUtils {

    public static Sort buildSort(SortInput sortInput) {
        if (sortInput == null || sortInput.getField() == null) {
            return Sort.unsorted();
        }

        Sort.Direction direction = sortInput.getDirection() == SortDirection.ASC
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        // Asegúrate que el nombre del campo coincide con el de la entidad
        return Sort.by(direction, sortInput.getField());
    }
}
