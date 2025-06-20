package com.supamenu.management.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Getter
public class LoginDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
