package com.supamenu.management.service;


import com.supamenu.management.payload.response.JwtAuthenticationResponse;

public interface AuthService {
    JwtAuthenticationResponse login(String email, String password);
}
