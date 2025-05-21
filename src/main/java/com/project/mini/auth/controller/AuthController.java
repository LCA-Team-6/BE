package com.project.mini.auth.controller;

import com.project.mini.auth.dto.EmailDto;
import com.project.mini.auth.dto.LoginDto;
import com.project.mini.auth.dto.SignUpDto;
import com.project.mini.auth.dto.TokenDto;
import com.project.mini.auth.util.JwtUtil;
import com.project.mini.common.response.Response;
import com.project.mini.user.entity.User;
import com.project.mini.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String email = user.getEmail();
            String name = user.getName();

            String token = jwtUtil.generateToken(email, name);
            return ResponseEntity.ok(Response.success("Token 발급 완료.", new TokenDto(token)));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Response.fail(HttpStatus.UNAUTHORIZED, "Token 발급 실패."));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDto signUpDto) {
        if (userService.isEmailDuplicate(signUpDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Response.fail(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."));
        }

        userService.signup(signUpDto);
        return ResponseEntity.ok(Response.success("회원가입 성공", null));
    }

    @PostMapping("/email")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailDto emailDto) {
        if (userService.isEmailDuplicate(emailDto.getEmail() )) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Response.fail(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."));
        } else {
            return ResponseEntity.ok(Response.success("사용 가능한 이메일입니다.", null));
        }
    }
}
