package com.adrip.ce;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

public class Population {

    private HashMap<Integer, Chromosome> town;

    private int generation;

    public Population() {
        this.generation = 0;
        this.town = new HashMap<>();
        this.createPopulation();
    }

    private void createPopulation() {
        this.debug("Va a comenzar la creación de la población", Operations.CREATE);
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            this.debug("Se crea el cromosoma numero " + i, Operations.CREATE);
            Chromosome chromosome = new Chromosome();
            for (int j = 0; j < Main.getNumGenes(); j++) {
                this.debug("Se añaden los " + Main.getNumGenes() + "genes", Operations.CREATE);
                int min = Main.getGenMinimum(j);
                int max = Main.getGenMaximum(j);
                chromosome.addGene(com.adrip.ce.Utils.generateRandom(min, max), min, max);
            }
            this.debug("El cromosoma " + i + " es introducido en la poblacion", Operations.CREATE);
            town.put(i, chromosome);
        }
    }

    public void select() {
        this.debug("Probando Select", Operations.SELECT);
    }

    public void evaluate() {
        this.debug("Probando Evaluate", Operations.EVALUATE);
    }

    public void crossover() {
        this.debug("Probando Crossover", Operations.CROSSOVER);
    }

    public void mutate() {
        this.debug("Probando mutate", Operations.MUTATE);
    }

    public void getBest() {
        this.debug("Probando getBest", Operations.GETBEST);
    }

    public boolean validSolution() {
        this.debug("Probando validSolution", Operations.VALIDSOL);
        return true;
    }

    public void newGeneration() {
        ++this.generation;
    }

    public int getGeneration() {
        return this.generation;
    }

    private void debug(String text, Operations operation) {
        /* Se formatea el nombre del metodo con getDebug y el enum con la primera letra mayuscula. */
        String methodName = "getDebug" + operation.toString().substring(0, 1) + operation.toString().substring(1).toLowerCase();
        try {
            /* Se usa reflexion para obtener la informacion de debug. */
            Method method = Main.class.getMethod(methodName);
            if ((Boolean) method.invoke(new Main()))
                System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Poblacion en generacion " + this.generation + ":\n");
        for (int i = 0; i < town.size(); i++)
            str.append(town.get(i)).append("\n");
        return str.deleteCharAt(str.length() - 1).toString();
    }
}