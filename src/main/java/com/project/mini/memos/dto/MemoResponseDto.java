package com.project.mini.memos.dto;

import com.project.mini.memos.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private Long memoId;
    private Long userId;
    private String memo;
    private LocalDateTime createdAt;

    public MemoResponseDto(Memo memo) {
        this.memoId = memo.getMemoId();
        this.userId = memo.getUserId();
        this.memo = memo.getMemo();
        this.createdAt = memo.getCreatedAt();
    }
}
