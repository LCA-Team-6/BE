package com.project.mini.prompt.controller; // ���� ���� ���濡 ���� controller ������ �̵�

import com.project.mini.prompt.dto.*;
import com.project.mini.prompt.service.PresetPromptService; // service ��Ű�� ����
import com.project.mini.common.response.Response; // Response Ŭ���� ����Ʈ
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails; // Spring Security UserDetails ����Ʈ
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PresetPromptController {

    private final PresetPromptService service;

    // TODO: ���� Spring Security UserDetails ����ü���� userId�� �������� �������� ��ü �ʿ�
    // UserDetails�� �Ϲ������� ������� �̸����̳� ID�� getUsername()���� ��ȯ�մϴ�.
    // ���⼭�� getUsername()�� ����� ID(Long)�� ���ڿ��� ��ȯ�Ѵٰ� �����մϴ�.
    // �Ǵ� CustomUserDetails�� �����Ͽ� Long Ÿ���� userId�� ���� ������ �ϴ� ���� ���� �����ϴ�.
    private Long getUserIdFromPrincipal(UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("������ ����� ������ ã�� �� �����ϴ�.");
        }
        // ����: UserDetails�� username�� ����� ID�� ��Ÿ���� ���ڿ��̶�� ����.
        // �����δ� User ��ƼƼ�� ���εǴ� userId�� �������� �������� �����ؾ� �մϴ�.
        // ���� ���, ����� ���� �������� CustomUserDetails�� �����ϰ� �� �ȿ� userId�� �����ϴ� ���.
        try {
            return Long.parseLong(userDetails.getUsername()); // UserDetails�� getUsername()�� userId ���ڿ��̶�� ����
        } catch (NumberFormatException e) {
            // userDetails.getUsername()�� ���ڰ� �ƴ� �̸��� ���̶��, �̸��Ϸ� User ��ƼƼ�� ��ȸ�Ͽ� ID�� �����;� �մϴ�.
            // ��: User findUser = userService.findUserByEmail(userDetails.getUsername()); return findUser.getId();
            throw new IllegalArgumentException("����� ID�� ������ �� �����ϴ�. (UserDetails username: " + userDetails.getUsername() + ")");
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<PresetPromptResponseDto>>> getAllPrompts(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long userId = getUserIdFromPrincipal(userDetails);
            List<PresetPromptResponseDto> prompts = service.getAllPrompts(userId);
            return ResponseEntity.ok(Response.success("������ ��� ��ȸ ����", prompts));
        } catch (IllegalArgumentException e) {
            // ���� ������ ���ų� userId ���� ���� ��
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error(HttpStatus.UNAUTHORIZED, e.getMessage()));
        } catch (Exception e) {
            // ���� �������� ������ RuntimeException �� ó��
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "������ ��� ��ȸ ����: " + e.getMessage()));
        }
    }

    @GetMapping("/{presetPromptId}") // @PathVariable Long id ��� @PathVariable Long presetPromptId
    public ResponseEntity<Response<PresetPromptResponseDto>> getPrompt(@PathVariable Long presetPromptId) {
        try {
            PresetPromptResponseDto prompt = service.getPrompt(presetPromptId);
            return ResponseEntity.ok(Response.success("������ ���� ��ȸ ����", prompt));
        } catch (EntityNotFoundException e) {
            // �������� �������� �ʴ� ���
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "������ ���� ��ȸ ����: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Response<Void>> createPrompt(@RequestBody PresetPromptRequestDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long userId = getUserIdFromPrincipal(userDetails); // �α����� ������ userId ��������
            service.createPrompt(dto, userId); // userId ����
            return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("������ ���� ����")); // 201 Created ����
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error(HttpStatus.UNAUTHORIZED, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "������ ���� ����: " + e.getMessage()));
        }
    }

    @PutMapping("/{presetPromptId}") // @PathVariable Long id ��� @PathVariable Long presetPromptId
    public ResponseEntity<Response<PresetPromptResponseDto>> updatePrompt(@PathVariable Long presetPromptId, @RequestBody PresetPromptRequestDto dto) {
        try {
            PresetPromptResponseDto updatedPrompt = service.updatePrompt(presetPromptId, dto);
            return ResponseEntity.ok(Response.success("������ ���� ����", updatedPrompt));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "������ ���� ����: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{presetPromptId}") // @PathVariable Long id ��� @PathVariable Long presetPromptId
    public ResponseEntity<Response<Void>> deletePrompt(@PathVariable Long presetPromptId) {
        try {
            service.deletePrompt(presetPromptId);
            return ResponseEntity.ok(Response.success("������ ���� ����"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "������ ���� ����: " + e.getMessage()));
        }
    }
}