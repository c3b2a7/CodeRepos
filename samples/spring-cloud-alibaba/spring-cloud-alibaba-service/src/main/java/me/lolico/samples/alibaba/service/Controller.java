package me.lolico.samples.alibaba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RefreshScope
@RestController
public class Controller {

    @Value("${value}")
    private String value;
    @Value("${spring.application.name}")
    private String application;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public ResponseEntity<?> index() {
        String body = restTemplate.getForObject("http://{application}/test", String.class, application);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("test: " + value);
    }
}
