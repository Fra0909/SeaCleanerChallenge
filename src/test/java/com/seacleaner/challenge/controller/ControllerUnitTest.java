package com.seacleaner.challenge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerUnitTest {

    private static final MockHttpServletRequestBuilder REQUEST_BUILDER =
            request(HttpMethod.POST, "/instructions")
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON);

    @Autowired
    private MockMvc mockMvc;

    private static final String BASIC_CASE_REQUEST =
            "{\"areaSize\": [5, 5], \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";

    private static final String OUT_OF_GRID_REQUEST =
            "{\"areaSize\": [5, 5], \"startingPosition\": [31, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";


    @Test
    void regularCaseReturnsCorrectResponse() throws Exception {
        mockMvc.perform(REQUEST_BUILDER.content(BASIC_CASE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"oilPatchesCleaned\":1,\"finalPosition\":[1,3]}"));
    }

    @Test
    void outOfGridExceptionAndInternalServerErrorWhenWrongInput() throws Exception {
        mockMvc.perform(REQUEST_BUILDER.content(OUT_OF_GRID_REQUEST))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("The robotic cleaner cannot be outside of the grid. Review the navigation instructions."));
    }

    @Test
    void badRequestExceptionWhenBadRequest() throws Exception {
        mockMvc.perform(REQUEST_BUILDER.content("badRequest"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input"));
    }



}