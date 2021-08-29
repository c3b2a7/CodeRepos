package me.lolico.samples.security.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

/**
 * @author lolico
 */
@Controller
public class AuthorizedController {
    @Value("${messages.base-uri}")
    private String messagesBaseUri;

    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/authorize", params = "grant_type=authorization_code")
    public String authorization_code_grant(Model model) {
        Map<?, ?> messages = retrieveMessages("oauth-client-auth-code");
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping(value = "/authorize", params = "grant_type=client_credentials")
    public String client_credentials_grant(Model model) {
        Map<?, ?> messages = retrieveMessages("oauth-client-client-creds");
        model.addAttribute("messages", messages);
        return "index";
    }

    @PostMapping(value = "/authorize", params = "grant_type=password")
    public String password_grant(Model model) {
        Map<?, ?> messages = retrieveMessages("oauth-client-password");
        model.addAttribute("messages", messages);
        return "index";
    }

    private Map<?, ?> retrieveMessages(String clientRegistrationId) {
        return this.webClient
                .get()
                .uri(this.messagesBaseUri)
                .attributes(clientRegistrationId(clientRegistrationId))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
