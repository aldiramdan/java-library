package com.aldiramdan.library.utils;

import java.util.Random;
import java.util.UUID;

public class GenerateRandom {
    public static String token() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String code() {
        int min = 100000;
        int max = 999999;
        return String.valueOf(new Random().nextInt(max - min + 1) + min);
    }
}
