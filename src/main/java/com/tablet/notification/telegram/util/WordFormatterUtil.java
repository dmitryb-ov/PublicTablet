package com.tablet.notification.telegram.util;

public class WordFormatterUtil {

    public static String getCurrentDayWordLeaningNumber(int number) {
//        int length = (int) (Math.log10(number) + 1);
        if (number == 11 || number == 12 || number == 13 || number == 14) {
            return "дней";
        }

        if (number % 10 == 1) {
            return "день";
        }

        if (number % 10 >= 2 && number % 10 <= 4) {
            return "дня";
        }

        if (number % 10 >= 5 || number % 10 == 0) {
            return "дней";
        }

        return "день";
    }

    public static String getMustDayWordLeaningNumber(int number) {
        if (number % 10 == 1) {
            return "дня";
        } else {
            return "дней";
        }
    }
}
