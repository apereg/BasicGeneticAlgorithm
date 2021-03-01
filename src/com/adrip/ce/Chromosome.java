package com.adrip.ce;

import java.util.LinkedList;
import java.util.List;

public class Chromosome {

    private List<Gene> geneChain;

    public Chromosome() {
        this.geneChain = new LinkedList<>();
    }

    public void addGene(int value, int min, int max) {
        if(geneChain.size() >= Main.getNumGenes())
            throw new RuntimeException("Se esta intentando añadir el gen " +this.geneChain.size()+ " de un total de " +Main.getNumGenes()+ "posibles");
        geneChain.add(new Gene(value, min, max));
    }

    public void mutateGen(int pos) {
        if (pos >= geneChain.size())
            throw new RuntimeException(
                    "Se esta intentando mutar el gen " + pos + " de un total de " + this.geneChain.size() + "genes");
        this.geneChain.get(pos).mutate();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Gene gene : geneChain)
            str.append(gene.toString()).append("-");
        return str.deleteCharAt(str.length()-1).toString();
    }

}