package com.project.mini.user.controller;
import com.project.mini.common.response.Response;
import com.project.mini.user.dto.*;
import com.project.mini.user.entity.User;
import com.project.mini.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // READ: 유저 정보 조회 (마이페이지 등)
    @GetMapping
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Response.success(null, new UserResponseDto(user)));
    }

    // UPDATE: 유저 정보 수정 (이름, 이메일 등)
    @PatchMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(Response.success("유저 정보 수정 완료.", userService.updateUserInfo(updateDto)));
    }

    // UPDATE: 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<Response<Void>> changePassword(@RequestBody ChangePasswordDto dto) {
        try {
            userService.changePassword(dto);
            return ResponseEntity.ok(Response.success("비밀번호 변경 완료.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Response.fail(HttpStatus.CONFLICT, e.getMessage()));
        }
    }

    // DELETE: 유저 삭제
    @DeleteMapping
    public ResponseEntity<Response<Void>> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok(Response.success("유저 삭제 완료.", null));
    }
}
