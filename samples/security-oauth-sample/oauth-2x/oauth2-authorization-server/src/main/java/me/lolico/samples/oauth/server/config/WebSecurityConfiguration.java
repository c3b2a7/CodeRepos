package me.lolico.samples.oauth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author lolico
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder passwordEncoder = userDetailsServicePasswordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("lolico").password("leisure.")
                        .passwordEncoder(passwordEncoder::encode)
                        .authorities("ROLE_ADMIN", "ROLE_USER", "READ", "WRITE", "CREATE", "DELETE")
                        .build(),
                User.withUsername("user").password("user")
                        .passwordEncoder(passwordEncoder::encode)
                        .authorities("ROLE_USER", "READ", "WRITE", "DELETE")
                        .build());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().permitAll();
    }

    @Bean
    public PasswordEncoder userDetailsServicePasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
