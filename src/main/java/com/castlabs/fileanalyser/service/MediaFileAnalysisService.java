package com.castlabs.fileanalyser.service;

import com.castlabs.fileanalyser.model.MediaFileAnalysisResult;

public interface MediaFileAnalysisService {
    MediaFileAnalysisResult analyseMP4(String url);
}
