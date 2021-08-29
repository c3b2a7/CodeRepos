package me.lolico.samples.security.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorizationServerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void requestTokenWhenUsingPasswordGrantTypeThenOk() throws Exception {
        mvc.perform(post("/oauth/token")
                .param("grant_type", "password")
                .param("username", "user")
                .param("password", "user")
                .header(HttpHeaders.AUTHORIZATION, withBasicAuthorize("oauth-client", "oauth-client")))
                .andExpect(status().isOk());
    }

    @Test
    public void requestJwkSetWhenUsingDefaultsThenOk() throws Exception {
        mvc.perform(get("/oauth/keys"))
                .andExpect(status().isOk());
    }

    private String withBasicAuthorize(String clientId, String clientSecret) {
        return "Basic " + HttpHeaders.encodeBasicAuth(clientId, clientSecret, null);
    }
}
