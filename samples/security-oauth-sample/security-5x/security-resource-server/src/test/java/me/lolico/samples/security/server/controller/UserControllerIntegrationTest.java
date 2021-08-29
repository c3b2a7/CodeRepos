package me.lolico.samples.security.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author lolico
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class UserControllerIntegrationTest {

    String noScopesToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsb2xpY28iLCJleHAiOjMxNjQyNDU4ODAsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiLCJSRUFEIiwiV1JJVEUiLCJDUkVBVEUiLCJERUxFVEUiXSwianRpIjoiMDFkOThlZWEtNjc0MC00OGRlLTk4ODAtYzM5ZjgyMGZiNzVlIiwiY2xpZW50X2lkIjoib2F1dGgyLWNsaWVudCIsInNjb3BlIjpbIm5vbmUiXX0.BstF67tCNUlJkrcnpSMcTtxuACQJwo20OpRdTC2PNHKY3JE9MdEdIilTOJvmKFL8FI8KrW0FamZAuFq8ZKv41qAXq1r0rti037Kw8Nhyw48GhaaT1HxQTgbMugtpSpsYasRhIsSXn7bswfDFkySKjE3jpdDWzQpEp4SeOru4EyxMijINb5zMI9nRja5ndlbKfenelPYIlvFZcs_-dwEXhhgiwv3zhlWBTNsIQ7PlTAhLQYc1Ft3HPV1uHfRSS_JRPHQKbxscFaSmrPhDueoQabFU3H8u2bHFo3jw3BYuvBVQ7WbmrrFE4wlRkQIvBZP5lOhCTcu25roto31jCY7URg";
    String userInfoReadToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsb2xpY28iLCJleHAiOjMxNjQyNDU4ODAsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiLCJSRUFEIiwiV1JJVEUiLCJDUkVBVEUiLCJERUxFVEUiXSwianRpIjoiMDFkOThlZWEtNjc0MC00OGRlLTk4ODAtYzM5ZjgyMGZiNzVlIiwiY2xpZW50X2lkIjoib2F1dGgyLWNsaWVudCIsInNjb3BlIjpbInVzZXJfaW5mby5yZWFkIl19.dPcLzR4N5-znx59Nxl1eqnYwjkpEN7zK5pUv46cboHYpY_wufIrTc8K3z4XoKCF7ggahblOVQEhIhAS40vR7kybRhup0elRd_LOK-xQWIvTtVftApnYylZnEOF0gx0s6T0La5Ubb4YH2R6b1bra5xpxw88RszfbWETLJAjlEzBP5A6EmQ9C4Sw9Q_j0gTsN3BaxXbnaGKLT6hYvOUGw79hSMwAYqxlIZMwa_OlKH8C7KjSdims0ManoiTXkh7iS00pZaIwMqPSU1--FBv0QQfwpTqcMDkZpGF-zT0XV8OH4G6CYHFTRcHFvJlBZ7HXcji2ohXAd4suL_HLZKjlIBEg";
    String allToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsb2xpY28iLCJleHAiOjMxNjQyNDU4ODAsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiLCJSRUFEIiwiV1JJVEUiLCJDUkVBVEUiLCJERUxFVEUiXSwianRpIjoiMDFkOThlZWEtNjc0MC00OGRlLTk4ODAtYzM5ZjgyMGZiNzVlIiwiY2xpZW50X2lkIjoib2F1dGgyLWNsaWVudCIsInNjb3BlIjpbImFsbCJdfQ.GqyvOUD6NoSpIS1wsD_5ZoZsBU9h0hSewzXHQVX_N6sRbdCGrajsEBzOSf1-neaCe_dS0sXlT46zeeF9kRuSjaA7EDHRF05WXxaEPG6jjRjtBLPYAa_C3jMf0hnlJFsIapXMG7oDN0Kb1gunVLzW1ZMVw0JLzbHOG_2eb7v_HVKcml50umdgXPABh40XplEABzm9hw09zaomi7kGU8P3ZkHq5xIbZhjVKCZ9SCwnDsasxbLNvE94wVafDv4kJlqssO9e6iuj6QI9qO3sl-66XjtR5SUv2NWHmBMLkvK27axjI7T8iiYX7Ty9IC7DuBA6TiEKnJk1snOnBsnWpmt3bg";

    @Autowired
    MockMvc mvc;

    @Test
    void performWhenValidBearerTokenThenAllows() throws Exception {
        this.mvc.perform(get("/user").with(bearerToken(this.noScopesToken)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, lolico!")));
    }

    // -- tests with scopes

    @Test
    void performWhenValidBearerTokenThenScopedRequestsAlsoWork() throws Exception {
        this.mvc.perform(get("/user/read").with(bearerToken(this.userInfoReadToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("lolico")));

        this.mvc.perform(get("/user/read").with(bearerToken(this.allToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("lolico")));
    }

    @Test
    void performWhenInsufficientlyScopedBearerTokenThenDeniesScopedMethodAccess() throws Exception {
        this.mvc.perform(get("/user/read").with(bearerToken(this.noScopesToken)))
                .andExpect(status().isForbidden())
                .andExpect(header().string(HttpHeaders.WWW_AUTHENTICATE,
                        containsString("Bearer error=\"insufficient_scope\"")));
    }

    @TestConfiguration
    static class TestConfig {

        private final OAuth2ResourceServerProperties.Jwt properties;

        TestConfig(OAuth2ResourceServerProperties properties) {
            this.properties = properties.getJwt();
        }

        @Bean
        JwtDecoder jwtDecoderByPublicKeyValue() throws Exception {
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(getKeySpec(this.properties.readPublicKey())));
            return NimbusJwtDecoder.withPublicKey(publicKey)
                    .signatureAlgorithm(SignatureAlgorithm.from(this.properties.getJwsAlgorithm())).build();
        }

        private byte[] getKeySpec(String keyValue) {
            keyValue = keyValue.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
            return Base64.getMimeDecoder().decode(keyValue);
        }
    }

    static class BearerTokenRequestPostProcessor implements RequestPostProcessor {
        private final String token;

        BearerTokenRequestPostProcessor(String token) {
            this.token = token;
        }

        @Override
        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
            request.addHeader("Authorization", "Bearer " + this.token);
            return request;
        }
    }

    static BearerTokenRequestPostProcessor bearerToken(String token) {
        return new BearerTokenRequestPostProcessor(token);
    }
}
