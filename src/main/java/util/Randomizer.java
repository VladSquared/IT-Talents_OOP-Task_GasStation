package util;

import java.util.Random;

public class Randomizer {
    static Random r = new Random();

    public static int randomInt(int min, int max) {
        return r.nextInt(max - min + 1) + min;
    }
}
