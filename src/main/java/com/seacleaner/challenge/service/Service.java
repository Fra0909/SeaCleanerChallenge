package com.seacleaner.challenge.service;

import com.seacleaner.challenge.exception.OutOfGridException;
import com.seacleaner.challenge.model.InputData;
import com.seacleaner.challenge.model.OutputData;

public class Service {

    public static OutputData cleanSea(InputData inputData) {
        int width = inputData.getAreaSize()[0];
        int height = inputData.getAreaSize()[1];

        int oilPatchesCleaned = 0;
        int[] currentPosition = inputData.getStartingPosition();

        //Adding an extra "empty step" that will be used as a null instruction to check if the initial
        //cell is an oil patch and if the initial position is illegal.
        inputData.setNavigationInstructions("0" + inputData.getNavigationInstructions());

        for (char instruction : inputData.getNavigationInstructions().toCharArray()) {
            int currentX = currentPosition[0];
            int currentY = currentPosition[1];

            switch (instruction) {
                case 'N':
                    currentY++;
                    break;
                case 'S':
                    currentY--;
                    break;
                case 'E':
                    currentX++;
                    break;
                case 'W':
                    currentX--;
                    break;
            }

            if (isValidPosition(new int[] {currentX, currentY}, width, height)) {
                currentPosition[0] = currentX;
                currentPosition[1] = currentY;

                if (inputData.getOilPatches().removeIf(cell -> cell[0] == currentPosition[0] && cell[1] == currentPosition[1])) {
                    oilPatchesCleaned++;
                }

            } else {
                throw new OutOfGridException("The robotic cleaner cannot be outside of the grid. Review the navigation instructions.");
            }

        }
        return new OutputData(currentPosition, oilPatchesCleaned);
    }

    private static boolean isValidPosition(int[] position, int width, int height) {
        int x = position[0];
        int y = position[1];
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
