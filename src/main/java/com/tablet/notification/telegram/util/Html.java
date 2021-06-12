package com.tablet.notification.telegram.util;

public class Html {

    private static final String STRONG = "<strong>";
    private static final String STRONG_ = "</strong>";

    private static final String BOLD = "<b>";
    private static final String BOLD_ = "</b>";

    private static final String I = "<i>";
    private static final String I_ = "</i>";

    private static final String DEL = "<del>";
    private static final String DEL_ = "</del>";

    public static String strong(String text) {
        return STRONG + text + STRONG_;
    }

    public static String strong(Number value) {
        return STRONG + value.toString() + STRONG_;
    }

    public static String bold(String text) {
        return BOLD + text + BOLD_;
    }

    public static String bold(Number value) {
        return BOLD + value.toString() + BOLD_;
    }

    public static String i(String text) {
        return I + text + I_;
    }

    public static String del(String text) {
        return DEL + text + DEL_;
    }
}
