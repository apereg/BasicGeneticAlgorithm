package com.adrip.ce;

public class Main {

    private static boolean debugCreate = true;

    private static boolean debugEvaluate = true;

    private static boolean debugGetBest = true;

    private static boolean debugSelect = true;

    private static boolean debugCrossover = true;

    private static boolean debugMutate = true;

    private static boolean debugValidSolution = true;

    private static int numChromosomes = 10;

    private static int numGenes = 5;

    public static int getNumChromosomes() {
        return Main.numChromosomes;
    }

    public static int getNumGenes() {
        return Main.numGenes;
    }

    public static int getGenMinimum(int pos) {
        return genRanges[0][pos];
    }

    public static int getGenMaximum(int pos) {
        return genRanges[1][pos];
    }

    /* Debe tener las dimensiones acordes al numero de genes. */
    private static int[][] genRanges = {{0,5},{0,5},{0,5},{0,5},{0,5}};

    public static void main(String[] args) {
        Population population = new Population();
        population.select();
    }

    public static boolean getDebugCreate() {
        return Main.debugCreate;
    }

    public static boolean getDebugEvaluate() {
        return Main.debugEvaluate;
    }

    public static boolean getDebugGetBest() {
        return Main.debugGetBest;
    }

    public static boolean getDebugSelect() {
        return Main.debugSelect;
    }

    public static boolean getDebugCrossover() {
        return Main.debugCrossover;
    }

    public static boolean getDebugMutate() {
        return Main.debugMutate;
    }

    public static boolean getDebugValidSolution() {
        return Main.debugValidSolution;
    }
}