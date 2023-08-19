package com.seacleaner.challenge.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OutputData {
    private final int[] finalCoordinates;
    private final int oilPatchesCleaned;

    public OutputData(int[] finalCoordinates, int oilPatchesCleaned) {
        this.finalCoordinates = finalCoordinates;
        this.oilPatchesCleaned = oilPatchesCleaned;
    }

    public int[] getFinalCoordinates() {
        return finalCoordinates;
    }

    public int getOilPatchesCleaned() {
        return oilPatchesCleaned;
    }

    public String toJson() {
        Map<String, Object> result = new HashMap<>();
        result.put("finalPosition", Arrays.asList(finalCoordinates[0], finalCoordinates[1]));
        result.put("oilPatchesCleaned", oilPatchesCleaned);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException("Error converting result to JSON");
        }
    }

}

