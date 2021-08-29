package me.lolico.samples.security.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lolico
 */
@Configuration
public class ClientDetailsServiceConfiguration {

    private final DataSource dataSource;

    public ClientDetailsServiceConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // @Bean
    // @ConfigurationProperties("oauth2.client.datasource")
    public DataSource clientDetailsDataSource() {
        // return DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        DataSource dataSource = clientDetailsDataSource();
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(clientDetailsServicePasswordEncoder());
        return clientDetailsService;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder clientDetailsServicePasswordEncoder() {
        String idToEncode = "sha256";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idToEncode, new StandardPasswordEncoder());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());
        return new DelegatingPasswordEncoder(idToEncode, encoders);
    }
}
