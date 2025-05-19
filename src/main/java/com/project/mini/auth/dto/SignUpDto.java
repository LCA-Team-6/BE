package com.project.mini.auth.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String name;
    private String password;
}