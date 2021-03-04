package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.exceptions.GeneticAlgorithmException;
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

    public void changeValue(int newValue) {
        if(newValue < this.min || newValue > this.max)
            throw new GeneticAlgorithmException("Se esta intentando cambiar el valor del gen a " +newValue+ "(Debe estar entre [" +this.min+ ", " +this.max+ "])");
        this.value = newValue;
    }
}