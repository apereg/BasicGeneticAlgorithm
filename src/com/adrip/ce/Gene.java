package com.adrip.ce;

public class Gene {

    private int value;

    private int min;

    private int max;

    public Gene(int value, int min, int max) {
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public int getMinimum() {
        return this.min;
    }

    public int getMaximum() {
        return this.max;
    }

    public int getValue() {
        return this.value;
    }

    public void mutate() {
        this.value = Utils.generateRandom(this.min, this.max);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

}