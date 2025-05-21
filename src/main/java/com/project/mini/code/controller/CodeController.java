package com.project.mini.code.controller;

import com.project.mini.code.service.PromptCodeService;
import com.project.mini.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {
    private final PromptCodeService promptCodeService;

    @GetMapping("/prompts")
    public ResponseEntity<?> getPromptCodes () {
        return ResponseEntity.ok(Response.success(null, promptCodeService.getAllPromptCodes()));
    }
}
