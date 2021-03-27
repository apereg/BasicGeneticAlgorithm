package com.adrip.ce;

import com.adrip.ce.basicgeneticalgorithm.GeneticAlgorithm;
import com.adrip.ce.basicgeneticalgorithm.Population;
import com.adrip.ce.exceptions.GeneticAlgorithmException;
import com.adrip.ce.exceptions.ModifiableParameterException;
import com.adrip.ce.utils.Colors;
import com.adrip.ce.utils.Mutations;

public class Main {

    /* PARAMETROS MODIFICABLES. */
    private static final boolean MASTERMIND = true;
    private static final int CHROMOSOMES = 4;
    private static int GENES = 10;
    /* Debe tener las dimensiones acordes al numero de genes. */
    private static int[][] GENE_RANGES = {{0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}, {0, 100}};

    private static final boolean DEBUG_CREATE = false;

    private static final boolean DEBUG_EVALUATE = false;

    private static final boolean DEBUG_SELECT = false;
    private static final boolean ELITISM = true;
    private static final int ELITE_SIZE = 2;

    private static final boolean DEBUG_CROSSOVER = false;
    private static final int CROSSOVER_PROB = 85;
    /* En el intervalo [1, Numero de genes - 1] */
    private static final int CROSSOVER_POINTS = 2;

    private static final boolean DEBUG_MUTATE = false;
    /* El valor debe estar dentro del enum Mutations. */
    private static final Mutations MUTATION_TYPE = Mutations.PER_GENE;
    /* Valor *100 (Ejemplo: 15 = 15%). */
    private static final int MUTATE_PROB_PER_CHROMOSOME = 15;
    /* Valor *100 (Ejemplo: 15 = 15%). */
    private static final int MUTATE_PROB_PER_GENE = 5;

    private static final boolean DEBUG_VALIDSOL = false;
    private static final boolean NUMBER_GENERATIONS_CONDITION = true;
    private static final int GENERATIONS = 2000;
    private static final boolean UPGRADE_GENERATIONS_CONDITION = false;
    private static final int NUMBER_OF_GENERATIONS_TO_CHECK = 50;
    /* Valor *100 (Ejemplo: 15 = 15%). */
    private static final int IMPROVEMENT_PERCENTAGE = 1;

    public static void main(String[] args) throws GeneticAlgorithmException, ModifiableParameterException {
        checkParams();
        GeneticAlgorithm algorithm = new GeneticAlgorithm(new Population());
        algorithm.run();
        algorithm.printSolution();
        algorithm.showEvolution();
    }

