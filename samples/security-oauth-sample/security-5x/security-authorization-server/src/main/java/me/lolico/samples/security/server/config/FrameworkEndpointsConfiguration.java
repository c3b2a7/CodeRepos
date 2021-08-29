package me.lolico.samples.security.server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author lolico
 */
@Configuration
public class FrameworkEndpointsConfiguration {
    @FrameworkEndpoint
    static class UserInfoEndpoint {

        final CheckTokenEndpoint delegator;

        public UserInfoEndpoint(CheckTokenEndpoint delegator) {
            this.delegator = delegator;
        }

        @PostMapping("/oauth/userinfo")
        @ResponseBody
        public Map<String, ?> getUserInfoViaPost(@RequestParam(value = "access_token") String accessToken) {
            return delegator.checkToken(accessToken);
        }

        @GetMapping("/oauth/userinfo")
        @ResponseBody
        public Map<String, ?> getUserInfoViaGet(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
            if (token.contains("Bearer")) {
                token = token.replace("Bearer", "").trim();
            }
            return delegator.checkToken(token);
        }

        @ExceptionHandler(InvalidTokenException.class)
        public ResponseEntity<OAuth2Exception> handleException(Exception ex) throws Exception {
            return delegator.handleException(ex);
        }
    }

    @FrameworkEndpoint
    static class JwkSetEndpoint {
        final KeyPair keyPair;

        JwkSetEndpoint(KeyPair keyPair) {
            this.keyPair = keyPair;
        }

        @GetMapping("/oauth/keys")
        @ResponseBody
        public Map<String, ?> getKey() {
            RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
            RSAKey key = new RSAKey.Builder(publicKey).build();
            return new JWKSet(key).toJSONObject();
        }
    }
}
