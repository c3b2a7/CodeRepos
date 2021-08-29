package me.lolico.samples.cloud.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.lolico.samples.cloud.common.api.HelloService;
import me.lolico.samples.cloud.common.model.dto.UserDTO;
import me.lolico.samples.cloud.common.model.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Lolico Li
 */
@Slf4j
@RefreshScope
@RestController
public class HelloController implements HelloService {

    @Value("${from}")
    private String from;

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("test")
    public ResponseEntity<Object> from() {
        Map<String, Object> instances = new HashMap<>();
        instances.put("eureka-server", discoveryClient.getInstances("eureka-server"));
        instances.put("config-server", discoveryClient.getInstances("config-server"));
        instances.put("api-gateway", discoveryClient.getInstances("api-gateway"));
        instances.put("cloud-service", discoveryClient.getInstances("cloud-service"));

        Map<String, Object> map = new HashMap<>();
        map.put("from", from);
        map.put("instances", instances);
        map.put("services", discoveryClient.getServices());
        map.put("description", discoveryClient.description());

        return ResponseEntity.ok(map);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Object> hello(@RequestParam(defaultValue = "UNKNOWN") String name) {

        int sleepTime = new Random().nextInt(3000);
        log.info("Sleeping: {}ms", sleepTime);
        Thread.sleep(sleepTime);

        UserVO userVO = new UserVO();
        userVO.setUsername(name);

        return ResponseEntity.ok(userVO);
    }

    @Override
    public ResponseEntity<Object> hello(String name, Integer age) {

        UserVO userVO = new UserVO();
        userVO.setUsername(name);
        userVO.setAge(age);

        return ResponseEntity.ok(userVO);
    }

    @Override
    public ResponseEntity<Object> hello(UserDTO userDTO) {

        UserVO userVO = new UserVO();
        userVO.setUsername(userDTO.getUsername());
        userVO.setAge(userDTO.getAge());

        return ResponseEntity.ok(userVO);

    }

}
