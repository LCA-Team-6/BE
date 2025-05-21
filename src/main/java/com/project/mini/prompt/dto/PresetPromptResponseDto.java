package com.project.mini.prompt.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetPromptResponseDto {
    private Long presetPromptId;
    private Long userId;
    private Long toneId;
    private Long personalityId;
    private Long styleId;
    private Long contentId;
    private String name;
    private LocalDateTime createdAt;
}