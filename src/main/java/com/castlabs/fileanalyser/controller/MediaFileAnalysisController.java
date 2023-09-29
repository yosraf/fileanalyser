package com.castlabs.fileanalyser.controller;


import com.castlabs.fileanalyser.model.MediaFileAnalysisResult;
import com.castlabs.fileanalyser.service.MediaFileAnalysisService;
import com.castlabs.fileanalyser.validation.MP4;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class MediaFileAnalysisController {
    private final MediaFileAnalysisService mediaFileAnalysisService;

    public MediaFileAnalysisController(MediaFileAnalysisService mediaFileAnalysisService) {
        this.mediaFileAnalysisService = mediaFileAnalysisService;
    }

    @GetMapping("/mp4-analysis")
    public ResponseEntity<MediaFileAnalysisResult> analyseMP4(@RequestParam @MP4 String url){
        return ResponseEntity.ok().build();
    }
}
