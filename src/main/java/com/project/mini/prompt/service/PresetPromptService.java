package com.project.mini.prompt.service; // ���� ���� ���濡 ���� service ������ �̵�

import com.project.mini.prompt.dto.*;
import com.project.mini.prompt.entity.PresetPrompt; // entity ��Ű�� ����
import com.project.mini.prompt.repository.PresetPromptRepository; // repository ��Ű�� ����
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
    public List<PresetPromptResponseDto> getAllPrompts(Long userId) { // userId ���� �߰�
        return repository.findByUserId(userId).stream() // userId�� ���͸�
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
    public PresetPromptResponseDto getPrompt(Long presetPromptId) { // id -> presetPromptId ����
        PresetPrompt prompt;
        try {
            prompt = repository.findByPresetPromptId(presetPromptId) // findById -> findByPresetPromptId
                    .orElseThrow(() -> new EntityNotFoundException("�������� �������� �ʽ��ϴ�.")); // �޽��� ����
        } catch (EntityNotFoundException e) {
            // Service �������� Ư�� ���ܸ� ��� RuntimeException���� �ٽ� �����ϴ�.
            // ���� ���ø����̼ǿ����� Custom Exception�� �����Ͽ� ����ϴ� ���� �����ϴ�.
            throw new RuntimeException("������ ��ȸ ����: " + e.getMessage(), e);
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
    public void createPrompt(PresetPromptRequestDto dto, Long userId) { // userId ���� �߰�
        try {
            PresetPrompt prompt = PresetPrompt.builder()
                    .userId(userId) // @AuthenticationPrincipal���� ���� userId ����
                    .toneId(dto.getToneId())
                    .personalityId(dto.getPersonalityId())
                    .styleId(dto.getStyleId())
                    .contentId(dto.getContentId())
                    .name(dto.getName())
                    .createdAt(LocalDateTime.now()) // createdAt ����
                    .build();
            repository.save(prompt);
        } catch (Exception e) {
            throw new RuntimeException("������ ���� ����: " + e.getMessage(), e);
        }
    }

    @Transactional
    public PresetPromptResponseDto updatePrompt(Long presetPromptId, PresetPromptRequestDto dto) { // id -> presetPromptId ����
        PresetPrompt prompt;
        try {
            prompt = repository.findByPresetPromptId(presetPromptId) // findById -> findByPresetPromptId
                    .orElseThrow(() -> new EntityNotFoundException("�������� �������� �ʽ��ϴ�.")); // �޽��� ����
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("������ ������Ʈ ����: " + e.getMessage(), e);
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
    public void deletePrompt(Long presetPromptId) { // id -> presetPromptId ����
        try {
            repository.findByPresetPromptId(presetPromptId) // ���� ���� ���� ���θ� Ȯ��
                    .orElseThrow(() -> new EntityNotFoundException("�������� �������� �ʽ��ϴ�.")); // �޽��� ����
            repository.deleteByPresetPromptId(presetPromptId); // ����� ���� �޼���
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("������ ���� ����: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("������ ���� �� ���� �߻�: " + e.getMessage(), e);
        }
    }
}