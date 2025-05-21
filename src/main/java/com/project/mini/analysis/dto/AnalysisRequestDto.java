package com.project.mini.analysis.dto;

import lombok.Data;

@Data
public class AnalysisRequestDto {
    private String title;
    private String memo;
    private Long tone;
    private Long personality;
    private Long style;
    private Long content;
}
