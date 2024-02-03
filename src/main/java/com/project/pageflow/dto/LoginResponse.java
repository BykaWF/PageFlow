package com.project.pageflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class LoginResponse {
    @NotBlank(message = "token mandatory")
    private String token;
    @NotBlank(message = "token type mandatory")
    private String tokenType = "Bearer ";

    public LoginResponse(String token) {
        this.token = token;
    }
}
