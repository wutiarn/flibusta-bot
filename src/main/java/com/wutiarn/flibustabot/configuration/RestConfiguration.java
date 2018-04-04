package com.wutiarn.flibustabot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

@Configuration
public class RestConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());

        return restTemplate;
    }
}
