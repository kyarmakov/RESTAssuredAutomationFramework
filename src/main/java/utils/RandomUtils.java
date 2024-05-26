package utils;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomUtils {
    private RandomUtils() {}

    public static String getRandomEmail() {
        return RandomStringUtils.random(8, true, true) + "@gmail.com";
    }
}
