package me.lolico.samples.cloud.fegin.controller;

import lombok.extern.slf4j.Slf4j;
import me.lolico.samples.cloud.common.model.dto.UserDTO;
import me.lolico.samples.cloud.fegin.service.WebService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class FeignController {

    final WebService webService;

    public FeignController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping("/feign")
    public ResponseEntity<Object> consumer(String name) {
        long start = System.currentTimeMillis();
        ResponseEntity<Object> s = webService.hello(name);
        long end = System.currentTimeMillis();
        log.info("Spend time: {}ms", end - start);
        return s;
    }

    @GetMapping("/feign1")
    public ResponseEntity<Object> consumer(@RequestHeader String name,
                                           @RequestHeader Integer age) {
        long start = System.currentTimeMillis();
        ResponseEntity<Object> s = webService.hello(name, age);
        long end = System.currentTimeMillis();
        log.info("Spend time: {}ms", end - start);
        return s;
    }

    @PostMapping("/feign")
    public ResponseEntity<Object> consumer(@RequestBody UserDTO userDTO) {
        long start = System.currentTimeMillis();
        ResponseEntity<Object> s = webService.hello(userDTO);
        long end = System.currentTimeMillis();
        log.info("Spend time: {}ms", end - start);
        return s;
    }
}
