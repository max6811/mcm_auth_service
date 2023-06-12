package com.maxcmart.mcm_auth_service.utils.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@Builder
public class ValidateTokenResponse {
    public String accessToken;

    public String message;
    public String tokenType;
    public long expiresIn;
    public boolean success;

    public UUID userId;

    public String role;

}
