package com.project.mini.analysis.dto;

import java.util.List;
import lombok.Data;

@Data
public class OpenAiResponse {
    private List<Choice> choices;
    @Data
    public static class Choice {
        private Message message;
    }
    @Data
    public static class Message {
        private String role;
        private String content;
    }
}