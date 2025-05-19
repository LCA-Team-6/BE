// JavaScript source code
package com.project.mini.user.controller;

import com.project.mini.common.Response;
import com.project.mini.user.dto.*;
import com.project.mini.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // READ: 유저 정보 조회 (마이페이지 등)
    @GetMapping
    public ResponseEntity<Response<UserResponseDto>> getUserInfo() {
        return ResponseEntity.ok(Response.success("유저 정보 조회 성공", userService.getCurrentUser()));
    }

    // UPDATE: 유저 정보 수정 (이름, 이메일 등)
    @PatchMapping
    public ResponseEntity<Response<Void>> updateUserInfo(@RequestBody UserUpdateDto updateDto) {
        userService.updateUserInfo(updateDto);
        return ResponseEntity.ok(Response.success("수정된 값", null));
    }

    // UPDATE: 비밀번호 변경
    @PostMapping("/password")
    public ResponseEntity<Response<Void>> changePassword(@RequestBody ChangePasswordDto dto) {
        userService.changePassword(dto);
        return ResponseEntity.ok(Response.success("비밀번호 변경 완료", null));
    }

    // DELETE: 유저 삭제
    @DeleteMapping
    public ResponseEntity<Response<Void>> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok(Response.success("삭제된 값", null));
    }
}
