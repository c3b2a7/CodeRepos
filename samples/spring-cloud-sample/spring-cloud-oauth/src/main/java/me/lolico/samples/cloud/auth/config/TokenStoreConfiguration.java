package me.lolico.samples.cloud.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.util.Optional;

/**
 * @author lolico
 */
@Configuration
public class TokenStoreConfiguration {

    private final AuthorizationServerProperties properties;

    public TokenStoreConfiguration(AuthorizationServerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    @Conditional(RedisTokenStoreCondition.class)
    public TokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    @Conditional(JwtTokenStoreCondition.class)
    public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    @ConditionalOnMissingBean(KeyPair.class)
    public KeyPair keyPair() {
        AuthorizationServerProperties.Jwt jwt = properties.getJwt();
        if (StringUtils.hasText(jwt.getKeyStore())) {
            Resource keyStore = new FileSystemResource(jwt.getKeyStore());
            char[] keyStorePassword = jwt.getKeyStorePassword().toCharArray();
            char[] keyPassword = Optional.ofNullable(jwt.getKeyPassword())
                    .map(String::toCharArray).orElse(keyStorePassword);
            KeyStoreKeyFactory factory = new KeyStoreKeyFactory(keyStore, keyStorePassword);
            return factory.getKeyPair(jwt.getKeyAlias(), keyPassword);
        }
        if (StringUtils.hasText(jwt.getPrivateKeyLocation())) {
            try {
                Class<?> helper = Class.forName("org.springframework.security.jwt.crypto.sign.RsaKeyHelper");
                Method method = helper.getDeclaredMethod("parseKeyPair", String.class);
                method.setAccessible(true);
                String privateKey = new String(Files.readAllBytes(Paths.get(jwt.getPrivateKeyLocation())));
                return (KeyPair) method.invoke(null, privateKey);
            } catch (Exception ex) {
                throw new IllegalStateException("Cannot create keypair", ex);
            }
        }
        throw new IllegalStateException("Cannot create keypair");
    }

    private static class JwtTokenStoreCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage
                    .forCondition("OAuth TokenStore Condition");
            Environment environment = context.getEnvironment();
            TokenStoreType tokenStoreType = environment
                    .getProperty("oauth2.authorization.token-store-type", TokenStoreType.class);
            if (tokenStoreType == TokenStoreType.JwtTokenStore) {
                return ConditionOutcome.match(message.foundExactly("token store type is JwtTokenStore"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("token store type is JwtTokenStore").atAll());
        }
    }

    private static class RedisTokenStoreCondition extends JwtTokenStoreCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return ConditionOutcome.inverse(super.getMatchOutcome(context, metadata));
        }
    }
}
