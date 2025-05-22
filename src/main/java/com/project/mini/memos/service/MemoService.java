package com.project.mini.memos.service;

import com.project.mini.memos.dto.MemoRequestDto;
import com.project.mini.memos.dto.MemoResponseDto;
import com.project.mini.memos.entity.Memo;
import com.project.mini.memos.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    @Autowired
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto saveMemo(MemoRequestDto requestDto) {
        Memo memo = Memo.builder()
                .userId(requestDto.getUserId())
                .title(requestDto.getTitle())
                .memo(requestDto.getMemo())
                .createdAt(LocalDateTime.now())
                .build();

        Memo saved = memoRepository.save(memo);
        return new MemoResponseDto(saved);
    }

    public MemoResponseDto updateMemo(Long memoId, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new RuntimeException("해당 메모가 없습니다: " + memoId));

        memo.setMemo(requestDto.getMemo());
        memo.setTitle(requestDto.getTitle()); 
        Memo updated = memoRepository.save(memo);
        return new MemoResponseDto(updated);
    }
}
