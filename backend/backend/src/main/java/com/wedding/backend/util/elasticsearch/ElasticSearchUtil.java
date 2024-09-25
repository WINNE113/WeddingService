package com.wedding.backend.util.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.function.Supplier;

public class ElasticSearchUtil {
    public static Supplier<Query> createSupplierQuery(String approximateServiceTitle) {
        Supplier<Query> supplier = () -> Query.of(q -> q.match(createMatchQuery(approximateServiceTitle)));
        System.out.println("Query match search: " + createMatchQuery(approximateServiceTitle));
        return supplier;
    }

    public static FuzzyQuery createFuzzyQuery(String approximateServiceTitle) {
        var fuzzyQuery = new FuzzyQuery.Builder();
        return fuzzyQuery.field("title").value(approximateServiceTitle).build();
    }

    public static MatchQuery createMatchQuery(String approximateServiceTitle){
        var matchQuery = new MatchQuery.Builder();
        return matchQuery
                .field("title")
                .query((approximateServiceTitle))
                .fuzziness("2")
                .operator(Operator.And)
                .build();
    }
}
