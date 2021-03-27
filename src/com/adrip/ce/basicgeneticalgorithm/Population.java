package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.exceptions.GeneticAlgorithmException;
import com.adrip.ce.utils.Mutations;
import com.adrip.ce.utils.Operations;
import com.adrip.ce.utils.Utils;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Population {

    private LinkedList<Chromosome> town;

    private final LinkedList<GenerationValues> pastGenerationMeanAptitudes = new LinkedList<>();

    private int generation;

    public Population() throws GeneticAlgorithmException {
        this.town = new LinkedList<>();
        this.createPopulation();
    }

    public void newGeneration() {
        ++this.generation;
    }

    public int getGeneration() {
        return this.generation;
    }

    public int getMean(int generation) {
        return this.pastGenerationMeanAptitudes.get(generation).getMeanAptitude();
    }

    public int getBest(int generation) {
        return this.pastGenerationMeanAptitudes.get(generation).getBestAptitude();
    }

    public int getPopulationMeanAptitude() {
        /* Se obtiene la media de aptitudes de los cromosomas de la lista. */
        return (int) this.town.stream().mapToInt(Chromosome::getAptitude).average().orElseThrow(IllegalStateException::new);
    }

    public Chromosome getBest() {
        /* Se comparan todas las aptitudes de la lista de la poblacion devolviendo el mejor cromosoma. */
        return this.town.stream().max(Comparator.comparing(Chromosome::getAptitude)).orElse(null);
    }

    public void saveHistorical() {
        this.pastGenerationMeanAptitudes.add(new GenerationValues(this.getBest().getAptitude(), this.getPopulationMeanAptitude()));
    }

    private void createPopulation() throws GeneticAlgorithmException {
        this.debug("------------------------------------*------------------------------------", Operations.CREATE);
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
        this.debug("------------------------------------*------------------------------------\n", Operations.CREATE);
    }

    public void evaluate() throws GeneticAlgorithmException {
        this.debug("------------------------------------*------------------------------------", Operations.EVALUATE);
        this.debug("Se va a evaluar la poblacion", Operations.EVALUATE);
        if (Main.getMastermind())
            this.mastermindEvaluate();
        else
            this.normalEvaluate();
        this.debug("------------------------------------*------------------------------------\n", Operations.EVALUATE);
    }

    private void normalEvaluate() throws GeneticAlgorithmException {
        /* Se evalua un cromosoma como la suma de los valores de sus genes. */
        this.debug("El metodo escogido es la suma de sus " + Main.getNumGenes() + " genes", Operations.EVALUATE);
        for (Chromosome c : this.town) {
            int aptitude = 0;
            for (int i = 0; i < Main.getNumGenes(); i++)
                aptitude += c.getGene(i).getValue();
            /* Se guarda la aptitud dentro del objeto Cromosoma. */
            c.setAptitude(aptitude);
            this.debug("Evaluando el cromosoma " + c.toString() + " -> " + aptitude, Operations.EVALUATE);
        }
    }

    private void mastermindEvaluate() throws GeneticAlgorithmException {
        this.debug("El metodo escogido es el Mastermind como fue explicado en clase", Operations.EVALUATE);
        int hits;
        int faults;
        int aptitude;
        boolean foundSol = false;
        this.debug("Solucion:\t" + GeneticAlgorithm.getSolutionRepresentation() + " (Que no se entere el algoritmo)", Operations.EVALUATE);
        for (Chromosome c : this.town) {
            hits = 0;
            faults = 0;
            for (int i = 0; i < Main.getNumGenes(); i++) {
                if (c.getGene(i).equals(GeneticAlgorithm.getSolution(i)))
                    hits++;
                else
                    faults++;
            }
            aptitude = Utils.getMastermindAptitude(hits, faults);
            this.debug("Evaluando:\t" + c.toString() + " (B:" + hits + ", N:" + faults + ") -> Aptitud: " + aptitude, Operations.EVALUATE);
            c.setAptitude(aptitude);
            if (aptitude == Utils.getMaxMastermind())
                foundSol = true;
        }
        if (foundSol) {
            System.out.println("El algoritmo ha ganado tras " + this.generation + " generaciones, no esta nada mal");
            GeneticAlgorithm.stop();

        }
    }

    public void select() {
        this.debug("------------------------------------*------------------------------------", Operations.SELECT);
        this.debug("Va a comenzar la selección de la población", Operations.SELECT);
        this.debug("El metodo escogido es la ruleta con pesos", Operations.SELECT);
        LinkedList<Chromosome> newTown = new LinkedList<>();
        if (Main.getElitism()) {
            this.debug("Se salvara la elite (" + Main.getEliteSize() + " cromosomas)", Operations.SELECT);
            /* Se ordena la lista segun la aptitud de los cromosomas. */
            this.town = this.town.stream().sorted(Comparator.comparingInt(Chromosome::getAptitude).reversed()).collect(Collectors.toCollection(LinkedList::new));
            for (int i = 0; i < Main.getEliteSize(); i++) {
                this.debug("Se añade a la nueva poblacion directamente el cromosoma " + this.town.getFirst().toString(), Operations.SELECT);
                newTown.add(new Chromosome(this.town.removeFirst()));
            }
        }

        this.debug("Se van a elegir " + this.town.size() + " nuevos cromosomas", Operations.SELECT);
        /* Se utilizan los valores absolutes de las aptitudes para facilitar los calculos. */
        int totalWeight = this.town.stream().mapToInt(Chromosome::getSelectAptitude).sum();
        this.debug("La suma de los valores absolutos de los pesos es " + totalWeight, Operations.SELECT);
        for (int i = 0; i < this.town.size(); i++) {
            /* Se gira la ruleta tantas veces como cromosomas tengamos para generar una nueva poblacion igual. */
            int value = Utils.generateRandom(0, totalWeight);
            this.debug("Se gira la ruleta y se obtiene " + value, Operations.SELECT);
            int actualRouletteBox = totalWeight;
            int j = this.town.size() - 1;
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
        this.debug("------------------------------------*------------------------------------\n", Operations.SELECT);
    }

    public void crossover() throws GeneticAlgorithmException {
        this.debug("------------------------------------*------------------------------------", Operations.CROSSOVER);
        this.debug("Va a comenzar el cruce de la población", Operations.CROSSOVER);
        this.debug("El metodo escogido es el cruzamiento con " + ((Main.getCrossoverPoints() == 1) ? "un punto de corte" : Main.getCrossoverPoints() + " puntos de corte"), Operations.CROSSOVER);
        for (int i = 0; i < Main.getNumChromosomes(); i += 2) {
            /* Si se cumple la probabilidad especificada se producira el cruce. */
            if (Utils.generateRandom(0, 99) < Main.getCrossoverProb()) {
                if ((i + 1) == this.town.size())
                    break;
                List<Integer> breakPoints = this.generateBreakPoints();
                this.debug("\nSe va a cruzar el cromosoma " + (i + 1) + " y " + (i + 2) + " (Puntos de corte: " + breakPoints.toString() + ")", Operations.CROSSOVER);
                breakPoints.add(0, 0);
                breakPoints.add(Main.getNumGenes());
                this.debug("Cromosomas antes de cruzar: \t" + this.town.get(i).toString() + "\t" + this.town.get(i + 1).toString(), Operations.CROSSOVER);
                /* Para cada dos puntos de corte se cambian los valores de ambos cromosomas en los genes especificados. */
                for (int j = 0; j < breakPoints.size() - 1; j += 2) {
                    for (int k = breakPoints.get(j); k < breakPoints.get(j + 1); k++) {
                        int value = this.town.get(i + 1).getGene(k).getValue();
                        this.town.get(i + 1).changeGene(k, this.town.get(i).getGene(k).getValue());
                        this.town.get(i).changeGene(k, value);
                    }
                }

                this.debug("Cromosomas despues de cruzar: \t" + this.town.get(i).toString() + "\t" + this.town.get(i + 1).toString(), Operations.CROSSOVER);
            } else {
                this.debug("\nNo se van a cruzar el cromosoma " + (i + 1) + " y " + (i + 2) + " (La probabilidad de cruce es del " + Main.getCrossoverProb() + "%)", Operations.CROSSOVER);
            }

        }
        this.debug("------------------------------------*------------------------------------\n", Operations.CROSSOVER);
    }

    private List<Integer> generateBreakPoints() {
        /* Metodo que devuelve una lista con tantos puntos de corte como parametro se especifique en el Main */
        LinkedList<Integer> breakPoints = new LinkedList<>();
        for (int i = 0; i < Main.getCrossoverPoints(); i++) {
            boolean added = false;
            int possiblePoint;
            while (!added) {
                /* Se generan posibles puntos de corte hasta que se encuentre uno que no se haya elegido ya. */
                possiblePoint = Utils.generateRandom(1, Main.getNumGenes() - 1);
                if (!breakPoints.contains(possiblePoint)) {
                    breakPoints.add(possiblePoint);
                    added = true;
                }
            }
        }
        /* Se ordena la lista de menor a mayor */
        breakPoints = breakPoints.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toCollection(LinkedList::new));
        return breakPoints;
    }

    public void mutate() throws GeneticAlgorithmException {
        this.debug("------------------------------------*------------------------------------", Operations.MUTATE);
        this.debug("Va a comenzar la mutación de la población", Operations.MUTATE);
        if (Main.getMutationType() == Mutations.PER_CHROMOSOME)
            this.mutatePerChromosome();
        else
            this.mutatePerGene();
        this.debug("------------------------------------*------------------------------------\n", Operations.MUTATE);
    }

    private void mutatePerChromosome() throws GeneticAlgorithmException {
        this.debug("El metodo escogido es la mutación por cromosoma", Operations.MUTATE);
        this.debug("La probabilidad de que un cromosoma mute es del " + Main.getMutateProbPerChromosome() + "%", Operations.MUTATE);
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            if (Utils.generateRandom(0, 99) < Main.getMutateProbPerChromosome()) {
                this.debug("Cromosoma " + (i + 1) + " antes de mutar:\t" + this.town.get(i).toString(), Operations.MUTATE);
                int mutatePos = Utils.generateRandom(0, Main.getNumGenes() - 1);
                this.debug("Va a mutar el gen " + (mutatePos + 1), Operations.MUTATE);
                this.town.get(i).mutateGene(mutatePos);
                this.debug("Cromosoma " + (i + 1) + " despues de mutar:\t" + this.town.get(i).toString(), Operations.MUTATE);
            }
        }
    }

    private void mutatePerGene() throws GeneticAlgorithmException {
        boolean muted;
        this.debug("El metodo escogido es la mutación por gen", Operations.MUTATE);
        this.debug("La probabilidad de que un gen mute es del " + Main.getMutateProbPerGene() + "%", Operations.MUTATE);
        for (int i = 0; i < Main.getNumChromosomes(); i++) {
            this.debug("Cromosoma " + (i + 1) + " antes de mutar:\t" + this.town.get(i).toString(), Operations.MUTATE);
            muted = false;
            for (int j = 0; j < Main.getNumGenes(); j++) {
                /* Se recorren todos los genes de cada cromosoma comprobando con un numero aleatorio si tiene que mutar. */
                if (Utils.generateRandom(0, 99) < Main.getMutateProbPerGene()) {
                    this.debug("Muta el gen " + (j + 1), Operations.MUTATE);
                    /* La responsabilidad de mutar se delega en el gen (Conoce su minimo y maximo). */
                    this.town.get(i).mutateGene(j);
                    muted = true;
                }
            }
            if (muted)
                this.debug("Cromosoma " + (i + 1) + " despues de mutar:\t" + this.town.get(i).toString(), Operations.MUTATE);
            else
                this.debug("El cromosoma " + (i + 1) + " no ha mutado", Operations.MUTATE);
        }
    }

    public boolean isValidSolution() {
        this.debug("------------------------------------*------------------------------------", Operations.VALIDSOL);
        this.debug("Va a comenzar la validación de la solución:", Operations.VALIDSOL);

        boolean result = false;
        if (Main.getNumberGenerationsCondition() && this.checkGenerationsElapsed())
            result = true;
        if (!result && Main.getUpgradeGenerationsCondition() && this.generation > (Main.getNumberOfGenerationsToCheck() - 1) && checkPastGenerationsImprovement())
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
        /* Se calcula la media de aptitud actual y se obtiene del historico la de hace n generaciones. */
        double act = this.getPopulationMeanAptitude();
        double past = this.getMean(this.generation - Main.getNumberOfGenerationsToCheck());
        this.debug("Aptitudes medias:", Operations.VALIDSOL);
        this.debug("\tGeneración actual: " + act, Operations.VALIDSOL);
        this.debug("\tHace " + Main.getNumberOfGenerationsToCheck() + " generaciones: " + past, Operations.VALIDSOL);
        /* Se dividen ambos valores para comprobar el porcentaje de mejora obtenido. */
        double upgrade = act / past;
        double improvementPercentage = (Main.getImprovementPercentage() + 100.0f) / 100.0f;
        this.debug("Porcentaje de mejora: " + upgrade + " (Requerido " + improvementPercentage + "%)", Operations.VALIDSOL);
        if (upgrade <= improvementPercentage) {
            this.debug("El algoritmo debe parar", Operations.VALIDSOL);
            return true;
        } else {
            this.debug("El algoritmo debe continuar", Operations.VALIDSOL);
        }
        return false;
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
        StringBuilder str = new StringBuilder("Poblacion (Gen " + this.generation + ")\n");
        /* Se guarda en el string cada cromosoma con su aptitud. */
        for (Chromosome c : this.town)
            str.append("\t").append(c.toString()).append(" -> ").append(c.getAptitude()).append("\n");
        return str.deleteCharAt(str.length() - 1).toString();
    }

}