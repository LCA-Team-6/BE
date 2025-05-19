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

    // READ: ���� ���� ��ȸ (���������� ��)
    @GetMapping
    public ResponseEntity<Response<UserResponseDto>> getUserInfo() {
        return ResponseEntity.ok(Response.success("���� ���� ��ȸ ����", userService.getCurrentUser()));
    }

    // UPDATE: ���� ���� ���� (�̸�, �̸��� ��)
    @PatchMapping
    public ResponseEntity<Response<Void>> updateUserInfo(@RequestBody UserUpdateDto updateDto) {
        userService.updateUserInfo(updateDto);
        return ResponseEntity.ok(Response.success("������ ��", null));
    }

    // UPDATE: ��й�ȣ ����
    @PostMapping("/password")
    public ResponseEntity<Response<Void>> changePassword(@RequestBody ChangePasswordDto dto) {
        userService.changePassword(dto);
        return ResponseEntity.ok(Response.success("��й�ȣ ���� �Ϸ�", null));
    }

    // DELETE: ���� ����
    @DeleteMapping
    public ResponseEntity<Response<Void>> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok(Response.success("������ ��", null));
    }
}
