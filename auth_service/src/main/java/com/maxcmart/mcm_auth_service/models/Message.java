package com.maxcmart.mcm_auth_service.models;

import org.springframework.web.bind.annotation.RestController;

public class Message {
    private final String message;

    public Message(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
