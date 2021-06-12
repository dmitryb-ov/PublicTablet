package com.tablet.notification.telegram.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
@Slf4j
public class BotUtil {

    public static String formatMinutes(int minutes) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(minutes);
    }

    public static String getMoscowTime() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return dateFormat.format(date);
    }

    public static int getHourMoscowTime() {
        String[] arrTime = getMoscowTime().split(":");
        if (ValidationUtil.isNumeric(arrTime[0])) {
            return Integer.parseInt(arrTime[0]);
        } else {
            return 0;
        }
    }
}