    private static void checkParams() throws ModifiableParameterException {
        System.out.println(Colors.GREEN + "------------------------------------*------------------------------------");
        /* Se comprueba si el modo MASTERMIND esta activado y se modifican los parametros en base a ello. */
        if (Main.MASTERMIND) {
            Main.GENES = 4;
            Main.GENE_RANGES = new int[][]{{0, 5}, {0, 5}, {0, 5}, {0, 5}};
            System.out.println(Colors.GREEN + "El " + Colors.RED + "modo mastermind"
                    +Colors.GREEN+ " esta activado (Los genes han sido cambiados a 4 con rangos (0-5))");
        }

        /* Se comprueban todos los parametros en busca de inconsistencias. */
        StringBuilder exceptions = new StringBuilder();
        /* Parametros generales */
        if (Main.CHROMOSOMES < 1)
            exceptions.append("\tEl numero de cromosomas debe ser al menos 1\n");
        if (Main.GENES < 1)
            exceptions.append("\tEl numero de genes por cromosoma debe ser al menos 1\n");
        if (Main.GENE_RANGES.length != Main.GENES)
            exceptions.append("\tLa matriz de rangos de genes debe ser de tamaño [").append(Main.GENES).append("x2]\n");
        for (int i = 0; i < Main.GENE_RANGES.length; i++)
            if (Main.GENE_RANGES[i][0] > Main.GENE_RANGES[i][1])
                exceptions.append("\tLos rangos para el gen ").append(i).append(" son incorrectos (Minimo < Maximo)\n");
        /* Parametros de SELECT */
        if (Main.ELITISM && Main.ELITE_SIZE < 1)
            exceptions.append("\tEl tamaño de la elite debe ser al menos 1\n");
        /* Parametros de CROSSOVER */
        if (Main.CROSSOVER_PROB < 1 || Main.CROSSOVER_PROB > 100)
            exceptions.append("\tLa probabilidad de crossover debe estar en el intervalo [1-100]\n");
        if (Main.CROSSOVER_POINTS < 1 || Main.CROSSOVER_POINTS > Main.GENES - 1)
            exceptions.append("\tLos puntos de corte deben estar en el intervalo [1-").append(Main.GENES - 1).append("]\n");
        /* Parametros de MUTATE */
        if (Main.MUTATION_TYPE == Mutations.PER_GENE && (Main.MUTATE_PROB_PER_GENE < 1 || Main.MUTATE_PROB_PER_GENE > 100))
            exceptions.append("\tLa probabilidad de mutacion por gen debe estar en el intervalo [1-100]\n");
        if (Main.MUTATION_TYPE == Mutations.PER_CHROMOSOME && (Main.MUTATE_PROB_PER_CHROMOSOME < 1 || Main.MUTATE_PROB_PER_CHROMOSOME > 100))
            exceptions.append("\tLa probabilidad de mutacion por cromosoma debe estar en el intervalo [1-100]\n");
        /* Parametros de VALIDSOL */
        if (!Main.NUMBER_GENERATIONS_CONDITION && !Main.UPGRADE_GENERATIONS_CONDITION)
            exceptions.append("\tNo existe condicion de parada\n");
        if (Main.NUMBER_GENERATIONS_CONDITION && Main.GENERATIONS < 0)
            exceptions.append("\tEl numero de generaciones debe ser al menos 1\n");
        if (Main.UPGRADE_GENERATIONS_CONDITION && Main.IMPROVEMENT_PERCENTAGE < 1)
            exceptions.append("\tEl porcentaje de mejora de la poblacion no puede ser menor que 1\n");

        /* Si alguno de los parametros es inconsistente se muestra la información como excepcion. */
        if (exceptions.length() != 0)
            throw new ModifiableParameterException("Listado de fallos:\n" + exceptions.toString());

        /* Si todos los parametros son consistentes se muestra la informacion basica de ejecucion. */
        System.out.println("Configuracion de ejecucion:");
        System.out.println("\tNumero de cromosomas: " + Main.getNumChromosomes());
        System.out.println("\tNúmero de genes: " + Main.getNumGenes());
        System.out.println("\tMutacion " + ((Main.MUTATION_TYPE == Mutations.PER_CHROMOSOME) ? "por cromosoma (Prob: " + Main.MUTATE_PROB_PER_CHROMOSOME + "%)" : "por gen (Prob: " + Main.MUTATE_PROB_PER_GENE + "%)"));
        System.out.println("\tProbabilidad de cruce: " + Main.CROSSOVER_PROB + "%");
        System.out.println("\tElitismo: " + ((Main.ELITISM) ? "SI (" + Main.ELITE_SIZE + ")" : "NO"));
        System.out.println("------------------------------------*------------------------------------" + Colors.RESET);
    }

    public static boolean getMastermind() {
        return Main.MASTERMIND;
    }

    public static int getNumChromosomes() {
        return Main.CHROMOSOMES;
    }

    public static int getNumGenes() {
        return Main.GENES;
    }

    public static int getGenMinimum(int pos) {
        return Main.GENE_RANGES[pos][0];
    }

    public static int getGenMaximum(int pos) {
        return Main.GENE_RANGES[pos][1];
    }

    public static boolean getElitism() {
        return Main.ELITISM;
    }

    public static int getEliteSize() {
        return Main.ELITE_SIZE;
    }

    public static int getCrossoverProb() {
        return Main.CROSSOVER_PROB;
    }

    public static int getCrossoverPoints() {
        return Main.CROSSOVER_POINTS;
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
        return Main.NUMBER_GENERATIONS_CONDITION;
    }

    public static int getNumGenerations() {
        return Main.GENERATIONS;
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