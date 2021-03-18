package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.utils.Mutations;
import com.adrip.ce.utils.Operations;
import com.adrip.ce.utils.Utils;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Population {

    private LinkedList<Chromosome> town;

    private int generation;

    private HashMap<Integer, Integer[]> pastGenerationMeanAptitudes = new HashMap<>();

    public Population() {
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
            this.debug("El cromosoma " + (i + 1) + " es introducido en la poblacion " + chromosome.toString(), Operations.CREATE);
            this.town.add(chromosome);
        }
        this.generation = 0;
        /* Salto de linea para embellecer el resultado mostrado en la consola. */
        this.debug("", Operations.CREATE);
    }

    public void evaluate() {
        /* Se evalua un cromosoma como la suma de los valores de sus genes. */
        this.debug("Se va a evaluar la poblacion", Operations.EVALUATE);
        this.debug("El metodo escogido es la suma de sus " + Main.getNumGenes() + " genes", Operations.EVALUATE);
        for (Chromosome c : this.town) {
            this.debug("Evaluando el cromosoma " + c.toString(), Operations.EVALUATE);
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
        this.debug("", Operations.EVALUATE);
    }

    public void select() {
        this.debug("Va a comenzar la selección de la población", Operations.SELECT);
        this.debug("El metodo escogido es la ruleta con pesos", Operations.SELECT);
        this.debug("Se van a elegir " + Main.getNumChromosomes() + " nuevos cromosomas", Operations.SELECT);

        /* Se utilizan los valores absolutes de las aptitudes para facilitar los calculos. */
        int totalWeight = this.town.stream().mapToInt(Chromosome::getSelectAptitude).sum();
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
                actualRouletteBox = actualRouletteBox - this.town.get(j).getSelectAptitude();
                /* Si se encuentra se añade y se tira otra vez de la ruleta. */
                if (actualRouletteBox <= value) {
                    this.debug("Pertenece al cromosoma " + (j + 1), Operations.SELECT);
                    this.debug("Se va a añadir a la nueva poblacion el cromosoma " + this.town.get(j).toString(), Operations.SELECT);
                    newTown.add(new Chromosome(this.town.get(j)));
                    break;
                }
                j--;
            }
        }
        /* Se actualiza la población a la nueva generada. */
        this.town = newTown;
        /* Salto de linea para embellecer el resultado mostrado en la consola. */
        this.debug("", Operations.SELECT);
    }

    public void crossover() {
        this.debug("Va a comenzar el cruce de la población", Operations.CROSSOVER);
        this.debug("El metodo escogido es el cruzamiento con un punto de corte", Operations.CROSSOVER);
        for (int i = 0; i < Main.getNumChromosomes(); i += 2) {
            this.debug("Se va a cruzar el cromosoma " + (i + 1) + " y " + (i + 2), Operations.CROSSOVER);
            if ((i + 1) == this.town.size())
                break;
            int breakPoint = Utils.generateRandom(1, Main.getNumGenes() - 1);
            this.debug("El punto de corte escogido es el gen " + breakPoint, Operations.CROSSOVER);
            this.debug("Cromosomas antes de cruzar: " + this.town.get(i).toString() + " " + this.town.get(i + 1).toString(), Operations.CROSSOVER);
            for (int j = breakPoint; j < Main.getNumGenes(); j++) {
                this.debug("Cruzando el gen " + (j + 1), Operations.CROSSOVER);
                int value = this.town.get(i + 1).getGene(j).getValue();
                this.debug("Moviendo el gen del cromosoma " + (i + 2) + " al " + (i + 1), Operations.CROSSOVER);
                this.town.get(i + 1).changeGene(j, this.town.get(i).getGene(j).getValue());
                this.town.get(i).changeGene(j, value);
            }
            this.debug("Cromosomas despues de cruzar: " + this.town.get(i).toString() + " " + this.town.get(i + 1).toString(), Operations.CROSSOVER);
        }
        /* Salto de linea para embellecer el resultado mostrado en la consola. */
        this.debug("", Operations.CROSSOVER);
    }

    public void mutate() {
        this.debug("Va a comenzar la mutación de la población", Operations.MUTATE);
        if (Main.getMutationType() == Mutations.PER_CHROMOSOME) {
            this.mutatePerChromosome();
        } else {
            /* Caso por gen */
            this.mutatePerGene();
        }
    }

    private void mutatePerChromosome() {
        this.debug("El metodo escogido es la mutación por cromosoma", Operations.MUTATE);
        this.debug("La probabilidad de que un cromosoma mute es del " + Main.getMutateProbPerChromosome() + "%", Operations.MUTATE);
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            if (Utils.generateRandom(0, 99) < Main.getMutateProbPerChromosome()) {
                this.debug("El cromosoma " + (i + 1) + " va a mutar (Antes de mutar " + this.town.get(i).toString() + ")", Operations.MUTATE);
                int mutatePos = Utils.generateRandom(0, Main.getNumGenes() - 1);
                this.debug("Va a mutar el gen " + (mutatePos + 1), Operations.MUTATE);
                this.town.get(i).mutateGene(mutatePos);
                this.debug("El cromosoma " + (i + 1) + " despues de mutar es " + this.town.get(i).toString(), Operations.MUTATE);
            } else {
                this.debug("El cromosoma " + (i + 1) + " no muta", Operations.MUTATE);
            }
        }
    }

    private void mutatePerGene() {
        this.debug("El metodo escogido es la mutación por gen", Operations.MUTATE);
        this.debug("La probabilidad de que un gen mute es del " + Main.getMutateProbPerGene() + "%", Operations.MUTATE);
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            this.debug("Cromosoma " + (i + 1) + " antes de mutar " + this.town.get(i).toString(), Operations.MUTATE);
            this.debug("Comprobando si los genes tienen que mutar...", Operations.MUTATE);
            for (int j = 0; j < Main.getNumGenes(); j++) {
                /* Se recorren todos los genes de cada cromosoma comprobando con un numero aleatorio si tiene que mutar. */
                if (Utils.generateRandom(0, 99) < Main.getMutateProbPerGene()) {
                    this.debug("Muta el gen " + (j + 1), Operations.MUTATE);
                    /* La responsabilidad de mutar se delega en el gen (Conoce su minimo y maximo). */
                    this.town.get(i).mutateGene(j);
                }
            }
            this.debug("Cromosoma " + (i + 1) + " despues de mutar " + this.town.get(i).toString(), Operations.MUTATE);
        }
    }

    public Chromosome getBest() {
        /* Se comparan todas las aptitudes de la lista de la poblacion devolviendo el mejor cromosoma. */
        return this.town.stream().max(Comparator.comparing(Chromosome::getAptitude)).orElse(null);
    }

    public int getPopulationMeanAptitude() {
        return (int) this.town.stream().mapToInt(Chromosome::getAptitude).average().getAsDouble();
    }

    public boolean validSolution() {
        this.debug("\n------------------------------------*------------------------------------", Operations.VALIDSOL);
        this.debug("Va a comenzar la validación de la solución:", Operations.VALIDSOL);

        boolean result = false;
        if (Main.getNumberGenerationsCondition() && this.checkGenerationsElapsed())
            result = true;
        if (!result && Main.getUpgradeGenerationsCondition() && this.generation > Main.getNumberOfGenerationsToCheck() && checkPastGenerationsImprovement())
            result = true;

        this.debug("------------------------------------*------------------------------------\n", Operations.VALIDSOL);
        return result;
    }

    private boolean checkGenerationsElapsed() {
        this.debug("\nEvaluando la condición de parada del número de generaciones", Operations.VALIDSOL);
        this.debug("Generación " + this.generation + "/" + Main.getNumGenerations(), Operations.VALIDSOL);
        if (this.generation >= Main.getNumGenerations()) {
            this.debug("El algoritmo debe parar", Operations.VALIDSOL);
            return true;
        } else {
            this.debug("El algoritmo debe continuar", Operations.VALIDSOL);
            return false;
        }
    }

    private boolean checkPastGenerationsImprovement() {
        this.debug("\nEvaluando la condición de parada de mejora hace n generaciones (n=" + Main.getNumberOfGenerationsToCheck() + ")", Operations.VALIDSOL);
        double act = this.getPopulationMeanAptitude();
        double past = this.pastGenerationMeanAptitudes.get(this.generation - Main.getNumberOfGenerationsToCheck() - 1)[1];
        double improvementPercentage = (Main.getImprovementPercentage() + 100.0f) / 100.0f;
        this.debug("Aptitudes medias:", Operations.VALIDSOL);
        this.debug("\tGeneración actual: " + act, Operations.VALIDSOL);
        this.debug("\tHace " + Main.getNumberOfGenerationsToCheck() + " generaciones: " + past, Operations.VALIDSOL);
        double upgrade = (act / past);
        this.debug("Porcentaje de mejora: " + upgrade + " (Requerido " + improvementPercentage + "%)", Operations.VALIDSOL);
        if (upgrade <= improvementPercentage) {
            this.debug("El algoritmo debe parar", Operations.VALIDSOL);
            return true;
        } else {
            this.debug("El algoritmo debe continuar", Operations.VALIDSOL);
        }
        return false;
    }

    public void newGeneration() {
        this.pastGenerationMeanAptitudes.put(this.generation, new Integer[]{this.getBest().getAptitude(), this.getPopulationMeanAptitude()});
        ++this.generation;
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

    public int getGeneration() {
        return this.generation;
    }

    public int getMean(int generation) {
        Integer[] values = this.pastGenerationMeanAptitudes.get(generation);
        return values[1];
    }

    public int getBest(int generation) {
        Integer[] values = this.pastGenerationMeanAptitudes.get(generation);
        return values[0];
    }

}