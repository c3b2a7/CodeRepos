package me.lolico.samples.security.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author lolico
 */
@EnableWebFluxSecurity
public class OAuth2LoginConfiguration {
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
                .authorizeExchange()
                    .anyExchange().authenticated()
                    .and()
                .oauth2Login()
                    .and()
                .logout();
        // @formatter:on
        return http.build();
    }
}
