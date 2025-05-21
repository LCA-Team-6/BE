package com.project.mini.code.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personality")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalityId;

    private String code;
    private String name;
}
