package com.project.mini.memos.controller;

import com.project.mini.auth.security.UserDetailsImpl;
import com.project.mini.memos.dto.MemoRequestDto;
import com.project.mini.memos.dto.MemoResponseDto;
import com.project.mini.memos.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

    private final MemoService memoService;

    @Autowired
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
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

    // 월별 기록 조회
    @GetMapping("/{month}")
    public ResponseEntity<?> getMemosByMonth(
            @PathVariable String month,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(401).body(Map.of("message", "인증 정보 없음"));
            }

            Long userId = userDetails.getUser().getUserId();
            List<MemoResponseDto> memos = memoService.getMemosByMonth(month, userId);
            return ResponseEntity.ok(memos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "월별 기록 조회 실패", "error", e.getMessage()));
        }
    }

}
