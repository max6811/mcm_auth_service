package com.maxcmart.mcm_auth_service.security.oauth2.user;

import com.maxcmart.mcm_auth_service.enums.AuthProvider;
import com.maxcmart.mcm_auth_service.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;



public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())){
            return new GithubOauth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
