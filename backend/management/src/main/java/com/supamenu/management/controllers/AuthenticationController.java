package com.supamenu.management.controllers;

import com.supamenu.management.payload.request.LoginDTO;
import com.supamenu.management.payload.response.ApiResponse;
import com.supamenu.management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/supa/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> login (@Valid @RequestBody LoginDTO dto){
        return ResponseEntity.ok(ApiResponse.success("Login Successful", this.authService.login(dto.getEmail(), dto.getPassword())));
    }
}
