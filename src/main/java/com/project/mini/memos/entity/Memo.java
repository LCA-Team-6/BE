package com.project.mini.memos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "memo")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long memoId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 5000)
    private String memo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
