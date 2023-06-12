package com.maxcmart.mcm_auth_service.controllers;

import com.maxcmart.mcm_auth_service.models.Message;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class APIController {


    @GetMapping
    public Map<String, Object>  currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        System.out.println("TTTTOOOOOO:" + oAuth2AuthenticationToken);
        System.out.println("TTTTOOOOOO:" + oAuth2AuthenticationToken.getPrincipal().getAttributes());
        System.out.println("TTTTOOOOOO:" + oAuth2AuthenticationToken.getPrincipal().getAuthorities());
        System.out.println("TTTTOOOOOO:" + oAuth2AuthenticationToken.getCredentials().toString());
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }
    @GetMapping(value = "/public")
    public Message publicEndpoint() {
        return new Message("All good. You DO NOT need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/private")
    public Message privateEndpoint() {
        return new Message("All good. You can see this because you are Authenticated.");
    }

    @GetMapping(value = "/private-scoped")
    public Message privateScopedEndpoint() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }
}
