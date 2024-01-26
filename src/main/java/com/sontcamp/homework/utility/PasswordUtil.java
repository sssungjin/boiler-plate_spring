package com.sontcamp.homework.utility;

import java.util.Random;
public class PasswordUtil {

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        int passwordLength = random.nextInt(11) + 10;

        for (int i = 0; i < passwordLength; i++) {
            int type = random.nextInt(3);
            switch (type) {
                case 0:
                    password.append((char) (random.nextInt(26) + 65)); // 대문자 알파벳
                    break;
                case 1:
                    password.append((char) (random.nextInt(26) + 97)); // 소문자 알파벳
                    break;
                case 2:
                    password.append((char) (random.nextInt(10) + 48)); // 숫자
                    break;
            }
        }

        return password.toString();
    }
}
