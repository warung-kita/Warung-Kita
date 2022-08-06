package com.pentagon.warungkita.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {
    private String token;
    private String type = "Bearer";
    private String username;
    private String password;

    public JwtResponse(
            String accessToken,
            String username,
            String email) {
        this.username = username;
        this.password = password;
        this.token = accessToken;
    }

}
