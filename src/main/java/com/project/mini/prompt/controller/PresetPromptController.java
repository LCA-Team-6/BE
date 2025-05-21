package com.project.mini.prompt.controller; // 폴더 구조 변경에 맞춰 controller 하위로 이동

import com.project.mini.prompt.dto.*;
import com.project.mini.prompt.service.PresetPromptService; // service 패키지 변경
import com.project.mini.common.response.Response; // Response 클래스 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails; // Spring Security UserDetails 임포트
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PresetPromptController {

    private final PresetPromptService service;

    // TODO: 실제 Spring Security UserDetails 구현체에서 userId를 가져오는 로직으로 교체 필요
    // UserDetails는 일반적으로 사용자의 이메일이나 ID를 getUsername()으로 반환합니다.
    // 여기서는 getUsername()이 사용자 ID(Long)를 문자열로 반환한다고 가정합니다.
    // 또는 CustomUserDetails를 구현하여 Long 타입의 userId를 직접 가지게 하는 것이 가장 좋습니다.
    private Long getUserIdFromPrincipal(UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("인증된 사용자 정보를 찾을 수 없습니다.");
        }
        // 예시: UserDetails의 username이 사용자 ID를 나타내는 문자열이라고 가정.
        // 실제로는 User 엔티티와 매핑되는 userId를 가져오는 로직으로 변경해야 합니다.
        // 예를 들어, 사용자 인증 과정에서 CustomUserDetails를 생성하고 그 안에 userId를 저장하는 방식.
        try {
            return Long.parseLong(userDetails.getUsername()); // UserDetails의 getUsername()이 userId 문자열이라고 가정
        } catch (NumberFormatException e) {
            // userDetails.getUsername()이 숫자가 아닌 이메일 등이라면, 이메일로 User 엔티티를 조회하여 ID를 가져와야 합니다.
            // 예: User findUser = userService.findUserByEmail(userDetails.getUsername()); return findUser.getId();
            throw new IllegalArgumentException("사용자 ID를 추출할 수 없습니다. (UserDetails username: " + userDetails.getUsername() + ")");
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<PresetPromptResponseDto>>> getAllPrompts(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long userId = getUserIdFromPrincipal(userDetails);
            List<PresetPromptResponseDto> prompts = service.getAllPrompts(userId);
            return ResponseEntity.ok(Response.success("프리셋 목록 조회 성공", prompts));
        } catch (IllegalArgumentException e) {
            // 인증 정보가 없거나 userId 추출 실패 시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error(HttpStatus.UNAUTHORIZED, e.getMessage()));
        } catch (Exception e) {
            // 서비스 계층에서 던져진 RuntimeException 등 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 목록 조회 실패: " + e.getMessage()));
        }
    }

    @GetMapping("/{presetPromptId}") // @PathVariable Long id 대신 @PathVariable Long presetPromptId
    public ResponseEntity<Response<PresetPromptResponseDto>> getPrompt(@PathVariable Long presetPromptId) {
        try {
            PresetPromptResponseDto prompt = service.getPrompt(presetPromptId);
            return ResponseEntity.ok(Response.success("프리셋 단일 조회 성공", prompt));
        } catch (EntityNotFoundException e) {
            // 프리셋이 존재하지 않는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 단일 조회 실패: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Response<Void>> createPrompt(@RequestBody PresetPromptRequestDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long userId = getUserIdFromPrincipal(userDetails); // 로그인한 유저의 userId 가져오기
            service.createPrompt(dto, userId); // userId 전달
            return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("프리셋 저장 성공")); // 201 Created 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error(HttpStatus.UNAUTHORIZED, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 저장 실패: " + e.getMessage()));
        }
    }

    @PutMapping("/{presetPromptId}") // @PathVariable Long id 대신 @PathVariable Long presetPromptId
    public ResponseEntity<Response<PresetPromptResponseDto>> updatePrompt(@PathVariable Long presetPromptId, @RequestBody PresetPromptRequestDto dto) {
        try {
            PresetPromptResponseDto updatedPrompt = service.updatePrompt(presetPromptId, dto);
            return ResponseEntity.ok(Response.success("프리셋 수정 성공", updatedPrompt));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 수정 실패: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{presetPromptId}") // @PathVariable Long id 대신 @PathVariable Long presetPromptId
    public ResponseEntity<Response<Void>> deletePrompt(@PathVariable Long presetPromptId) {
        try {
            service.deletePrompt(presetPromptId);
            return ResponseEntity.ok(Response.success("프리셋 삭제 성공"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "프리셋 삭제 실패: " + e.getMessage()));
        }
    }
}