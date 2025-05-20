package com.project.mini.prompt;

import com.project.mini.prompt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PresetPromptController {

    private final PresetPromptService service;

    @GetMapping
    public ResponseEntity<List<PresetPromptResponseDto>> getAllPrompts() {
        return ResponseEntity.ok(service.getAllPrompts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresetPromptResponseDto> getPrompt(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPrompt(id));
    }

    @PostMapping
    public ResponseEntity<Void> createPrompt(@RequestBody PresetPromptRequestDto dto) {
        service.createPrompt(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePrompt(@PathVariable Long id, @RequestBody PresetPromptRequestDto dto) {
        service.updatePrompt(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrompt(@PathVariable Long id) {
        service.deletePrompt(id);
        return ResponseEntity.ok().build();
    }
}
