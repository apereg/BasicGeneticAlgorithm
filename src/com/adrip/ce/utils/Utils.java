package com.adrip.ce.utils;

import com.adrip.ce.Main;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    private Utils() {
    }

    public static int generateRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int getMastermindAptitude(int hits, int faults) {
        int sum = 0;
        for (int i = 1; i < Main.getNumGenes(); i++)
            sum += i;
        return ((2 * hits) + faults + sum);
    }

    public static int getMaxMastermind() {
        return Utils.getMastermindAptitude(Main.getNumGenes(), 0);
    }

}