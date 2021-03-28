package edu.sampleproject.movielens.configurations;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    private final static String SCHEME = "http";

    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private int port;

    @Bean
    public RestHighLevelClient getESRestHighLevelClient() {
        HttpHost node1 = new HttpHost(host, port, SCHEME);
        return new RestHighLevelClient(RestClient.builder(node1));
    }
}
