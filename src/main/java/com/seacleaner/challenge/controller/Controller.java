package com.seacleaner.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seacleaner.challenge.model.InputData;
import com.seacleaner.challenge.exception.OutOfGridException;
import com.seacleaner.challenge.model.OutputData;
import com.seacleaner.challenge.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    static ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/instructions")
    public ResponseEntity parseInstructions(@RequestBody String instructionsJson) {
        try {
            InputData inputData = mapper.readValue(instructionsJson, InputData.class);
            OutputData outputData = Service.cleanSea(inputData);
            return new ResponseEntity<>(outputData.toJson(), HttpStatus.OK);
        } catch (OutOfGridException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
    }
}
