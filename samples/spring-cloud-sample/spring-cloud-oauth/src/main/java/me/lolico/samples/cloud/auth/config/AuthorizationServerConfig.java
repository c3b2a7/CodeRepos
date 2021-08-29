package me.lolico.samples.cloud.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.StringUtils;

import java.security.KeyPair;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lolico
 */
@EnableConfigurationProperties(AuthorizationServerProperties.class)
@EnableAuthorizationServer
@Configuration
@Import(TokenStoreConfiguration.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        if (StringUtils.hasText(authorizationServerProperties.getTokenKeyAccess())) {
            security.tokenKeyAccess(authorizationServerProperties.getTokenKeyAccess());
        }
        if (StringUtils.hasText(authorizationServerProperties.getCheckTokenAccess())) {
            security.checkTokenAccess(authorizationServerProperties.getCheckTokenAccess());
        }
        if (StringUtils.hasText(authorizationServerProperties.getRealm())) {
            security.realm(authorizationServerProperties.getRealm());
        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(inMemoryClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices());
    }

    @Bean
    public ClientDetailsService inMemoryClientDetailsService() throws Exception {
        // JdbcClientDetailsService inMemoryClientDetailsService = new JdbcClientDetailsService(dataSource);
        // inMemoryClientDetailsService.setPasswordEncoder(passwordEncoder);
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        builder.withClient("test_client")
                .secret(passwordEncoder.encode("test_secret"))
                .accessTokenValiditySeconds(60 * 60 * 12)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 7)
                .redirectUris("http://localhost:10010/login")
                .scopes("all")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .autoApprove(true);
        builder.withClient("gateway").secret(passwordEncoder.encode("gateway"))
                .redirectUris("http://localhost:8080/login").scopes("all")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
        return builder.build();
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices() throws Exception {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 令牌存储策略
        tokenServices.setTokenStore(tokenStore);
        // 支持刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // 不重用刷新令牌
        tokenServices.setReuseRefreshToken(false);
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        // 令牌默认有效期7小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 7);
        // 刷新令牌默认有效期7天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        // 客户端详情
        tokenServices.setClientDetailsService(inMemoryClientDetailsService());
        return tokenServices;
    }

    @Bean
    @ConditionalOnMissingBean(JwtAccessTokenConverter.class)
    public JwtAccessTokenConverter jwtAccessTokenConverter(KeyPair keyPair) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair);
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
        converter.setAccessTokenConverter(accessTokenConverter);
        return converter;
    }

    static class SubjectAttributeUserTokenConverter implements UserAuthenticationConverter {
        static final String SUBJECT = "sub";

        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put(SUBJECT, authentication.getName());
            if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            }
            return response;
        }

        @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
            if (map.containsKey(SUBJECT)) {
                Object principal = map.get(SUBJECT);
                Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
                return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
            }
            return null;
        }

        private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
            if (!map.containsKey(AUTHORITIES)) {
                return null;
            }
            Object authorities = map.get(AUTHORITIES);
            if (authorities instanceof String) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
            }
            if (authorities instanceof Collection) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                        .collectionToCommaDelimitedString((Collection<?>) authorities));
            }
            throw new IllegalArgumentException("Authorities must be either a String or a Collection");
        }
    }
}
