package com.project.mini.memos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MemoByDateResponseDto {
    private Long memoId;
    private Long userId;
    private String title;
    private String memo;
    private String analysis;
    private LocalDateTime createdAt;
}
