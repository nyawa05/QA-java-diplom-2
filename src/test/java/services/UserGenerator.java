package services;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.UserCredentials;

public class UserGenerator {
    static int targetStringLength = 6;

    public static String randomEmail() {
        return RandomStringUtils.randomAlphabetic(targetStringLength)+"@yandex.ru";
    }
    public static String randomPassword() {
        return RandomStringUtils.randomAlphanumeric(targetStringLength);
    }
    public static String randomName() {
        return RandomStringUtils.randomAlphabetic(targetStringLength);
    }

    public static UserCredentials randomUser() { return new UserCredentials(randomEmail(), randomPassword(), randomName()); }
}
