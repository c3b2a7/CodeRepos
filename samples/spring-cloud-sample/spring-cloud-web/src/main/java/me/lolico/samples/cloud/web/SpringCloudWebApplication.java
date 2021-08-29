package me.lolico.samples.cloud.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringCloudApplication
public class SpringCloudWebApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(@Value("#{environment['ribbon.ReadTimeout'] ?: 2000}") Integer readTimeout,
                                     @Value("#{environment['ribbon.ConnectTimeout'] ?: 2000}") Integer connectTimeout) {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectTimeout);
        return new RestTemplate(factory);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudWebApplication.class, args);
    }

}
