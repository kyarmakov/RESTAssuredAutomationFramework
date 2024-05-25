package utils;

import net.datafaker.Faker;

public final class RandomUtils {
    private static final Faker faker = new Faker();

    private RandomUtils() {}

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }
}
