package com.wedding.backend.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedding.backend.dto.service.ElasticsearchServiceDTO;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableBatchProcessing
public class BatchJobConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private PlatformTransactionManager transactionManager;


    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private List<String> errorGlobal = new ArrayList<>();

    /**
     * Bean to create JobExecutionListener for monitoring job completion and logging errors.
     *
     * @return JobExecutionListener
     */
    @Bean
    public JobExecutionListener listenerJobReflectData() {
        return new JobCompletionNotificationListener("Job reflect data to Elasticsearch", errorGlobal);
    }

    /**
     * Bean for reading data from a relational database using JDBC.
     * The reader queries books, authors, and categories information.
     *
     * @return JdbcCursorItemReader<Book>
     */
    @Bean
    public JdbcCursorItemReader<ElasticsearchServiceDTO> readerJobReflectData() {
        String sqlQuery = "SELECT s.id as serviceId, s.title, s.information, s.image, s.address, \n" +
                "s.link_website as linkWebsite, s.link_facebook as linkFacebook, s.rotation, \n" +
                "s.is_deleted as isDeleted, s.is_selected as isSelected, s.is_publish_to_elasticsearch as isPublishToElasticsearch\n" +
                "from services as s where s.is_deleted = false and s.is_publish_to_elasticsearch = false and s.status = 'APPROVED'";

        JdbcCursorItemReader<ElasticsearchServiceDTO> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource); // Set the datasource
        reader.setSql(sqlQuery); // Set the SQL query
        reader.setRowMapper(new BeanPropertyRowMapper<>(ElasticsearchServiceDTO.class)); // Map result set to Book object
        return reader;
    }

    /**
     * Bean for writing data into Elasticsearch.
     * This method indexes each book object into the 'books' index in Elasticsearch.
     *
     * @return ItemWriter<Book>
     */
    @Bean
    public ItemWriter<ElasticsearchServiceDTO> elasticsearchItemWriter() {
        return services -> {
            String objItem;
            errorGlobal.clear(); // clear all before processing
            for (ElasticsearchServiceDTO service : services) {
                service.setId(String.valueOf(service.getServiceId()));
                ObjectMapper objectMapper = new ObjectMapper(); // Convert Book object to JSON
                try {
                    objItem = objectMapper.writeValueAsString(service); // Convert to JSON string
                } catch (Exception ex) {
                    objItem = "{}"; // Fallback to empty JSON if conversion fails
                    errorGlobal.add("Cannot convert service with id " + service.getServiceId() + " to JSON");
                    continue;
                }

                try {
                    // Create an index request to send to Elasticsearch
                    String finalObjItem = objItem;
                    IndexRequest<JsonData> indexRequest = IndexRequest.of(i -> i
                            .index("services") //index name
                            .id(String.valueOf(service.getServiceId())) // Document ID
                            .document(JsonData.fromJson(finalObjItem))); // Document content

                    IndexResponse response = elasticsearchClient.index(indexRequest); // Send the request
                    if (!response.result().name().equals("Created")) {
                        // Add error if document is not created
                        errorGlobal.add("Failed to index service with id " + service.getServiceId());
                    } else {
                        jdbcTemplate.update("UPDATE services SET is_publish_to_elasticsearch = true WHERE id = ?", service.getServiceId());
                        System.out.println("Update success serviceID: " + service.getServiceId());
                    }
                } catch (Exception ex) {
                    errorGlobal.add("Cannot write service with id " + service.getServiceId() + " to Elasticsearch");
                }
            }
        };
    }

    /**
     * Step definition for the batch job.
     * It defines a chunk-oriented processing model that reads services from the database
     * and writes them to Elasticsearch.
     *
     * @return TaskletStep
     */

    @Bean
    public TaskletStep step() {
        return new StepBuilder("step", jobRepository)
                .<ElasticsearchServiceDTO, ElasticsearchServiceDTO>chunk(10, transactionManager)
                .reader(readerJobReflectData())
                .writer(elasticsearchItemWriter())
                .build();
    }

    /**
     * Job definition that includes the step of reading and writing data.
     *
     * @return Job
     */
    @Bean
    public Job importDataJob() {
        return new JobBuilder("importDataJob", jobRepository)
                .start(step())
                .build();
    }


    // chỗ này là khai báo thời gian chạy job (tự động chạy 30s)
    @Scheduled(cron = "*/30 * * * * *")
    public void performJobReflectData() throws Exception {

        listenerJobReflectData().beforeJob(null);

        JobParameters param = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        // Khởi tạo job import data
        JobExecution execution = jobLauncher.run(importDataJob(), param);
        // tiến hành chạy job
        listenerJobReflectData().afterJob(execution);
    }
}
