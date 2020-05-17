package ru.geekbrains.utils;

import java.util.Random;

public class Rnd {
    private Rnd() {}

    private static final Random random = new Random();

    public static float nextFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
}
