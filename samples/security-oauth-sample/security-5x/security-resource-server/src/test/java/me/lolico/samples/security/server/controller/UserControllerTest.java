package me.lolico.samples.security.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void indexGreetsAuthenticatedUser() throws Exception {
        mvc.perform(get("/user")
                .with(jwt().jwt(jwt -> jwt.subject("lolico"))))
                .andExpect(content().string(is("Hello, lolico!")));
    }

    @Test
    void canReadWithScopeAllAuthority() throws Exception {
        mvc.perform(get("/user/read")
                .with(jwt().jwt(jwt -> jwt.subject("lolico").claim("scope", "all"))))
                .andExpect(jsonPath("$.name", is("lolico")));
    }

    @Test
    void canReadWithScopeUserInfoReadAuthority() throws Exception {
        mvc.perform(get("/user/read")
                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_user_info.read"))))
                .andExpect(jsonPath("$.name", is("user")));
    }

    @Test
    void cannotReadWithoutScopeAuthority() throws Exception {
        mvc.perform(get("/user/read")
                .with(jwt()))
                .andExpect(status().isForbidden());
    }
}
