package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.exceptions.GeneticAlgorithmException;
import com.adrip.ce.utils.Colors;

import java.util.LinkedList;
import java.util.List;

public class Chromosome {

    private final List<Gene> geneChain;

    private int aptitude;

    public Chromosome() {
        this.geneChain = new LinkedList<>();
        this.aptitude = Integer.MIN_VALUE;
    }

    public Chromosome(Chromosome oldChromosome) throws GeneticAlgorithmException {
        this.aptitude = oldChromosome.getAptitude();
        this.geneChain = new LinkedList<>();
        for (int i = 0; i < Main.getNumGenes(); i++) {
            Gene gene = oldChromosome.getGene(i);
            this.geneChain.add(new Gene(gene.getValue(), gene.getMin(), gene.getMax()));
        }
    }

    public int getAptitude() {
        return this.aptitude;
    }

    public int getSelectAptitude() {
        return Math.max(this.aptitude, 0);
    }

    public void setAptitude(int aptitude) {
        this.aptitude = aptitude;
    }

    public void addGene(int value, int min, int max) throws GeneticAlgorithmException {
        if (geneChain.size() >= Main.getNumGenes())
            throw new GeneticAlgorithmException("Se esta intentando añadir el gen " + this.geneChain.size() + " de un total de " + Main.getNumGenes() + "posibles");
        geneChain.add(new Gene(value, min, max));
    }

    public Gene getGene(int pos) throws GeneticAlgorithmException {
        if (pos >= geneChain.size())
            throw new GeneticAlgorithmException(
                    "Se esta intentando obtener el gen " + pos + " de un total de " + this.geneChain.size() + "genes");
        return this.geneChain.get(pos);
    }

    public void changeGene(int pos, int newValue) throws GeneticAlgorithmException {
        if (pos >= geneChain.size())
            throw new GeneticAlgorithmException(
                    "Se esta intentando cambiar el gen " + pos + " de un total de " + this.geneChain.size() + "genes");
        this.geneChain.get(pos).changeValue(newValue);
    }

    public void mutateGene(int pos) throws GeneticAlgorithmException {
        if (pos >= geneChain.size())
            throw new GeneticAlgorithmException(
                    "Se esta intentando mutar el gen " + pos + " de un total de " + this.geneChain.size() + "genes");
        this.geneChain.get(pos).mutate();
    }

    @Override
    public String toString() {
        return (Main.getMastermind() ? this.mastermindToString() : this.normalToString());
    }

    private String mastermindToString() {
        StringBuilder str = new StringBuilder();
        for (Gene gene : geneChain)
            str.append("[").append(Colors.getColor(gene.getValue())).append(" ").append(Colors.RESET).append("] ");
        return str.deleteCharAt(str.length() - 1).toString();
    }

    private String normalToString() {
        StringBuilder str = new StringBuilder("(");
        for (Gene gene : geneChain)
            str.append(gene.toString()).append("\t");
        return str.deleteCharAt(str.length() - 1).append(")").toString();
    }

}