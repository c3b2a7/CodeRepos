package me.lolico.samples.security.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;

/**
 * @author lolico
 */
@RequestMapping("user")
@RestController
public class UserController {
    @GetMapping("/read")
    public ResponseEntity<?> principal(Principal principal) {
        return ResponseEntity.ok(Collections.singletonMap("name", principal.getName()));
    }

    @GetMapping
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }
}
