package com.project.mini.analysis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analysis_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analysisTagId;

    private Long analysisId;
    private Long tagId;
    private int score;
}
