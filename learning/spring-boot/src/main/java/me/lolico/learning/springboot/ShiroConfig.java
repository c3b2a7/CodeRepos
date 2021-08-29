package me.lolico.learning.springboot;

import me.lolico.learning.springboot.common.Constants;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lolico
 */
@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class ShiroConfig {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/captcha", "anon");
        chainDefinition.addPathDefinition("/logout", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/register", "anon");
        chainDefinition.addPathDefinition("/api/test/**", "anon");
        chainDefinition.addPathDefinition("/favicon.ico","anon");
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Constants.Security.ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(Constants.Security.HASH_ITERATIONS);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    // @Bean
    // public DefaultWebSecurityManager securityManager(Collection<Realm> realms) {
    //     DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    //     securityManager.setRealms(realms);
    //     return securityManager;
    // }
    //
    // @Bean
    // public Realm userRealm(UserService userService) {
    //     UserRealm userRealm = new UserRealm(userService);
    //     userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    //     return userRealm;
    // }

}
