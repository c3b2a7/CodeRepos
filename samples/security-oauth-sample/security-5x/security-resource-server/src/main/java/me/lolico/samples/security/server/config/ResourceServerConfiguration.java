package me.lolico.samples.security.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lolico
 */
@EnableWebSecurity
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .cors()
                    .configurationSource(corsConfigurationSource())
                    .and()
                .authorizeRequests()
                    .mvcMatchers("/user/read").access("hasAnyAuthority('SCOPE_user_info.read','SCOPE_all')")
                    .and()
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .oauth2ResourceServer()
                    .jwt()
                    .jwtAuthenticationConverter(new UsernameJwtAuthenticationConverter());
        // @formatter:on
    }

    static class UsernameJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
        private final static String SUB = "user_name";
        private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter
                = new JwtGrantedAuthoritiesConverter();

        @Override
        public AbstractAuthenticationToken convert(Jwt source) {
            Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(source);
            String sub = Optional.ofNullable(source.getClaimAsString(SUB)).orElseGet(source::getSubject);
            return new JwtAuthenticationToken(source, authorities, sub);
        }

        public void setJwtGrantedAuthoritiesConverter(Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
            this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
        }
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);
        configuration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
