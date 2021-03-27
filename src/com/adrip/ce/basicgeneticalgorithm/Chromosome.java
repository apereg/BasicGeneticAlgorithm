package com.adrip.ce.basicgeneticalgorithm;

import com.adrip.ce.Main;
import com.adrip.ce.exceptions.GeneticAlgorithmException;
import com.adrip.ce.utils.Colors;

import java.util.LinkedList;
import java.util.List;

public class Chromosome {

    private final List<Gene> geneChain;

    private int aptitude;

    public Chromosome() {
        this.geneChain = new LinkedList<>();
        this.aptitude = Integer.MIN_VALUE;
    }

    public Chromosome(Chromosome oldChromosome) {
        /* Se copian todos los valores de genes y aptitudes (Por valor) */
        this.geneChain = new LinkedList<>();
        for (Gene g : oldChromosome.geneChain)
            this.geneChain.add(new Gene(g.getValue(), g.getMin(), g.getMax()));
        this.aptitude = oldChromosome.getAptitude();
    }

    public int getAptitude() {
        return this.aptitude;
    }

    public int getSelectAptitude() {
        /* Para la ruleta de pesos solo se tendran en cuenta aptitudes positiva. */
        return Math.max(this.aptitude, 0);
    }

    public void setAptitude(int aptitude) {
        this.aptitude = aptitude;
    }

    public Gene getGene(int pos) throws GeneticAlgorithmException {
        if (pos >= geneChain.size())
            throw new GeneticAlgorithmException(
                    "Se esta intentando obtener el gen " + pos + " de un total de " + this.geneChain.size() + "genes");
        return this.geneChain.get(pos);
    }

    public void addGene(int value, int min, int max) throws GeneticAlgorithmException {
        if (geneChain.size() >= Main.getNumGenes())
            throw new GeneticAlgorithmException("Se esta intentando añadir el gen " + this.geneChain.size() + " de un total de " + Main.getNumGenes() + "posibles");
        /* Si el gen es valido se añade a la cadena del cromosoma. */
        geneChain.add(new Gene(value, min, max));
    }

    public void changeGene(int pos, int newValue) throws GeneticAlgorithmException {
        if (pos >= geneChain.size())
            throw new GeneticAlgorithmException("Se esta intentando cambiar el gen " + pos + " de un total de " + this.geneChain.size() + "genes");

        /* Si la posicion y el valor son validos se produce el cambio. */
        this.geneChain.get(pos).changeValue(newValue);
    }

    public void mutateGene(int pos) throws GeneticAlgorithmException {
        if (pos >= geneChain.size())
            throw new GeneticAlgorithmException("Se esta intentando mutar el gen " + pos + " de un total de " + this.geneChain.size() + "genes");
        this.geneChain.get(pos).mutate();
    }

    @Override
    public String toString() {
        /* Se devuelve el cromosoma en formato normal o mastermind depende de la variable parametrizable del main. */
        return (Main.getMastermind() ? this.mastermindToString() : this.normalToString());
    }

    private String normalToString() {
        StringBuilder str = new StringBuilder("(");
        /* Para cada gen del cromosoma se guarda en el String su propio toString (valor del gen). */
        for (Gene gene : geneChain)
            str.append(gene.toString()).append("\t");
        return str.deleteCharAt(str.length() - 1).append(")").toString();
    }

    private String mastermindToString() {
        StringBuilder str = new StringBuilder();
        /* Para cada gen del cromosoma se guarda en el String su color. */
        for (Gene gene : geneChain)
            str.append("[").append(Colors.getColor(gene.getValue())).append(" ").append(Colors.RESET).append("] ");
        return str.deleteCharAt(str.length() - 1).toString();
    }

}