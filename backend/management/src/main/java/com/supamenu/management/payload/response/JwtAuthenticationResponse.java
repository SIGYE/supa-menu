package com.supamenu.management.payload.response;

import com.supamenu.management.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;

    public JwtAuthenticationResponse(String accessToken, User user){
        this.accessToken = accessToken;
        this.user = user;
    }
}
