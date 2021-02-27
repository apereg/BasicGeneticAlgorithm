package com.adrip.ce;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    private Utils() {}

    public static int generateRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}