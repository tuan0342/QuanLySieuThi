package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class GenerateCode {
    private static Random generator = new Random();

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;

    // Sinh số ngẫu nhiên từ 10000000 -> 99999999
    public static String generateNumber() {
        int max = 99999999;
        int min = 10000000;
        int value = generator.nextInt((max - min) + 1) + min;
        return String.valueOf(value);
    }

    // Sinh số ngẫu nhiên từ min -> max
    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    // Sinh chuỗi ngẫu nhiên
    public static String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    // Sinh ra ngày hiện tại
    public static String generateCurrentDate() {
        long millis=System.currentTimeMillis();  // yyyy--MM-dd
        java.sql.Date date = new java.sql.Date(millis);
        return date + "";
    }

    // Sinh mã item ngẫu nhiên
    public static String generateMaItem(String numberMaItem) {
        int numberOfCharactor = 4;
        String rs = randomAlphaNumeric(numberOfCharactor);
        return numberMaItem + "-" + rs;
    }

    // Sinh mã hóa đơn
    public static String generateMaHoaDon(String codeNumber) {
        String dateString = generateCurrentDate();
        return codeNumber + "-" + dateString;
    }
}
