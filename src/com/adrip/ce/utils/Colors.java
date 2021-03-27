package com.adrip.ce.utils;

public class Colors {

    /* Color codes. */
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String BLACK = "\033[0;30m";
    public static final String WHITE = "\033[0;37m";
    public static final String RED_BACKGROUND = "\033[0;101m";
    public static final String GREEN_BACKGROUND = "\033[0;102m";
    public static final String YELLOW_BACKGROUND = "\033[0;103m";
    public static final String BLUE_BACKGROUND = "\033[0;104m";
    public static final String PURPLE_BACKGROUND = "\033[0;105m";
    public static final String CYAN_BACKGROUND = "\033[0;106m";
    public static final String BLACK_BACKGROUND = "\033[0;100m";
    public static final String WHITE_BACKGROUND = "\033[0;107m";
    public static final String RESET = "\033[0m";

    public static String getColor(int number) {
        switch (number) {
            case 0:
                return RED_BACKGROUND;
            case 1:
                return GREEN_BACKGROUND;
            case 2:
                return YELLOW_BACKGROUND;
            case 3:
                return BLUE_BACKGROUND;
            case 4:
                return PURPLE_BACKGROUND;
            case 5:
                return CYAN_BACKGROUND;
            default:
                return BLACK_BACKGROUND;
        }
    }
}
