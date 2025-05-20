package com.project.mini.prompt;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preset_prompt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetPrompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "created_by")
    private String createdBy;
}
