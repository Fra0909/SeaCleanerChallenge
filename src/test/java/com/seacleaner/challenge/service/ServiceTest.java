package com.seacleaner.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seacleaner.challenge.exception.OutOfGridException;
import com.seacleaner.challenge.model.InputData;
import com.seacleaner.challenge.model.OutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    static ObjectMapper mapper = new ObjectMapper();

    //Regular case
    @Test
    public void regularTestCase1() throws JsonProcessingException {
        String input =
                "{\"areaSize\": [5, 5], \"startingPosition\": [2, 1], \"oilPatches\": [[2, 0], [1, 1], [2, 2], [3, 2]], \"navigationInstructions\": \"S\"}";

        OutputData outputData = Service.cleanSea(mapper.readValue(input, InputData.class));
        int[] expectedFinalCoordinates = {2, 0};
        int expectedPatchesCleaned = 1;

        assertArrayEquals(expectedFinalCoordinates, outputData.getFinalCoordinates());
        assertEquals(outputData.getOilPatchesCleaned(), expectedPatchesCleaned);
    }

    //Regular case
    @Test
    public void regularTestCase2() throws JsonProcessingException {
        String input =
                "{\"areaSize\": [7, 7], \"startingPosition\": [0, 0], \"oilPatches\": [[2, 2], [3, 3], [6, 6]], \"navigationInstructions\": \"NENENENENENE\"}";

        OutputData outputData = Service.cleanSea(mapper.readValue(input, InputData.class));
        int[] expectedFinalCoordinates = {6, 6};
        int expectedPatchesCleaned = 3;

        assertArrayEquals(expectedFinalCoordinates, outputData.getFinalCoordinates());
        assertEquals(outputData.getOilPatchesCleaned(), expectedPatchesCleaned);
    }

    //Duplicate oil patches should cause no issues as we are using a Set<int[]>, which does not allow duplicate entries.
    @Test
    public void duplicate_oil_patches_are_removed() throws JsonProcessingException {
        String input =
                "{\"areaSize\": [5, 5], \"startingPosition\": [2, 1], \"oilPatches\": [[2, 0], [2, 0], [2, 0], [2, 0]], \"navigationInstructions\": \"S\"}";

        OutputData outputData = Service.cleanSea(mapper.readValue(input, InputData.class));
        int[] expectedFinalCoordinates = {2, 0};
        int expectedPatchesCleaned = 1;

        assertArrayEquals(expectedFinalCoordinates, outputData.getFinalCoordinates());
        assertEquals(outputData.getOilPatchesCleaned(), expectedPatchesCleaned);
    }

    //OutOfGrid Exception
    @Test
    public void navigating_out_of_grid_throws_exception() {
        String input =
                "{\"areaSize\": [2, 2], \"startingPosition\": [0, 0], \"oilPatches\": [], \"navigationInstructions\": \"NNNNNNN\"}";

        assertThrows(OutOfGridException.class, () -> Service.cleanSea(mapper.readValue(input, InputData.class)));
    }


    //Checking that if starting cell is in oilPatches it does get removed from the list and counter increased.
    @Test
    public void if_starting_cell_is_oil_patch_it_gets_counted() throws JsonProcessingException {
        String input =
                "{\"areaSize\": [2, 2], \"startingPosition\": [0, 0], \"oilPatches\": [[0, 0]], \"navigationInstructions\": \"N\"}";

        OutputData outputData = Service.cleanSea(mapper.readValue(input, InputData.class));
        int[] expectedFinalCoordinates = {0, 1};
        int expectedPatchesCleaned = 1;

        assertArrayEquals(expectedFinalCoordinates, outputData.getFinalCoordinates());
        assertEquals(outputData.getOilPatchesCleaned(), expectedPatchesCleaned);
    }

    //Going through the same oil patch more than once to verify that it does not get counted twice.
    @Test
    public void going_through_same_patch_twice_does_not_count() throws JsonProcessingException {
        String input =
                "{\"areaSize\": [4, 4], \"startingPosition\": [0, 0], \"oilPatches\": [[0, 1]], \"navigationInstructions\": \"NSNSNSNSNSN\"}";

        OutputData outputData = Service.cleanSea(mapper.readValue(input, InputData.class));
        int[] expectedFinalCoordinates = {0, 1};
        int expectedPatchesCleaned = 1;

        assertArrayEquals(expectedFinalCoordinates, outputData.getFinalCoordinates());
        assertEquals(outputData.getOilPatchesCleaned(), expectedPatchesCleaned);
    }

    //Illegal starting position throws exception.
    @Test
    public void should_throw_exception_if_starting_position_is_illegal() throws JsonProcessingException {
        String input =
                "{\"areaSize\": [4, 4], \"startingPosition\": [33, 0], \"oilPatches\": [[0, 1]], \"navigationInstructions\": \"NSNSNSNSNSN\"}";

        assertThrows(OutOfGridException.class, () -> Service.cleanSea(mapper.readValue(input, InputData.class)));
    }
}
