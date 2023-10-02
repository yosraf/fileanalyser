package com.castlabs.fileanalyser.service;

import com.castlabs.fileanalyser.model.MediaFileAnalysisResult;
import com.castlabs.fileanalyser.provider.MediaFileProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MediaFileAnalysisServiceImplTest {
   @Mock
   private MediaFileProvider mediaFileProvider;
   @InjectMocks
   private MediaFileAnalysisServiceImpl mediaFileAnalysisService;
   private final String ERROR_MSG="MP4 file length is too short";

    @BeforeEach
    public void initTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void analyseMP4() {
        byte[] bytes={0x00, 0x00, 0x00,0x10,0x6D, 0x66, 0x64, 0x68};
        Mockito.when(mediaFileProvider.fetchFileContent(any())).thenReturn(bytes);
        MediaFileAnalysisResult mediaFileAnalysisResult=mediaFileAnalysisService.analyseMP4("url");
        assertNotNull(mediaFileAnalysisResult);
        assertEquals(1,mediaFileAnalysisResult.boxes().size());
        System.out.println(mediaFileAnalysisResult.boxes().get(0).getSize());
        System.out.println(mediaFileAnalysisResult.boxes().get(0).getType());
        assertEquals(16,mediaFileAnalysisResult.boxes().get(0).getSize());
        assertEquals("mfdh",mediaFileAnalysisResult.boxes().get(0).getType());
    }
    @Test
    void analyseMP4InvalidMP4File() {
        byte[] bytes={0x00, 0x00, 0x00,0x10};
        Mockito.when(mediaFileProvider.fetchFileContent(any())).thenReturn(bytes);
        try {
            mediaFileAnalysisService.analyseMP4("url");
        }catch (RuntimeException exception){
            assertEquals(ERROR_MSG, exception.getMessage());
            return;
        }
        Assertions.fail("an error should be thrown");
    }
}