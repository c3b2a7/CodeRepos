package me.lolico.samples.cloud.fegin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;


@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignConsumerApplication {

    // @Bean
    public Retryer retryer() {
        // 最多尝试2次即最多重试1次
        return new Retryer.Default(100, SECONDS.toMillis(1), 2);
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApplication.class, args);
    }

    @Slf4j
    // @Component
    static class FeignCustomRequestInterceptor implements RequestInterceptor {

        private final ObjectMapper objectMapper;

        public FeignCustomRequestInterceptor(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public void apply(RequestTemplate template) {
            if (Request.HttpMethod.GET.toString().equals(template.method()) && template.body() != null) {
                //feign 不支持GET方法传输POJO 转换成json，再换成query
                try {
                    Map<String, Collection<String>> map = objectMapper.readValue(template.bodyTemplate(), new TypeReference<Map<String, Collection<String>>>() {

                    });
                    template.body("");
                    template.queries(map);
                } catch (IOException e) {
                    log.error("cause exception", e);
                }
            }
        }
    }
}
