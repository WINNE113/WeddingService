package com.wedding.backend.controller.service;

import com.wedding.backend.entity.ElasticSearchService;
import com.wedding.backend.service.impl.service.ElasticSearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/elasticsearch-services")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ElasticsearchServiceController {
    private final ElasticSearchQuery elasticSearchQuery;


    @GetMapping(value = "/fuzzy-search")
    public ResponseEntity<?> fuzzySearch(@RequestParam(name = "approximateServiceTitle") String approximateServiceTitle) throws IOException {
        return ResponseEntity.ok(elasticSearchQuery.fuzzySearch(approximateServiceTitle));
    }

    @PostMapping("/createOrUpdateDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody ElasticSearchService elasticSearchService) throws IOException {
        String response = elasticSearchQuery.createOrUpdateDocument(elasticSearchService);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getDocument")
    public ResponseEntity<Object> getDocumentById(@RequestParam String elasticSearchServiceId) throws IOException {
        ElasticSearchService elasticSearchService = elasticSearchQuery.getDocumentById(elasticSearchServiceId);
        return new ResponseEntity<>(elasticSearchService, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocument")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String elasticSearchServiceId) throws IOException {
        String response = elasticSearchQuery.deleteDocumentById(elasticSearchServiceId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchDocument")
    public ResponseEntity<Object> searchAllDocument() throws IOException {
        List<ElasticSearchService> elasticSearchServices = elasticSearchQuery.searchAllDocuments();
        return new ResponseEntity<>(elasticSearchServices, HttpStatus.OK);
    }

    @GetMapping(value = "/delete-index")
    public void deleteIndex(@RequestParam(name = "indexName") String indexName) {
        elasticSearchQuery.deleteIndex(indexName);
    }
}
