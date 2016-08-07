package com.techtask.android.picscramble.util;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AppUtils {
    public static long convertMSToSeconds(long ms) {
        return TimeUnit.MILLISECONDS.toSeconds(ms);
    }

    public static int randRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
