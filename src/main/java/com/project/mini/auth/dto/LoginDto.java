package com.project.mini.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그인 요청 dto", example = """
{
  "email": "test@test.com",
  "password": "1234"
}
""")
public class LoginDto {
    private String email;
    private String password;
}
