package com.wedding.backend.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.wedding.backend.entity.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ElasticSearchQuery {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "services";

    public String createOrUpdateDocument(ElasticSearchService elasticSearchService) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(elasticSearchService.getId())
                .document(elasticSearchService)
        );

        if (response.result().name().equals("Created")) {
            return new StringBuilder("Document has been successfully created.").toString();
        } else if (response.result().name().equals("Updated")) {
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public ElasticSearchService getDocumentById(String elasticSearchServiceId) throws IOException {
        ElasticSearchService elasticSearchService = null;
        GetResponse<ElasticSearchService> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(elasticSearchServiceId),
                ElasticSearchService.class
        );

        if (response.found()) {
            elasticSearchService = response.source();
            System.out.println("Elasticsearch service title " + elasticSearchService.getTitle());
        } else {
            System.out.println("Elasticsearch service not found");
        }
        return elasticSearchService;
    }

    public String deleteDocumentById(String elasticSearchServiceId) throws IOException {
        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(elasticSearchServiceId));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);

        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Elasticsearch service with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("Elasticsearch service not found");
        return new StringBuilder("Elasticsearch service with id " + deleteResponse.id() + " does not exist.").toString();
    }

    public List<ElasticSearchService> searchAllDocuments() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, ElasticSearchService.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<ElasticSearchService> elasticSearchServices = new ArrayList<>();
        for (Hit object : hits
        ) {
            elasticSearchServices.add((ElasticSearchService) object.source());
        }
        return elasticSearchServices;
    }
}
