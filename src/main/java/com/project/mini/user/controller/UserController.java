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

    // READ: ���� ���� ��ȸ (���������� ��)
    @GetMapping
    public ResponseEntity<UserResponseDto> getUserInfo() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    // UPDATE: ���� ���� ���� (�̸�, �̸��� ��)
    @PatchMapping
    public ResponseEntity<String> updateUserInfo(@RequestBody UserUpdateDto updateDto) {
        userService.updateUserInfo(updateDto);
        return ResponseEntity.ok("������ ��");
    }

    // UPDATE: ��й�ȣ ����
    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto dto) {
        userService.changePassword(dto);
        return ResponseEntity.ok("ok");
    }

    // DELETE: ���� ����
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok("������ ��");
    }
}