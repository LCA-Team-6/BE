package com.project.mini.memos.repository;

import com.project.mini.memos.dto.MemoByDateResponseDto;
import com.project.mini.memos.dto.MemoResponseDto;
import com.project.mini.memos.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    @Query("""
            SELECT new com.project.mini.memos.dto.MemoByDateResponseDto(
                    m.memoId,
                    m.userId,
                    m.title,
                    m.memo,
                    a.analysis,
                    m.createdAt
                )
                FROM Memo m
                LEFT JOIN Analysis a ON m.memoId = a.memoId
                WHERE DATE(m.createdAt) = :date
                AND   m.userId = :userId
            """)
    List<MemoByDateResponseDto> findMemoByDate(@Param("date") LocalDate date, @Param("userId") Long userId);

    @Query("""
            SELECT new com.project.mini.memos.dto.MemoResponseDto(
                    m.memoId,
                    m.userId,
                    m.title,
                    m.memo,
                    m.createdAt
                )
                FROM Memo m
                WHERE FUNCTION('MONTH', m.createdAt) = :month
                AND   m.userId = :userId
            """)
    List<MemoResponseDto> findMemoByMonth(@Param("month") int month, @Param("userId") Long userId);
}
