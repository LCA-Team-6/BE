package com.project.mini.memos.service;

import com.project.mini.memos.dto.MemoRequestDto;
import com.project.mini.memos.dto.MemoResponseDto;
import com.project.mini.memos.entity.Memo;
import com.project.mini.memos.repository.MemoRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    @Autowired
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }
    // 기록 저장장
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
    // 기록 수정
    public MemoResponseDto updateMemo(Long memoId, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new RuntimeException("해당 메모가 없습니다: " + memoId));

        memo.setMemo(requestDto.getMemo());
        Memo updated = memoRepository.save(memo);
        return new MemoResponseDto(updated);
    }

    // 월별 기록 조회
    public List<MemoResponseDto> getMemosByMonth(String month, Long userId) {
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());

        LocalDate start = LocalDate.parse(year + "-" + month + "-01");
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Memo> memos = memoRepository.findAllByUserIdAndCreatedAtBetween(userId, start.atStartOfDay(),
                end.atTime(23, 59, 59));
        return memos.stream().map(MemoResponseDto::new).toList();
    }
}
