package com.project.mini.memos.repository;

import com.project.mini.memos.entity.Memo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
