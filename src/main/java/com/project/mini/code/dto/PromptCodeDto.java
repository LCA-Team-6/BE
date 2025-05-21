package com.project.mini.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromptCodeDto {
    private List<ToneDto> tone;
    private List<PersonalityDto> personality;
    private List<StyleDto> style;
    private List<ContentDto> content;
}
