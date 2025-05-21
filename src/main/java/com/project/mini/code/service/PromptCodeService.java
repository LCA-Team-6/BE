package com.project.mini.code.service;

import com.project.mini.code.dto.*;
import com.project.mini.code.repository.ContentRepository;
import com.project.mini.code.repository.PersonalityRepository;
import com.project.mini.code.repository.StyleRepository;
import com.project.mini.code.repository.ToneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptCodeService {
    private final ToneRepository toneRepository;
    private final PersonalityRepository personalityRepository;
    private final StyleRepository styleRepository;
    private final ContentRepository contentRepository;

    public PromptCodeDto getAllPromptCodes() {
        return new PromptCodeDto(
                toneRepository.findAll().stream()
                        .map(tone -> new ToneDto(tone.getToneId(), tone.getName()))
                        .toList(),
                personalityRepository.findAll().stream()
                        .map(personality -> new PersonalityDto(personality.getPersonalityId(), personality.getName()))
                        .toList(),
                styleRepository.findAll().stream()
                        .map(style -> new StyleDto(style.getStyleId(), style.getName()))
                        .toList(),
                contentRepository.findAll().stream()
                        .map(content -> new ContentDto(content.getContentId(), content.getName()))
                        .toList()
        );
    }
}
