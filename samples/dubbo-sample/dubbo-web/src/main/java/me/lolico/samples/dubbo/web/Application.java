package me.lolico.samples.dubbo.web;

import me.lolico.samples.dubbo.common.CalculateService;
import me.lolico.samples.dubbo.common.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @DubboReference(check = false)
    private HelloService helloService;

    @DubboReference(check = false)
    private CalculateService calculateService;

    @GetMapping("/say")
    public ResponseEntity<Object> say(String name) {
        String sayHello = helloService.sayHello(name);
        return ResponseEntity.ok(sayHello);
    }

    @GetMapping("/compute")
    public ResponseEntity<Object> compute(int a, int b) {
        return ResponseEntity.ok(calculateService.add(a, b));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
