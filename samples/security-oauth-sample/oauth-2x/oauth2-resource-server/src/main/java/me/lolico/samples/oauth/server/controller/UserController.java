package me.lolico.samples.oauth.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author lolico
 */
@RequestMapping("user")
@RestController
public class UserController {

    @GetMapping("/read")
    @PreAuthorize("#oauth2.throwOnError(#oauth2.hasAnyScope('user_info.read','read','all'))")
    public ResponseEntity<?> readPrincipal(Principal principal) {
        if (principal instanceof OAuth2Authentication) {
            return ResponseEntity.ok(((OAuth2Authentication) principal).getUserAuthentication());
        }
        return ResponseEntity.ok(principal);
    }

    @GetMapping("/all")
    @PreAuthorize("#oauth2.throwOnError(#oauth2.hasScope('all'))")
    public ResponseEntity<?> principal(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
