package com.project.mini.prompt;

import com.project.mini.prompt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 추가

import javax.persistence.EntityNotFoundException; // 추가
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresetPromptService {

    private final PresetPromptRepository repository;

    public List<PresetPromptResponseDto> getAllPrompts() {
        return repository.findAll().stream()
                .map(prompt -> PresetPromptResponseDto.builder()
                        .id(prompt.getId())
                        .title(prompt.getTitle())
                        .content(prompt.getContent())
                        .build())
                .collect(Collectors.toList());
    }

    public PresetPromptResponseDto getPrompt(Long id) {
        PresetPrompt prompt = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("프롬프트가 존재하지 않습니다.")); // 수정
        return PresetPromptResponseDto.builder()
                .id(prompt.getId())
                .title(prompt.getTitle())
                .content(prompt.getContent())
                .build();
    }

    @Transactional // 추가
    public void createPrompt(PresetPromptRequestDto dto) {
        PresetPrompt prompt = PresetPrompt.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        repository.save(prompt);
    }

    @Transactional // 추가
    public PresetPromptResponseDto updatePrompt(Long id, PresetPromptRequestDto dto) { // 수정
        PresetPrompt prompt = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("프롬프트가 존재하지 않습니다.")); // 수정
        prompt.setTitle(dto.getTitle());
        prompt.setContent(dto.getContent());
        PresetPrompt updatedPrompt = repository.save(prompt);
        return PresetPromptResponseDto.builder()
                .id(updatedPrompt.getId())
                .title(updatedPrompt.getTitle())
                .content(updatedPrompt.getContent())
                .build();
    }

    @Transactional // 추가
    public void deletePrompt(Long id) {
        repository.deleteById(id);
    }
}