package me.lolico.samples.oauth.gateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author lolico
 */
@EnableOAuth2Sso
@Configuration
public class Oauth2ClientSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        OAuth2SsoProperties properties = getApplicationContext().getBean(OAuth2SsoProperties.class);
        // @formatter:off
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/authorization-server/**").permitAll()
                .antMatchers(properties.getLoginPath()).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
        // @formatter:on
    }
}
