package com.adrip.ce.basicgeneticalgorithm;

public class GenerationValues {

    private final int best;

    private final int mean;

    public GenerationValues(int best, int mean) {
        /* Objeto encapsulador de los datos historicos de una generacion. */
        this.best = best;
        this.mean = mean;
    }

    public int getBest() {
        return this.best;
    }

    public int getMean() {
        return this.mean;
    }

}
