package com.project.mini.prompt.service; // 폴더 구조 변경에 맞춰 service 하위로 이동

import com.project.mini.prompt.dto.*;
import com.project.mini.prompt.entity.PresetPrompt; // entity 패키지 변경
import com.project.mini.prompt.repository.PresetPromptRepository; // repository 패키지 변경
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresetPromptService {

    private final PresetPromptRepository repository;

    @Transactional(readOnly = true)
    public List<PresetPromptResponseDto> getAllPrompts(Long userId) { // userId 인자 추가
        return repository.findByUserId(userId).stream() // userId로 필터링
                .map(prompt -> PresetPromptResponseDto.builder()
                        .presetPromptId(prompt.getPresetPromptId())
                        .userId(prompt.getUserId())
                        .toneId(prompt.getToneId())
                        .personalityId(prompt.getPersonalityId())
                        .styleId(prompt.getStyleId())
                        .contentId(prompt.getContentId())
                        .name(prompt.getName())
                        .createdAt(prompt.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PresetPromptResponseDto getPrompt(Long presetPromptId) { // id -> presetPromptId 변경
        PresetPrompt prompt;
        try {
            prompt = repository.findByPresetPromptId(presetPromptId) // findById -> findByPresetPromptId
                    .orElseThrow(() -> new EntityNotFoundException("프리셋이 존재하지 않습니다.")); // 메시지 변경
        } catch (EntityNotFoundException e) {
            // Service 계층에서 특정 예외를 잡고 RuntimeException으로 다시 던집니다.
            // 실제 애플리케이션에서는 Custom Exception을 정의하여 사용하는 것이 좋습니다.
            throw new RuntimeException("프리셋 조회 실패: " + e.getMessage(), e);
        }

        return PresetPromptResponseDto.builder()
                .presetPromptId(prompt.getPresetPromptId())
                .userId(prompt.getUserId())
                .toneId(prompt.getToneId())
                .personalityId(prompt.getPersonalityId())
                .styleId(prompt.getStyleId())
                .contentId(prompt.getContentId())
                .name(prompt.getName())
                .createdAt(prompt.getCreatedAt())
                .build();
    }

    @Transactional
    public void createPrompt(PresetPromptRequestDto dto, Long userId) { // userId 인자 추가
        try {
            PresetPrompt prompt = PresetPrompt.builder()
                    .userId(userId) // @AuthenticationPrincipal에서 받은 userId 설정
                    .toneId(dto.getToneId())
                    .personalityId(dto.getPersonalityId())
                    .styleId(dto.getStyleId())
                    .contentId(dto.getContentId())
                    .name(dto.getName())
                    .createdAt(LocalDateTime.now()) // createdAt 설정
                    .build();
            repository.save(prompt);
        } catch (Exception e) {
            throw new RuntimeException("프리셋 생성 실패: " + e.getMessage(), e);
        }
    }

    @Transactional
    public PresetPromptResponseDto updatePrompt(Long presetPromptId, PresetPromptRequestDto dto) { // id -> presetPromptId 변경
        PresetPrompt prompt;
        try {
            prompt = repository.findByPresetPromptId(presetPromptId) // findById -> findByPresetPromptId
                    .orElseThrow(() -> new EntityNotFoundException("프리셋이 존재하지 않습니다.")); // 메시지 변경
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("프리셋 업데이트 실패: " + e.getMessage(), e);
        }

        prompt.setToneId(dto.getToneId());
        prompt.setPersonalityId(dto.getPersonalityId());
        prompt.setStyleId(dto.getStyleId());
        prompt.setContentId(dto.getContentId());
        prompt.setName(dto.getName());

        PresetPrompt updatedPrompt = repository.save(prompt);
        return PresetPromptResponseDto.builder()
                .presetPromptId(updatedPrompt.getPresetPromptId())
                .userId(updatedPrompt.getUserId())
                .toneId(updatedPrompt.getToneId())
                .personalityId(updatedPrompt.getPersonalityId())
                .styleId(updatedPrompt.getStyleId())
                .contentId(updatedPrompt.getContentId())
                .name(updatedPrompt.getName())
                .createdAt(updatedPrompt.getCreatedAt())
                .build();
    }

    @Transactional
    public void deletePrompt(Long presetPromptId) { // id -> presetPromptId 변경
        try {
            repository.findByPresetPromptId(presetPromptId) // 삭제 전에 존재 여부를 확인
                    .orElseThrow(() -> new EntityNotFoundException("프리셋이 존재하지 않습니다.")); // 메시지 변경
            repository.deleteByPresetPromptId(presetPromptId); // 명시적 삭제 메서드
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("프리셋 삭제 실패: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("프리셋 삭제 중 오류 발생: " + e.getMessage(), e);
        }
    }
}