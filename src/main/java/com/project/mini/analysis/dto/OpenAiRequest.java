package com.project.mini.analysis.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OpenAiRequest {
    private String model;
    private List<Map<String, String>> messages;
    private double temperature;
    private int max_tokens;
}
