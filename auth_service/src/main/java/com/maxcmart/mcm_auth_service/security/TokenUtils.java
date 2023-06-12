package com.maxcmart.mcm_auth_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxcmart.mcm_auth_service.configs.AppConfig;
import com.maxcmart.mcm_auth_service.utils.responses.ValidateTokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenUtils {
    private final AppConfig appConfig;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + appConfig.getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject((userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String jwt, HttpServletResponse response) throws IOException {
        SecretKey key = getSecretKey();
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            setResponseForTokenErrors("Invalid token", jwt, HttpStatus.FORBIDDEN.value(), response);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            setResponseForTokenErrors("Expired token", jwt, HttpStatus.UNAUTHORIZED.value(), response);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            setResponseForTokenErrors("Unsupported token", jwt, HttpStatus.FORBIDDEN.value(), response);
        } catch (IllegalArgumentException ex) {
            log.error("JWT Illegal Argument token");
            setResponseForTokenErrors("Illegal Argument token", jwt, HttpStatus.FORBIDDEN.value(), response);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            setResponseForTokenErrors("Invalid signature token", jwt, HttpStatus.FORBIDDEN.value(), response);
        }
        return false;
    }

    public String parserJwt(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    private void setResponseForTokenErrors(
            String message,
            String token,
            int statusCode,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        ValidateTokenResponse.builder()
                                .accessToken(token)
                                .tokenType("Bearer")
                                .expiresIn(0)
                                .success(false)
                                .message(message)
                                .build()
                )
        );
        response.setStatus(statusCode);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(appConfig.getTokenSecret().getBytes(StandardCharsets.UTF_8));
    }
}
