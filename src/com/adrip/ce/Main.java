package com.adrip.ce;

import com.adrip.ce.basicgeneticalgorithm.Chromosome;
import com.adrip.ce.basicgeneticalgorithm.Population;

public class Main {

    private static final int GENERATIONS = 2;

    private static final int CHROMOSOMES = 5;

    private static final int GENES = 5;

    /* Debe tener las dimensiones acordes al numero de genes. */
    private static final int[][] GENE_RANGES = {{0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}};

    private static final int MUTATE_PROB = 25;

    private static final boolean DEBUG_CREATE = false;

    private static final boolean DEBUG_EVALUATE = false;

    private static final boolean DEBUG_GETBEST = false;

    private static final boolean DEBUG_SELECT = false;

    private static final boolean DEBUG_CROSSOVER = true;

    private static final boolean DEBUG_MUTATE = false;

    private static final boolean DEBUG_VALIDSOL = false;

    public static void main(String[] args) {
        Population population = new Population();
        Chromosome bestChromosome;
        Chromosome bestGenerationChromosome;
        int bestChromosomeGeneration;
        System.out.println("Se ha creado la poblacion");
        System.out.println(population.toString());
        population.evaluate();
        bestGenerationChromosome = population.getBest();
        bestChromosome = bestGenerationChromosome;
        bestChromosomeGeneration = population.getGeneration();
        while(!population.validSolution()){
            population.select();
            population.crossover();
            population.mutate();
            population.evaluate();
            bestGenerationChromosome = population.getBest();
            if(bestGenerationChromosome.getAptitude() > bestChromosome.getAptitude()) {
                bestChromosome = bestGenerationChromosome;
                bestChromosomeGeneration = population.getGeneration();
            }
            population.newGeneration();
        }
        System.out.println("Ha acabado el algoritmo genetico");
        System.out.println(population.toString());
        System.out.println();
        System.out.println("El mejor cromosoma fue obtenido en la generacion " +bestChromosomeGeneration+ ", tenia aptitud " +bestChromosome.getAptitude()+ " y una composicion (" +bestChromosome.toString()+ ")");
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

    public static int getMutateProb() {
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