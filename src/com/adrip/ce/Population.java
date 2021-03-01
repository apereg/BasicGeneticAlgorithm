package com.adrip.ce;

import java.util.HashMap;

public class Population {

    private HashMap<Integer, Chromosome> town;

    private int generation;

    public Population() {
        this.generation = 0;
        this.town = new HashMap<>();
        this.createPopulation();
    }

    private void createPopulation() {
        this.debugCreatePopulation("Va a comenzar la creación de la población");
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            this.debugCreatePopulation("Se crea el cromosoma numero " + i);
            Chromosome chromosome = new Chromosome();
            for (int j = 0; j < Main.getNumGenes(); j++) {
                this.debugCreatePopulation("Se añaden los " + Main.getNumGenes() + "genes");
                int min = Main.getGenMinimum(j);
                int max = Main.getGenMaximum(j);
                chromosome.addGene(com.adrip.ce.Utils.generateRandom(min, max), min, max);
            }
            this.debugCreatePopulation("El cromosoma " + i + " es introducido en la poblacion");
            town.put(i, chromosome);
        }
    }

    public void select() {
        this.debugSelect("Probando Select");
    }

    public void evaluate() {
        this.debugEvaluate("Probando Evaluate");
    }

    public void crossover() {
        this.debugCrossover("Probando Crossover");
    }

    public void mutate() {
        this.debugMutate("Probando mutate");
    }

    public void getBest() {
        this.debugGetBest("Probando getBest");
    }

    public boolean validSolution() {
        this.debugValidSolution("Probando validSolution");
        return true;
    }

    public void newGeneration() {
        ++this.generation;
    }

    public int getGeneration() {
        return this.generation;
    }

    private void debugCreatePopulation(String text) {
        if (Main.getDebugCreate())
            System.out.println(text);
    }

    private void debugSelect(String text) {
        if (Main.getDebugSelect())
            System.out.println(text);
    }

    private void debugEvaluate(String text) {
        if (Main.getDebugEvaluate())
            System.out.println(text);
    }

    private void debugCrossover(String text) {
        if (Main.getDebugCrossover())
            System.out.println(text);
    }

    private void debugMutate(String text) {
        if (Main.getDebugMutate())
            System.out.println(text);
    }

    private void debugGetBest(String text) {
        if (Main.getDebugGetBest())
            System.out.println(text);
    }

    private void debugValidSolution(String text) {
        if (Main.getDebugValidSolution())
            System.out.println(text);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Poblacion en generacion " + this.generation + ":\n");
        for (int i = 0; i < town.size(); i++)
            str.append(town.get(i)).append("\n");
        return str.deleteCharAt(str.length()-1).toString();
    }
}