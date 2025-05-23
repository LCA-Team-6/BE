package com.project.mini.analysis.entity;

import java.time.LocalDateTime;

import com.project.mini.memos.entity.Memo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "analysis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analysisId;

    // memoId를 별도 필드로 두되, DB에서 값만 읽어오고 수정은 안 함
    @Column(name = "memo_id")
    private Long memoId;

    private Long presetPromptId;

    private String analysis;

    private LocalDateTime createdAt;

    // 진짜 연관관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id", insertable = false, updatable = false)
    private Memo memo;

}
