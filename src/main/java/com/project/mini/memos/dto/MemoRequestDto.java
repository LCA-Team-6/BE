package com.project.mini.memos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoRequestDto {
    private Long userId;
    private String memo;
}
