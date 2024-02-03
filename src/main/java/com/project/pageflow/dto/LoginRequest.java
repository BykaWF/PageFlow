package com.project.pageflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "username mandatory")
    private String username;
    @NotBlank(message = "password mandatory")
    private String password;
}

