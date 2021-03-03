package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.utils.Utils;

public class Gene {

    private int value;

    private final int min;

    private final int max;

    public Gene(int value, int min, int max) {
        this.value = value;
        this.min = min;
        this.max = max;
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