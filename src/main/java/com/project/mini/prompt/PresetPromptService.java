package com.project.mini.prompt;

import com.project.mini.prompt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("프롬프트가 존재하지 않습니다."));
        return PresetPromptResponseDto.builder()
                .id(prompt.getId())
                .title(prompt.getTitle())
                .content(prompt.getContent())
                .build();
    }

    public void createPrompt(PresetPromptRequestDto dto) {
        PresetPrompt prompt = PresetPrompt.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        repository.save(prompt);
    }

    public void updatePrompt(Long id, PresetPromptRequestDto dto) {
        PresetPrompt prompt = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("프롬프트가 존재하지 않습니다."));
        prompt.setTitle(dto.getTitle());
        prompt.setContent(dto.getContent());
        repository.save(prompt);
    }

    public void deletePrompt(Long id) {
        repository.deleteById(id);
    }
}
