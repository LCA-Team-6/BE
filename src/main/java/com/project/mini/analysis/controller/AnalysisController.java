package com.project.mini.analysis.controller;

import com.project.mini.analysis.dto.AnalysisRequestDto;
import com.project.mini.analysis.dto.AnalysisSaveDto;
import com.project.mini.analysis.service.AnalysisService;
import com.project.mini.common.response.Response;
import com.project.mini.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<?> analysis(@RequestBody AnalysisRequestDto analysisRequestDto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Response.success(null, analysisService.createMemo(analysisRequestDto, user)));
    }

    @PostMapping("/{memoId}")
    public ResponseEntity<?> analysisWithEdit(@RequestBody AnalysisRequestDto analysisRequestDto, @PathVariable Long memoId) {
        try {
            return ResponseEntity.ok(Response.success(null, analysisService.modifyMemo(analysisRequestDto, memoId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.fail(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AnalysisSaveDto analysisSaveDto, @AuthenticationPrincipal User user) {
        analysisService.save(analysisSaveDto);
        return ResponseEntity.ok(Response.success("피드백 저장을 완료했습니다.", null));
    }
}
