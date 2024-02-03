package com.project.pageflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentInfoResponse {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String rollNumber;
}
