package com.adrip.ce;

import com.adrip.ce.basicgeneticalgorithm.Population;

public class Main {

    private static final int GENERATIONS = 10;

    private static final int CHROMOSOMES = 10;

    private static final int GENES = 5;

    /* Debe tener las dimensiones acordes al numero de genes. */
    private static final int[][] GENE_RANGES = {{-19, -10}, {-9, 0}, {1, 10}, {11, 20}, {21, 30}};

    private static final double MUTATE_PROB = 0.15;

    private static final boolean DEBUG_CREATE = false;

    private static final boolean DEBUG_EVALUATE = true;

    private static final boolean DEBUG_GETBEST = true;

    private static final boolean DEBUG_SELECT = true;

    private static final boolean DEBUG_CROSSOVER = true;

    private static final boolean DEBUG_MUTATE = true;

    private static final boolean DEBUG_VALIDSOL = true;

    public static void main(String[] args) {
        Population population = new Population();
        System.out.println("Se ha creado la poblacion");
        System.out.println(population.toString());
        population.crossover();
        System.out.println(population.toString());
    }

    public static int getNumGenerations() {
        return Main.GENERATIONS;
    }

    public static int getNumChromosomes() {
        return Main.CHROMOSOMES;
    }

    public static int getNumGenes() {
        return Main.GENES;
    }

    public static int getGenMinimum(int pos) {
        return GENE_RANGES[pos][0];
    }

    public static int getGenMaximum(int pos) {
        return GENE_RANGES[pos][1];
    }

    public static double getMutateProb() {
        return Main.MUTATE_PROB;
    }

    public static boolean getDebugCreate() {
        return Main.DEBUG_CREATE;
    }

    public static boolean getDebugEvaluate() {
        return Main.DEBUG_EVALUATE;
    }

    public static boolean getDebugGetbest() {
        return Main.DEBUG_GETBEST;
    }

    public static boolean getDebugSelect() {
        return Main.DEBUG_SELECT;
    }

    public static boolean getDebugCrossover() {
        return Main.DEBUG_CROSSOVER;
    }

    public static boolean getDebugMutate() {
        return Main.DEBUG_MUTATE;
    }

    public static boolean getDebugValidsol() {
        return Main.DEBUG_VALIDSOL;
    }

}