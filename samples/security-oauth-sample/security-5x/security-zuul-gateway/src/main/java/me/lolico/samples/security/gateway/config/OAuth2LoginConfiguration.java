package me.lolico.samples.security.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author lolico
 */
@EnableWebSecurity
public class OAuth2LoginConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .oauth2Login()
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
        // @formatter:on
    }
}
