package me.lolico.samples.oauth.server.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author lolico
 */
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(AuthorizationServerProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
        implements ApplicationContextAware {

    private final AuthenticationManager authenticationManager;
    private final ClientDetailsService jdbcClientDetailsService;
    private final RedisConnectionFactory redisConnectionFactory;
    private final AuthorizationServerProperties properties;

    private ApplicationContext applicationContext;

    @Value("#{environment['security.oauth2.authorization.allow-form-authentication-for-clients'] ?: false}")
    private boolean allowFormAuthenticationForClients;

    public AuthorizationServerConfiguration(AuthenticationManager authenticationManager, ClientDetailsService jdbcClientDetailsService, RedisConnectionFactory redisConnectionFactory, AuthorizationServerProperties properties) {
        this.authenticationManager = authenticationManager;
        this.jdbcClientDetailsService = jdbcClientDetailsService;
        this.redisConnectionFactory = redisConnectionFactory;
        this.properties = properties;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        if (this.properties.getCheckTokenAccess() != null) {
            security.checkTokenAccess(this.properties.getCheckTokenAccess());
        }
        if (this.properties.getTokenKeyAccess() != null) {
            security.tokenKeyAccess(this.properties.getTokenKeyAccess());
        }
        if (this.properties.getRealm() != null) {
            security.realm(this.properties.getRealm());
        }
        if (this.allowFormAuthenticationForClients) {
            // 应用了一个ClientCredentialsTokenEndpointFilter过滤器用于从表单中获取
            // client_id和client_secret进行认证，可支持Get请求，不建议开启
            // 默认使用BasicAuthenticationFilter认证client_id和client_secret
            security.allowFormAuthenticationForClients();
        }
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 使用Redis存储令牌
        tokenServices.setTokenStore(redisTokenStore());
        // 支持刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // 不重用旧的刷新令牌
        tokenServices.setReuseRefreshToken(false);
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // 增强令牌为jwt格式，默认为uuid
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(jwtAccessTokenConverter()));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        // 令牌默认有效期7小时
        tokenServices.setAccessTokenValiditySeconds(Math.toIntExact(TimeUnit.HOURS.toSeconds(7)));
        // 刷新令牌默认有效期7天
        tokenServices.setRefreshTokenValiditySeconds(Math.toIntExact(TimeUnit.DAYS.toSeconds(7)));
        // 客户端详情服务
        tokenServices.setClientDetailsService(jdbcClientDetailsService);
        return tokenServices;
    }

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        Assert.notNull(this.properties.getJwt().getKeyStore(), "keyStore cannot be null");
        Assert.notNull(this.properties.getJwt().getKeyStorePassword(), "keyStorePassword cannot be null");
        Assert.notNull(this.properties.getJwt().getKeyAlias(), "keyAlias cannot be null");

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        Resource keyStore = this.applicationContext.getResource(this.properties.getJwt().getKeyStore());
        char[] keyStorePassword = this.properties.getJwt().getKeyStorePassword().toCharArray();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStore, keyStorePassword);

        String keyAlias = this.properties.getJwt().getKeyAlias();
        char[] keyPassword = Optional.ofNullable(
                this.properties.getJwt().getKeyPassword())
                .map(String::toCharArray).orElse(keyStorePassword);
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyAlias, keyPassword));

        return converter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
