package com.project.mini.prompt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetPromptRequestDto {
    private String title;
    private String content;
}
