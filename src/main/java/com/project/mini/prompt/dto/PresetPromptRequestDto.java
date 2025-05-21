package com.project.mini.preset.prompt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetPromptRequestDto {
    private Long toneId;
    private Long personalityId;
    private Long styleId;
    private Long contentId;
    private String name; 
}