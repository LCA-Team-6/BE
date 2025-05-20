package com.project.mini.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpDto {
    @NotBlank(message = "아이디는 필수적으로 입력해 주세요.")
    private String email;
    @NotBlank(message = "닉네임은 필수적으로 입력해 주세요.")
    private String name;
    @NotBlank(message = "비밀번호는 필수적으로 입력해 주세요.")
    private String password;
}