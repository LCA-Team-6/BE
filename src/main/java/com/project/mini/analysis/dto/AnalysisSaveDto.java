package com.project.mini.analysis.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisSaveDto {
    private Long memoId;
    private Long presetPromptId;
    private String analysis;
    private List<EmotionDto> emotions;
}
