package com.maxcmart.mcm_auth_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxcmart.mcm_auth_service.enums.AuthProvider;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
//@Getter
public class User {
    @Id
    private String id;

    private String name;
    private String email;
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password = null;
    private AuthProvider provider;

    private String providerId;
}
