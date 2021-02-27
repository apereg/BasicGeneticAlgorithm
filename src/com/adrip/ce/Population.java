package com.adrip.ce;
import java.util.HashMap;
import com.adrip.ce.Utils;

public class Population {

    private HashMap<Integer, Chromosome> population;

    private int generation;

    public Population() {
        this.generation = 0;
        this.population = new HashMap<>();
        this.createPopulation();
    }

    private void createPopulation() {
        this.debugCreatePopulation("Va a comenzar la creación de la población");
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            this.debugCreatePopulation("Se crea el cromosoma numero " +i);
            Chromosome chromosome = new Chromosome();
            for (int j = 0; j < Main.getNumGenes()  ; j++) {
                this.debugCreatePopulation("Se añaden los " +Main.getNumGenes()+ "genes");
                int min = Main.getGenMinimum(j);
                int max = Main.getGenMaximum(j);
                chromosome.addGene(Utils.generateRandom(min, max), min, max);
            }
            this.debugCreatePopulation("El cromosoma " +i+ " es introducido en la poblacion");
            population.put(i, chromosome);
        }
    }

    public void select() {

    }

    public void evaluate() {

    }

    public void newGeneration() {
        this.generation++;
    }

    public int getGeneration() {
        return this.generation;
    }

    private void debugCreatePopulation(String text) {
        if(Main.getDebugCreate())
            System.out.println(text);
    }

}