package com.wedding.backend.util.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.function.Supplier;

public class ElasticSearchUtil {
    public static Supplier<Query> createSupplierQuery(String approximateServiceTitle) {
        Supplier<Query> supplier = () -> Query.of(q -> q.fuzzy(createFuzzyQuery(approximateServiceTitle)));
        return supplier;
    }

    public static FuzzyQuery createFuzzyQuery(String approximateServiceTitle) {
        var fuzzyQuery = new FuzzyQuery.Builder();
        return fuzzyQuery.field("title").value(approximateServiceTitle).build();
    }
}
