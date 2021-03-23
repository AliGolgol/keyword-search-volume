package com.sellics.assignment.amazonsearchvolume.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AmazonAPI {

    @Value("${sellics.amazon.host}")
    private String host;

    @Value("${sellics.amazon.api}")
    private String api;

    @Value("${sellics.amazon.timeout}")
    private String timeout;

    @Value("${sellics.amazon.url}")
    private String url;
}
