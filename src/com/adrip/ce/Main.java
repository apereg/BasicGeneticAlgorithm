package com.adrip.ce;

import com.adrip.ce.basicgeneticalgorithm.GeneticAlgorithm;
import com.adrip.ce.basicgeneticalgorithm.Population;
import com.adrip.ce.utils.Mutations;

public class Main {

    /* PARAMETROS MODIFICABLES. */

    /* Valores numericos generales. */
    private static final int CHROMOSOMES = 5;
    private static final int GENES = 10;
    /* Debe tener las dimensiones acordes al numero de genes. */
    private static final int[][] GENE_RANGES = {{0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}};

    private static final boolean DEBUG_CREATE = false;

    private static final boolean DEBUG_EVALUATE = false;

    private static final boolean DEBUG_SELECT = true;
    private static final boolean ELITISM = true;
    private static final int ELITE_SIZE = 2;

    private static final boolean DEBUG_CROSSOVER = false;
    private static final int CROSSOVER_PROB = 90;

    /* Funcion MUTATE */
    private static final boolean DEBUG_MUTATE = false;
    /* El valor debe estar dentro del enum Mutations. */
    private static final Mutations MUTATION_TYPE = Mutations.PER_GENE;
    /* Valor *100 (Ejemplo: 15 = 15%). */
    private static final int MUTATE_PROB_PER_CHROMOSOME = 15;
    /* Valor *100 (Ejemplo: 15 = 15%). */
    private static final int MUTATE_PROB_PER_GENE = 5;

    private static final boolean DEBUG_VALIDSOL = false;

    private static final boolean NUMBER_GENERATIONS_CONDITION = true;
    private static final int GENERATIONS = 1;
    private static final boolean UPGRADE_GENERATIONS_CONDITION = false;
    private static final int NUMBER_OF_GENERATIONS_TO_CHECK = 50;
    /* Valor *100 (Ejemplo: 15 = 15%). */
    private static final int IMPROVEMENT_PERCENTAGE = 1;


    public static void main(String[] args) {
        GeneticAlgorithm algorithm = new GeneticAlgorithm(new Population());
        algorithm.run();
        algorithm.printSolution();
        //algorithm.showEvolution();
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

    public static Mutations getMutationType() {
        return Main.MUTATION_TYPE;
    }

    public static int getMutateProbPerChromosome() {
        return Main.MUTATE_PROB_PER_CHROMOSOME;
    }

    public static int getMutateProbPerGene() {
        return Main.MUTATE_PROB_PER_GENE;
    }

    public static boolean getNumberGenerationsCondition() {
        return NUMBER_GENERATIONS_CONDITION;
    }

    public static boolean getUpgradeGenerationsCondition() {
        return UPGRADE_GENERATIONS_CONDITION;
    }

    public static int getNumberOfGenerationsToCheck() {
        return NUMBER_OF_GENERATIONS_TO_CHECK;
    }

    public static int getImprovementPercentage() {
        return IMPROVEMENT_PERCENTAGE;
    }

    public static boolean getElitism() {
        return ELITISM;
    }

    public static int getEliteSize() {
        return ELITE_SIZE;
    }

    public static int getCrossoverProb() {
        return CROSSOVER_PROB;
    }

    @SuppressWarnings("unused")
    public static boolean getDebugCreate() {
        /* Se suprime el warning ya que si se llama (Usando reflexión). */
        return Main.DEBUG_CREATE;
    }

    @SuppressWarnings("unused")
    public static boolean getDebugEvaluate() {
        /* Se suprime el warning ya que si se llama (Usando reflexión). */
        return Main.DEBUG_EVALUATE;
    }

    @SuppressWarnings("unused")
    public static boolean getDebugSelect() {
        /* Se suprime el warning ya que si se llama (Usando reflexión). */
        return Main.DEBUG_SELECT;
    }

    @SuppressWarnings("unused")
    public static boolean getDebugCrossover() {
        /* Se suprime el warning ya que si se llama (Usando reflexión). */
        return Main.DEBUG_CROSSOVER;
    }

    @SuppressWarnings("unused")
    public static boolean getDebugMutate() {
        /* Se suprime el warning ya que si se llama (Usando reflexión). */
        return Main.DEBUG_MUTATE;
    }

    @SuppressWarnings("unused")
    public static boolean getDebugValidsol() {
        /* Se suprime el warning ya que si se llama (Usando reflexión). */
        return Main.DEBUG_VALIDSOL;
    }

}