package com.project.mini.analysis.controller;

import com.project.mini.analysis.dto.AnalysisRequestDto;
import com.project.mini.analysis.dto.AnalysisSaveDto;
import com.project.mini.analysis.service.AnalysisService;
import com.project.mini.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<?> analysis(@RequestBody AnalysisRequestDto analysisRequestDto) {
        return ResponseEntity.ok(Response.success(null, analysisService.getAnalysis(analysisRequestDto)));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AnalysisSaveDto analysisSaveDto) {

        return ResponseEntity.ok(Response.success("피드백 저장을 완료했습니다.", null));
    }
}
