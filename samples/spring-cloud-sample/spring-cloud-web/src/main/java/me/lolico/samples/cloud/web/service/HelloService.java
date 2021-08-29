package me.lolico.samples.cloud.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class HelloService {

    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;

    public HelloService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @HystrixCommand(fallbackMethod = "helloServiceFallback")
    public Object helloService() {
        ResponseEntity<Object> entity = restTemplate.getForEntity("http://cloud-service/hello", Object.class);
        Object body = entity.getBody();
        if (body instanceof Map) {
            try {
                body = objectMapper.writeValueAsString(body);
            } catch (JsonProcessingException e) {
                log.warn("Cannot write unknown-type object as raw json, falling back to raw response", e);
            }
        }
        return body;
    }

    private String helloServiceFallback(Throwable throwable) {
        log.warn("Requests hello-service error. service has fallback", throwable);
        return "error";
    }
    //org.springframework.web.client.ResourceAccessException: I/O error on GET request for "http://CLOUD-SERVICE/hello": Connection refused: connect; nested exception is java.net.ConnectException: Connection refused: connect
    //com.netflix.hystrix.exception.HystrixTimeoutException
}
