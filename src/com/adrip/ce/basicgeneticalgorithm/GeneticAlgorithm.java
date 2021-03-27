package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.exceptions.GeneticAlgorithmException;
import com.adrip.ce.outputs.EvolutionGraphic;
import com.adrip.ce.utils.Utils;

public class GeneticAlgorithm {

    private final Population population;

    private static Chromosome mastermindSolution;

    private static boolean solved;

    private Chromosome bestChromosome;

    private int bestChromosomeGeneration;

    public GeneticAlgorithm(Population population) {
        this.population = population;
    }

    public void run() throws GeneticAlgorithmException {
        System.out.println("\nComienza el algoritmo genetico");
        /* En caso de estar jugando mastermind se genera una solucion aleatoria. */
        if (Main.getMastermind()) {
            GeneticAlgorithm.mastermindSolution = new Chromosome();
            for (int i = 0; i < Main.getNumGenes(); i++)
                GeneticAlgorithm.mastermindSolution.addGene(Utils.generateRandom(0, 5), 0, 5);
            System.out.println("La solucion es " + mastermindSolution.toString() + " (No se la digas al algoritmo)");
        }

        /* Estructura del algoritmo genetico. */
        population.evaluate();
        System.out.println(population.toString() + "\n");
        Chromosome bestGenerationChromosome;
        bestGenerationChromosome = population.getBest();
        bestChromosome = bestGenerationChromosome;
        bestChromosomeGeneration = population.getGeneration();
        solved = false;
        while (!population.isValidSolution() && !solved) {
            population.newGeneration();
            population.select();
            population.crossover();
            population.mutate();
            population.evaluate();
            bestGenerationChromosome = population.getBest();
            if (bestGenerationChromosome.getAptitude() > bestChromosome.getAptitude()) {
                bestChromosome = bestGenerationChromosome;
                bestChromosomeGeneration = population.getGeneration();
            }
            population.saveHistorical();
        }
        System.out.println("\nHa acabado el algoritmo genetico");

    }

    public static void stop() {
        solved = true;
    }

    public void printSolution() {
        System.out.println(population.toString() + "\n");
        System.out.println("El mejor cromosoma fue obtenido en la generacion " + bestChromosomeGeneration + ", tenia aptitud " + bestChromosome.getAptitude() + " y una composicion " + bestChromosome.toString());
    }

    public void showEvolution() {
        new EvolutionGraphic(this.population);
    }

    public static Gene getSolution(int index) throws GeneticAlgorithmException {
        return GeneticAlgorithm.mastermindSolution.getGene(index);
    }

    public static String getSolutionRepresentation() {
        return GeneticAlgorithm.mastermindSolution.toString();
    }

}
