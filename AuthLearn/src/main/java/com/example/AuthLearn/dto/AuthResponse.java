package com.example.AuthLearn.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType;

    public AuthResponse(String accessToken){
        this.accessToken = accessToken;
    }


}
