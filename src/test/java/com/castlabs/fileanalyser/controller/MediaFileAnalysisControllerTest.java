package com.castlabs.fileanalyser.controller;

import com.castlabs.fileanalyser.model.ApiError;
import com.castlabs.fileanalyser.model.MediaFileAnalysisResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class MediaFileAnalysisControllerTest {
    @Autowired protected MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static final String VALID_URL="/mp4-analysis?url=https://demo.castlabs.com/tmp/text0.mp4";
    private static final String INVALID_URL="/mp4-analysis?url=https://demo.castlabs.com/tmp/text0";
    @BeforeEach
    public void init() {
        this.objectMapper = new ObjectMapper();
    }
    @Test
    public void analyseMP4() throws Exception {
        ResultActions analyseResult =
                mockMvc.perform(
                        MockMvcRequestBuilders.
                                get(VALID_URL));
        analyseResult.andExpect(status().isOk());
        assertTrue(
                StringUtils.hasText(analyseResult.andReturn().getResponse().getContentAsString()));
        String content = analyseResult.andReturn().getResponse().getContentAsString();
        assertTrue(StringUtils.hasText(content));
        MediaFileAnalysisResult mediaFileAnalysisResult =
                objectMapper.readValue(content, new TypeReference<>() {
                });
        assertNotNull(mediaFileAnalysisResult);
    }
    @Test
    public void analyseMP4InvalidFormat() throws Exception {
        ResultActions analyseResult =
                mockMvc.perform(
                        MockMvcRequestBuilders.
                                get(INVALID_URL));
        analyseResult.andExpect(status().is4xxClientError());
        assertTrue(
                StringUtils.hasText(analyseResult.andReturn().getResponse().getContentAsString()));
        String content = analyseResult.andReturn().getResponse().getContentAsString();
        assertTrue(StringUtils.hasText(content));
        ApiError apiError =
                objectMapper.readValue(content, new TypeReference<>() {
                });
        assertNotNull(apiError);
        assertEquals(apiError.status(), HttpStatus.BAD_REQUEST);
        assertEquals(apiError.message(), "invalid format, only MP4 is supported");
    }
}