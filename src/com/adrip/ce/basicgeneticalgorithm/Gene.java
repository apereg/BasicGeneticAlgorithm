package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.exceptions.GeneticAlgorithmException;
import com.adrip.ce.utils.Utils;

import java.util.Objects;

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

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public void changeValue(int newValue) throws GeneticAlgorithmException {
        if (newValue < this.min || newValue > this.max)
            throw new GeneticAlgorithmException("Se esta intentando cambiar el valor del gen a " + newValue + "(Debe estar en [" + this.min + ", " + this.max + "])");
        this.value = newValue;
    }

    public void mutate() {
        this.value = Utils.generateRandom(this.min, this.max);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
            Gene gene = (Gene) o;
        return this.value == gene.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, min, max);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}