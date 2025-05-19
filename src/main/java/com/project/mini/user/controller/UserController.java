// JavaScript source code
package com.project.mini.user.controller;

import com.project.mini.user.dto.UserResponseDto;
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
    public ResponseEntity<UserResponseDto> getUserInfo() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    // UPDATE: 유저 정보 수정 (이름, 이메일 등)
    @PatchMapping
    public ResponseEntity<String> updateUserInfo(@RequestBody UserUpdateDto updateDto) {
        userService.updateUserInfo(updateDto);
        return ResponseEntity.ok("수정된 값");
    }

    // UPDATE: 비밀번호 변경
    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto dto) {
        userService.changePassword(dto);
        return ResponseEntity.ok("ok");
    }

    // DELETE: 유저 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok("삭제된 값");
    }
}