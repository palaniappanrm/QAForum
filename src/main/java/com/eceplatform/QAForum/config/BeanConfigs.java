package com.eceplatform.QAForum.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.eceplatform.QAForum.dto.UserDTO;
import com.eceplatform.QAForum.util.ThreadLocalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.IOException;
import java.time.LocalDate;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Configuration
public class BeanConfigs {

//    @Value("${s3.access_key_id}")
//    private String access_key_id;
//
//    @Value("${s3.secret_key_id}")
//    private String secret_key_id;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("userThreadLocal")
    public ThreadLocalUtil<UserDTO> userThreadLocalUtil() {
        return new ThreadLocalUtil<UserDTO>();
    }

    @Bean
    public AmazonS3 s3Client() {
//        BasicAWSCredentials awsCreds = new BasicAWSCredentials(access_key_id, secret_key_id);
        return AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("ap-south-1")
                .build();
    }

    @Bean
    public ObjectMapper mapper(){
        return new ObjectMapper();
    }

    @Bean
    public RestHighLevelClient esClient() throws IOException {

        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("root", "Socure1!2@"));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("https://search-my-es-ke5pgmkmje7euxsitog2csre7e.ap-south-1.es.amazonaws.com"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(
                                    HttpAsyncClientBuilder httpClientBuilder) {
                                return httpClientBuilder
                                        .setDefaultCredentialsProvider(credentialsProvider);
                            }
                        })
        );

        // run the below as a scheduled job

        GetIndexRequest getIndexRequest = new GetIndexRequest("qa_forum_content_" + LocalDate.now().getYear());

        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

        if (!exists) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("qa_forum_content_" + LocalDate.now().getYear());
            createIndexRequest.settings(Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 0)
                    .loadFromSource(Strings.toString(jsonBuilder()
                            .startObject()
                            .startObject("analysis")
                            .startObject("analyzer")
                            .startObject("default")
                            .field("type", "stop")
                            .endObject()
                            .endObject()
                            .endObject()
                            .endObject()), XContentType.JSON)
            );
            createIndexRequest.mapping(jsonBuilder()
                    .startObject()
                    .startObject("properties")

                    .startObject("esContentType")
                    .field("type", "text")
                    .endObject()

                    .startObject("dbId")
                    .field("type", "long")
                    .field("index", false)
                    .endObject()

                    .startObject("content")
                    .field("type", "text")
                    .endObject()

                    .startObject("likes")
                    .field("type", "long")
                    .field("index", false)
                    .endObject()

                    .endObject()
                    .endObject()

            );
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }

        return restHighLevelClient;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.eceplatform.QAForum.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
