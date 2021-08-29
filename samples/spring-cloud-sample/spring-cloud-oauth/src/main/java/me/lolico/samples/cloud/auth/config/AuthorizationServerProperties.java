package me.lolico.samples.cloud.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lolico
 */
@ConfigurationProperties("oauth2.authorization")
public class AuthorizationServerProperties {

    /**
     * Spring Security access rule for the check token endpoint (e.g. a SpEL expression
     * like "isAuthenticated()") . Default is empty, which is interpreted as "denyAll()"
     * (no access).
     */
    private String checkTokenAccess;

    /**
     * Spring Security access rule for the token key endpoint (e.g. a SpEL expression like
     * "isAuthenticated()"). Default is empty, which is interpreted as "denyAll()" (no
     * access).
     */
    private String tokenKeyAccess;

    /**
     * Realm name for client authentication. If an unauthenticated request comes in to the
     * token endpoint, it will respond with a challenge including this name.
     */
    private String realm;

    private TokenStoreType tokenStoreType = TokenStoreType.JwtTokenStore;

    /**
     * Jwt-specific configuration properties
     */
    private Jwt jwt = new Jwt();


    public String getCheckTokenAccess() {
        return this.checkTokenAccess;
    }

    public void setCheckTokenAccess(String checkTokenAccess) {
        this.checkTokenAccess = checkTokenAccess;
    }

    public String getTokenKeyAccess() {
        return this.tokenKeyAccess;
    }

    public void setTokenKeyAccess(String tokenKeyAccess) {
        this.tokenKeyAccess = tokenKeyAccess;
    }

    public String getRealm() {
        return this.realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public TokenStoreType getTokenStoreType() {
        return tokenStoreType;
    }

    public void setTokenStoreType(TokenStoreType tokenStoreType) {
        this.tokenStoreType = tokenStoreType;
    }

    /**
     * Configuration properties for Authorization Server Jwt configuration
     */
    public static class Jwt {

        /**
         * The signing key of the JWT token. Can either be a symmetric secret or
         * PEM-encoded RSA private key.
         */
        private String keyValue;

        /**
         * The location of the private key.
         */
        private String privateKeyLocation;

        /**
         * The location of the public key.
         */
        private String publicKeyLocation;

        /**
         * The location of the key store.
         */
        private String keyStore;

        /**
         * The key store's password
         */
        private String keyStorePassword;

        /**
         * The alias of the key from the key store
         */
        private String keyAlias;

        /**
         * The password of the key from the key store
         */
        private String keyPassword;

        public String getKeyValue() {
            return this.keyValue;
        }

        public void setKeyValue(String keyValue) {
            this.keyValue = keyValue;
        }

        public String getKeyStore() {
            return keyStore;
        }

        public void setKeyStore(String keyStore) {
            this.keyStore = keyStore;
        }

        public String getKeyStorePassword() {
            return keyStorePassword;
        }

        public void setKeyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
        }

        public String getKeyAlias() {
            return keyAlias;
        }

        public void setKeyAlias(String keyAlias) {
            this.keyAlias = keyAlias;
        }

        public String getKeyPassword() {
            return keyPassword;
        }

        public void setKeyPassword(String keyPassword) {
            this.keyPassword = keyPassword;
        }

        public String getPrivateKeyLocation() {
            return privateKeyLocation;
        }

        public void setPrivateKeyLocation(String privateKeyLocation) {
            this.privateKeyLocation = privateKeyLocation;
        }

        public String getPublicKeyLocation() {
            return publicKeyLocation;
        }

        public void setPublicKeyLocation(String publicKeyLocation) {
            this.publicKeyLocation = publicKeyLocation;
        }
    }

}
