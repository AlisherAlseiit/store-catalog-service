package com.example.storecatalogservice;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableHystrix
public class StoreCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreCatalogServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);

        		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        		credentialsProvider.setCredentials(AuthScope.ANY,
        				new UsernamePasswordCredentials("rest-client", "p@ssword"));

        		HttpClient client = HttpClientBuilder
        				.create()
        				.setDefaultCredentialsProvider(credentialsProvider)
        				.build();

        		requestFactory.setHttpClient(client);

        return new RestTemplate(requestFactory);
    }

}
