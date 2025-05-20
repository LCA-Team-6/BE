package com.project.mini.prompt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetPromptResponseDto {
    private Long id;
    private String title;
    private String content;
}
