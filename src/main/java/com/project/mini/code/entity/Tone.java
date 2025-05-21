package com.project.mini.code.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tone")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toneId;

    private String code;
    private String name;
}
