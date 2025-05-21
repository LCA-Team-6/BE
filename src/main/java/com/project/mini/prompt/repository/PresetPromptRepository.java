package com.project.mini.prompt.repository; 

import com.project.mini.prompt.entity.PresetPrompt; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PresetPromptRepository extends JpaRepository<PresetPrompt, Long> {
    List<PresetPrompt> findByUserId(Long userId);

    Optional<PresetPrompt> findByPresetPromptId(Long presetPromptId);

    void deleteByPresetPromptId(Long presetPromptId);
}