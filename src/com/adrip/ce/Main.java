package com.adrip.ce;

import com.adrip.ce.basicgeneticalgorithm.Chromosome;
import com.adrip.ce.basicgeneticalgorithm.Population;
import com.adrip.ce.utils.Mutations;

public class Main {

    private static final int GENERATIONS = 10000;

    private static final int CHROMOSOMES = 20;

    private static final int GENES = 5;

    /* Debe tener las dimensiones acordes al numero de genes. */
    private static final int[][] GENE_RANGES = {{0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}};

    /* El valor debe estar dentro del enum Mutations. */

    private static final boolean DEBUG_CREATE = false;

    private static final Mutations MUTATION_TYPE = Mutations.PER_CHROMOSOME;

    private static final int MUTATE_PROB_PER_CHROMOSOME = 20;

    private static final int MUTATE_PROB_PER_GENE = 15;

    private static final boolean DEBUG_MUTATE = false;

    private static final boolean DEBUG_EVALUATE = false;

    private static final boolean DEBUG_GETBEST = false;

    private static final boolean DEBUG_SELECT = false;

    private static final boolean DEBUG_CROSSOVER = false;


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
        while (!population.validSolution()) {
            population.select();
            population.crossover();
            population.mutate();
            population.evaluate();
            bestGenerationChromosome = population.getBest();
            if (bestGenerationChromosome.getAptitude() > bestChromosome.getAptitude()) {
                bestChromosome = bestGenerationChromosome;
                bestChromosomeGeneration = population.getGeneration();
            }
            population.newGeneration();
        }
        System.out.println("Ha acabado el algoritmo genetico");
        System.out.println(population.toString());
        System.out.println();
        System.out.println("El mejor cromosoma fue obtenido en la generacion " + bestChromosomeGeneration + ", tenia aptitud " + bestChromosome.getAptitude() + " y una composicion " + bestChromosome.toString());
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
    public static boolean getDebugGetbest() {
        /* Se suprime el warning ya que si se llama (Usando reflexión). */
        return Main.DEBUG_GETBEST;
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