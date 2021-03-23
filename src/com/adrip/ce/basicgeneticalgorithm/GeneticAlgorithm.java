package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.outputs.EvolutionGraphic;

public class GeneticAlgorithm {

    private Population population;

    public GeneticAlgorithm(Population population) {
        this.population = population;
    }

    public void run() {
        Chromosome bestChromosome;
        Chromosome bestGenerationChromosome;
        int bestChromosomeGeneration;
        System.out.println("Se ha creado la poblacion");
        System.out.println(population.toString());
        population.evaluate();
        bestGenerationChromosome = population.getBest();
        bestChromosome = bestGenerationChromosome;
        bestChromosomeGeneration = population.getGeneration();
        while (!population.isValidSolution()) {
            System.out.println("Empieza la gen " + population.getGeneration());
            population.select();
            population.crossover();
            population.mutate();
            population.evaluate();
            bestGenerationChromosome = population.getBest();
            if (bestGenerationChromosome.getAptitude() > bestChromosome.getAptitude()) {
                bestChromosome = bestGenerationChromosome;
                bestChromosomeGeneration = population.getGeneration();
            }
            System.out.println("Acaba la gen " + population.getGeneration());
            population.newGeneration();
        }
        System.out.println("Ha acabado el algoritmo genetico");
        System.out.println(population.toString());
        System.out.println();

        System.out.println("El mejor cromosoma fue obtenido en la generacion " + bestChromosomeGeneration + ", tenia aptitud " + bestChromosome.getAptitude() + " y una composicion " + bestChromosome.toString());
    }

    public void printSolution() {
    }

    public void showEvolution() {
        new EvolutionGraphic(population);
    }

}
