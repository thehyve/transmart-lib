package org.transmartproject.proxy.config;

import org.apache.http.entity.ContentType;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.transmartproject.proxy.security.CurrentUser;

@Configuration
public class RestTemplateConfiguration implements RestTemplateCustomizer {

    @Bean
    public ClientHttpRequestInterceptor restTemplateRequestInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + CurrentUser.getAccessToken());
            request.getHeaders().add("Accept", ContentType.APPLICATION_JSON.getMimeType());
            return execution.execute(request, body);
        };
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(restTemplateRequestInterceptor());
    }

}
