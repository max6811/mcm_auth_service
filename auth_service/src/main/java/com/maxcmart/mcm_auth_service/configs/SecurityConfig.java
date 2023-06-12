package com.maxcmart.mcm_auth_service.configs;


import com.maxcmart.mcm_auth_service.security.AuthenticationJwtEntryPoint;
import com.maxcmart.mcm_auth_service.security.TokenAuthenticationFilter;
import com.maxcmart.mcm_auth_service.security.oauth2.OAuth2UserService;
import com.maxcmart.mcm_auth_service.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.maxcmart.mcm_auth_service.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.maxcmart.mcm_auth_service.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationJwtEntryPoint())//Verify if to be authenticated
                .and()

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/auth/**", "/oauth2/**").permitAll()
                        .requestMatchers("/api/user/profile").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authEndPoint -> authEndPoint
                                .baseUri("/login/oauth2/authorization")
                                .authorizationRequestRepository(this.cookieOAuth2AuthorizationRequestRepository())
                        )
                        .redirectionEndpoint(redirectEndPoint -> redirectEndPoint
                                .baseUri("/login/oauth2/code/*")
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(this.oAuth2UserService)
                        )
                        .successHandler(this.oAuth2AuthenticationSuccessHandler)
                        .failureHandler(this.oAuth2AuthenticationFailureHandler)
                );

        http.addFilterBefore(this.tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
