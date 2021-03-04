package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.utils.Operations;
import com.adrip.ce.utils.Utils;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedList;

public class Population {

    private LinkedList<Chromosome> town;

    private int generation;

    public Population() {
        this.generation = 0;
        this.town = new LinkedList<>();
        this.createPopulation();
    }

    private void createPopulation() {
        this.debug("Va a comenzar la creación de la población", Operations.CREATE);
        this.debug("El metodo escogido es la creación aleatoria", Operations.CREATE);
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            /* Se itera tantas veces como el numero de cromosomas creando cada gen con otro bucle. */
            this.debug("\nSe crea el cromosoma numero " + (i + 1), Operations.CREATE);
            Chromosome chromosome = new Chromosome();
            for (int j = 0; j < Main.getNumGenes(); j++) {
                /* Se guardan los valores en variables para facilitar el debug. */
                int min = Main.getGenMinimum(j);
                int max = Main.getGenMaximum(j);
                this.debug("Generando un valor aleatorio entre " + min + " y " + max + " para el gen " + (j + 1), Operations.CREATE);
                int value = Utils.generateRandom(min, max);
                this.debug("Añadiendo el gen " + (j + 1) + " con valor " + value + " (" + (j + 1) + "/" + (Main.getNumGenes()) + ")", Operations.CREATE);
                chromosome.addGene(value, min, max);
            }
            /* Tras generar todos los genes se introduce el cromosoma en el mapa. */
            this.debug("El cromosoma " + (i + 1) + " es introducido en la poblacion (" + chromosome.toString() + ")", Operations.CREATE);
            this.town.add(chromosome);
        }
        /* Salto de linea para embellecer el resultado mostrado en la consola. */
        this.debug("", Operations.CREATE);
    }

    public void evaluate() {
        /* Se evalua un cromosoma como la suma de los valores de sus genes. */
        this.debug("Se va a evaluar la poblacion", Operations.EVALUATE);
        this.debug("El metodo escogido es la suma de sus " + Main.getNumGenes() + " genes", Operations.EVALUATE);
        for (Chromosome c : this.town) {
            this.debug("Evaluando el cromosoma (" + c.toString() + ")", Operations.EVALUATE);
            int aptitude = 0;
            StringBuilder str = new StringBuilder("Sumando ");
            for (int i = 0; i < Main.getNumGenes(); i++) {
                /* Se va sumando la aptitud en un bucle. */
                str.append(c.getGene(i).getValue()).append(" + ");
                aptitude += c.getGene(i).getValue();
            }
            /* Se borra el ultimo " + ". */
            str.delete(str.length() - 3, str.length());
            /* Se guarda la aptitud dentro del objeto Cromosoma. */
            c.setAptitude(aptitude);
            this.debug(str.append(" = ").append(aptitude).toString(), Operations.EVALUATE);
        }
        /* Salto de linea para embellecer el resultado mostrado en la consola. */
        this.debug("", Operations.CREATE);
    }

    public void select() {
        this.debug("Va a comenzar la selección de la población", Operations.SELECT);
        this.debug("El metodo escogido es la ruleta con pesos", Operations.SELECT);
        this.debug("Se van a elegir " + Main.getNumChromosomes() + " nuevos cromosomas", Operations.SELECT);

        /* Se utilizan los valores absolutes de las aptitudes para facilitar los calculos. */
        int totalWeight = this.town.stream().mapToInt(Chromosome::getAbsAptitude).sum();
        this.debug("La suma de los valores absolutos de los pesos es " + totalWeight, Operations.SELECT);

        LinkedList<Chromosome> newTown = new LinkedList<>();
        for (int i = 0; i < this.town.size(); i++) {
            /* Se gira la ruleta tantas veces como cromosomas tengamos para generar una nueva poblacion igual. */
            int value = Utils.generateRandom(0, totalWeight);
            this.debug("Se gira la ruleta y se obtiene " + value, Operations.SELECT);
            int actualRouletteBox = totalWeight;
            int j = Main.getNumChromosomes() - 1;
            while (j >= 0) {
                /* Se calcula dinamicamente de final a inicio a que cromosoma pertenece el valor. */
                actualRouletteBox = actualRouletteBox - this.town.get(j).getAbsAptitude();
                /* Si se encuentra se añade y se tira otra vez de la ruleta. */
                if (actualRouletteBox <= value) {
                    this.debug("Pertenece al cromosoma " + (j + 1), Operations.SELECT);
                    this.debug("Se va a añadir a la nueva poblacion el cromosoma (" + this.town.get(j).toString() + ")", Operations.SELECT);
                    newTown.add(this.town.get(j));
                    break;
                }
                j--;
            }
        }
        /* Se actualiza la población a la nueva generada. */
        this.town = newTown;
        /* Salto de linea para embellecer el resultado mostrado en la consola. */
        this.debug("", Operations.CREATE);
    }

    public void crossover() {
        this.debug("Probando Crossover", Operations.CROSSOVER);
    }

    public void mutate() {
        this.debug("Va a comenzar la mutación de la población", Operations.MUTATE);
        this.debug("El metodo escogido es la mutación por gen", Operations.MUTATE);
        this.debug("La probabilidad de que un gen mute es del " + (Main.getMutateProb() * 100) + "%", Operations.MUTATE);

        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            for (int j = 0; j < Main.getNumGenes(); j++) {
                this.debug("Se va a comprobar la mutación en el gen " + (j + 1) + " del cromosoma " + (i + 1), Operations.MUTATE);
                if (Utils.generateRandom(0, 99) < Main.getMutateProb() * 100) {
                    this.debug("Muta", Operations.MUTATE);
                    this.town.get(i).getGene(j).mutate();
                } else {
                    this.debug("No muta", Operations.MUTATE);
                }
            }
        }
    }

    public Chromosome getBest() {
        this.debug("Se va a obtener al mejor de la generacion " + this.generation, Operations.GETBEST);
        /* Se comparan todas las aptitudes de la lista de la poblacion devolviendo el mejor cromosoma. */
        return this.town.stream().max(Comparator.comparing(Chromosome::getAptitude)).orElse(null);
    }

    public boolean validSolution() {
        this.debug("Va a comenzar la validación de la solución", Operations.VALIDSOL);
        this.debug("El metodo escogido es el numero de generaciones", Operations.VALIDSOL);
        this.debug("La población esta en la generación " + this.generation + " de " + Main.getNumGenerations(), Operations.VALIDSOL);
        if (this.generation >= Main.getNumGenerations() - 1) {
            this.debug("La solución ya es valida", Operations.VALIDSOL);
            return true;
        }
        this.debug("La solución aun no es valida", Operations.VALIDSOL);
        return false;
    }

    public void newGeneration() {
        ++this.generation;
    }

    public int getGeneration() {
        return this.generation;
    }

    private void debug(String text, Operations operation) {
        /* Se formatea el nombre del metodo con getDebug y el enum con la primera letra mayuscula. */
        String methodName = "getDebug" + operation.toString().charAt(0) + operation.toString().substring(1).toLowerCase();
        try {
            /* Se usa reflexion para obtener la informacion de debug. */
            Method method = Main.class.getMethod(methodName);
            if (Boolean.TRUE.equals(method.invoke(null)))
                System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Poblacion en generacion " + this.generation + ":\n");
        for (Chromosome c : this.town)
            str.append(c.toString()).append("\n");
        return str.deleteCharAt(str.length() - 1).toString();
    }
}