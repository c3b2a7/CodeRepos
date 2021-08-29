package me.lolico.samples.cloud.fegin.service;

import me.lolico.samples.cloud.common.api.HelloService;
import me.lolico.samples.cloud.common.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * {@link FeignClient} 会自动注册成bean，无需使用{@link Component}.
 */
@FeignClient(value = "cloud-service", fallback = WebService.Fallback.class)
public interface WebService extends HelloService {

    @Component
    class Fallback implements WebService {

        @Override
        public ResponseEntity<Object> hello(String name) {
            return ResponseEntity.ok(name);
        }

        @Override
        public ResponseEntity<Object> hello(String name, Integer age) {
            return ResponseEntity.ok(name);
        }

        @Override
        public ResponseEntity<Object> hello(UserDTO userDTO) {
            return ResponseEntity.ok(userDTO);
        }
    }
}
