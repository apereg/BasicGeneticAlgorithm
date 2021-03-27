package com.adrip.ce.basicgeneticalgorithm;

public class GenerationValues {

    private final int bestAptitude;

    private final int meanAptitude;

    public GenerationValues(int best, int mean) {
        this.bestAptitude = best;
        this.meanAptitude = mean;
    }

    public int getBestAptitude() {
        return this.bestAptitude;
    }

    public int getMeanAptitude() {
        return this.meanAptitude;
    }

}
