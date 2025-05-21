package com.project.mini.prompt.entity; 
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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
    @Column(name = "preset_prompt_id")
    private Long presetPromptId;

    @Column(name = "user_id", nullable = false) 
    private Long userId;

    @Column(name = "tone_id")
    private Long toneId;

    @Column(name = "personality_id")
    private Long personalityId;

    @Column(name = "style_id")
    private Long styleId;

    @Column(name = "content_id")
    private Long contentId;

    @Column(nullable = false) 
    private String name;

    @Column(name = "created_at", nullable = false) 
    private LocalDateTime createdAt; 
}