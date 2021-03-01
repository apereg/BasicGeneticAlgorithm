package com.adrip.ce;

public class Main {

    private static final int GENERATIONS = 10;

    private static final int CHROMOSOMES = 10;

    private static final int GENES = 5;

    /* Debe tener las dimensiones acordes al numero de genes. */
    private static final int[][] GENE_RANGES = {{0, 5}, {0, 5}, {0, 5}, {0, 5}, {0, 5}};

    private static final boolean DEBUG_CREATE = true;

    private static final boolean DEBUG_EVALUATE = true;

    private static final boolean DEBUG_GETBEST = true;

    private static final boolean DEBUG_SELECT = true;

    private static final boolean DEBUG_CROSSOVER = true;

    private static final boolean DEBUG_MUTATE = true;

    private static final boolean DEBUG_VALIDSOLUTION = true;

    public static void main(String[] args) {
        Population population = new Population();
        population.select();
        System.out.println(population.toString());
    }

    public static int getNumGenerations() { return Main.GENERATIONS; }

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

    public static boolean getDebugCreate() {
        return Main.DEBUG_CREATE;
    }

    public static boolean getDebugEvaluate() {
        return Main.DEBUG_EVALUATE;
    }

    public static boolean getDebugGetBest() {
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

    public static boolean getDebugValidSolution() {
        return Main.DEBUG_VALIDSOLUTION;
    }

}