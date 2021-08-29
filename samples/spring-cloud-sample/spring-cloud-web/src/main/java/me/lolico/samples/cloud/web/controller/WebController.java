package me.lolico.samples.cloud.web.controller;

import lombok.extern.slf4j.Slf4j;
import me.lolico.samples.cloud.web.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lolico Li
 */
@Slf4j
@RestController
public class WebController {

    final HelloService helloService;

    public WebController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/consumer")
    public Object index(String name) {
        long start = System.currentTimeMillis();
        Object s = helloService.helloService();
        long end = System.currentTimeMillis();
        log.info("Spend time: {}ms", end - start);
        return s;
    }
}
