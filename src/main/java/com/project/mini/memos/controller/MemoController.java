package com.project.mini.memos.controller;

import com.project.mini.common.response.Response;
import com.project.mini.memos.dto.MemoRequestDto;
import com.project.mini.memos.dto.MemoResponseDto;
import com.project.mini.memos.service.MemoService;
import com.project.mini.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.project.mini.user.service.UserService;
import com.project.mini.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

    private final MemoService memoService;
    private final JwtUtil jwtUtil;
    private final UserService userService; //

    @Autowired
    public MemoController(MemoService memoService, JwtUtil jwtUtil, UserService userService) {
        this.memoService = memoService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    // 기록 저장
    @PostMapping
    public ResponseEntity<?> saveMemo(@RequestBody MemoRequestDto requestDto) {
        try {
            MemoResponseDto saved = memoService.saveMemo(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("message", "기록 저장 성공!", "memoId", saved.getMemoId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", "기록 저장 실패", "error", e.getMessage()));
        }
    }

    // 기록 수정
    @PutMapping("/{memoId}")
    public ResponseEntity<?> updateMemo(@PathVariable Long memoId, @RequestBody MemoRequestDto requestDto) {
        try {
            MemoResponseDto updated = memoService.updateMemo(memoId, requestDto);
            return ResponseEntity.ok(
                    Map.of("message", "기록 수정 성공!", "memoId", updated.getMemoId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "기록 수정 실패", "error", e.getMessage()));
        }
    }

    @GetMapping("/{date}")
    public ResponseEntity<?> getMemoByDate (@PathVariable String date, @AuthenticationPrincipal User user) {
        if (date.length() == 2) {
            return ResponseEntity.ok(Response.success(null, memoService.getMemoByMonth(date, user.getUserId())));
        } else {
            return ResponseEntity.ok(Response.success(null, memoService.getMemoByDate(date, user.getUserId())));
        }
    }

    // 기록 삭제
    @DeleteMapping("/{memoId}")
    public ResponseEntity<?> deleteMemo(@PathVariable Long memoId, HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "인증 토큰이 없습니다."));
            }


            String token = authHeader.substring(7);
            String email = jwtUtil.getEmailFromToken(token);

            Long userId = userService.getUserIdByEmail(email); 

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "토큰에서 사용자 정보를 읽을 수 없습니다."));
            }

            memoService.deleteMemo(memoId, userId);

            return ResponseEntity.ok(Map.of("message", "기록 삭제 성공!"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "삭제 권한이 없습니다.", "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "기록 삭제 실패", "error", e.getMessage()));
        }
    }

}
