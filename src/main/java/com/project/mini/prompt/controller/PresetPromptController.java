package com.project.mini.prompt.controller; // 폴더 구조 변경에 맞춰 controller 하위로 이동

import com.project.mini.prompt.dto.*;
import com.project.mini.prompt.entity.PresetPrompt;
import com.project.mini.prompt.service.PresetPromptService; // service 패키지 변경
import com.project.mini.common.response.Response; // Response 클래스 임포트
import com.project.mini.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PresetPromptController {

    private final PresetPromptService service;

    @GetMapping
    public ResponseEntity<?> getAllPrompts(@AuthenticationPrincipal User user) {
        try {
            List<PresetPromptResponseDto> prompts = service.getAllPrompts(user.getUserId());
            return ResponseEntity.ok(Response.success(null, prompts));
        } catch (IllegalArgumentException e) {
            // 인증 정보가 없거나 userId 추출 실패 시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.fail(HttpStatus.UNAUTHORIZED, e.getMessage()));
        } catch (Exception e) {
            // 서비스 계층에서 던져진 RuntimeException 등 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 목록 조회 실패: " + e.getMessage()));
        }
    }

    @GetMapping("/{presetPromptId}")
    public ResponseEntity<Response<PresetPromptResponseDto>> getPrompt(@PathVariable Long presetPromptId) {
        try {
            PresetPromptResponseDto prompt = service.getPrompt(presetPromptId);
            return ResponseEntity.ok(Response.success(null, prompt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 단일 조회 실패: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Response<PresetPrompt>> createPrompt(@RequestBody PresetPromptRequestDto dto, @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(Response.success("프리셋 저장 성공", service.createPrompt(dto, user.getUserId())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.fail(HttpStatus.UNAUTHORIZED, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 저장 실패: " + e.getMessage()));
        }
    }

    @PutMapping("/{presetPromptId}")
    public ResponseEntity<Response<PresetPromptResponseDto>> updatePrompt(@PathVariable Long presetPromptId, @RequestBody PresetPromptRequestDto dto) {
        try {
            PresetPromptResponseDto updatedPrompt = service.updatePrompt(presetPromptId, dto);
            return ResponseEntity.ok(Response.success(null, updatedPrompt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 수정 실패: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{presetPromptId}")
    public ResponseEntity<Response<Void>> deletePrompt(@PathVariable Long presetPromptId) {
        try {
            service.deletePrompt(presetPromptId);
            return ResponseEntity.ok(Response.success("프리셋 삭제 성공", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 삭제 실패: " + e.getMessage()));
        }
    }
}