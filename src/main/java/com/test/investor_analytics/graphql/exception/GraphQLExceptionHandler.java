package com.test.investor_analytics.graphql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {

        if (ex instanceof DateTimeParseException) {
            return GraphqlErrorBuilder.newError(env)
                    .message("Invalid date format. Use yyyy-MM-dd")
                    .build();
        }

        return null;
    }
}