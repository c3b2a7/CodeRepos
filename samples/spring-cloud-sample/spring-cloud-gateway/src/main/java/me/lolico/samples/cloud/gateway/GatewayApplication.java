package me.lolico.samples.cloud.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lolico
 */
@EnableZuulProxy
@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    // @Bean
    public ZuulFilter zuulFilter() {
        return new SecurityHeaderFilter();
    }

    private class SecurityHeaderFilter extends ZuulFilter {
        // /oauth/check_token使用RemoteTokenService，需要保留Authorization头
        // /oauth/token默认将client id/secret放在request parameter中，可以不带Authorization头
        // /oauth/authorize 本就不检查Authorization
        @Override
        public String filterType() {
            return "pre";
        }

        @Override
        public int filterOrder() {
            return 0;
        }

        @Override
        public boolean shouldFilter() {
            return false;
        }

        @Override
        public Object run() throws ZuulException {
            return null;
        }
    }

    @RefreshScope
    @RestController
    static class TestController {

        @Value("${from}")
        private String from;

        final DiscoveryClient discoveryClient;

        public TestController(DiscoveryClient discoveryClient) {
            this.discoveryClient = discoveryClient;
        }

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
    }

    // @Bean
    // public KeyResolver keyResolver() {
    //     // return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    //     return exchange -> Mono.just(exchange.getRequest().getPath().value()); //uri
    //     // return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId")); //query-param
    // }
    //
    // @Bean
    // public RouterFunction<ServerResponse> routes(Controller controller) {
    //     return RouterFunctions.route(
    //             RequestPredicates.GET("/fallback").and(RequestPredicates.accept(MediaType.ALL)),
    //             controller::helloLolico);
    // }
    //
    // @RefreshScope
    // @Component
    // static class Controller {
    //
    //     @Value("${from:unknown}")
    //     String from;
    //
    //     // @GetMapping("/fallback")
    //     public Mono<ServerResponse> helloLolico(ServerRequest request) {
    //         return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
    //                 .headers(httpHeaders -> httpHeaders.add("lolico", "hello"))
    //                 .body(BodyInserters.fromValue("hello lolico, I'm from " + from));
    //     }
    //
    // }

    // for zuul
    // @Bean
    // @Primary
    // @RefreshScope
    // @ConfigurationProperties("zuul")
    // public ZuulProperties zuulProperties() {
    //     return new ZuulProperties();
    // }
    //
    // @Bean
    // public PatternServiceRouteMapper patternServiceRouteMapper() {
    //     return new PatternServiceRouteMapper(
    //             "(?<service>^.+)-(?<version>v.+$)",
    //             "${version}/${service}");
    // }
}
