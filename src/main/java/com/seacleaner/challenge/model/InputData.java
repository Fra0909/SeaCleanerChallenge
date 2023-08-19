package com.seacleaner.challenge.model;

import java.util.Set;

public class InputData {
    private int[] areaSize;
    private int[] startingPosition;
    private Set<int[]> oilPatches;
    private String navigationInstructions;

    public InputData() {
    }

    public int[] getAreaSize() {
        return areaSize;
    }

    public int[] getStartingPosition() {
        return startingPosition;
    }

    public Set<int[]> getOilPatches() {
        return oilPatches;
    }

    public void setOilPatches(Set<int[]> oilPatches) {
        this.oilPatches = oilPatches;
    }

    public String getNavigationInstructions() {
        return navigationInstructions;
    }

    public void setNavigationInstructions(String navigationInstructions) {
        this.navigationInstructions = navigationInstructions;
    }
}
