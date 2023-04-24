package com.comp7506.customplus.UI.tools;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class tools {
    public static class DateFormatter extends ValueFormatter {
        private final SimpleDateFormat mFormat = new SimpleDateFormat("MMM dd");

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(new Date((long) value));
        }
    }

    public static long getTimeInMillis(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sdf.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String[] codeToNameBrowserUrl(String code) {
        String[] result = new String[]{"", ""};
        switch (code) {
            case "Futian":
                result[0] = "Futian";
                result[1] = "NZQ";
                break;
            case "Shenzhen North":
                result[0] = "Shenzhenbei";
                result[1] = "IOQ";
                break;
            case "Guangzhou South":
                result[0] = "Guangzhounan";
                result[1] = "IZQ";
                break;
        }
        return result;
    }

    public static String getTodayYYYYMMDD() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Get today's date
            LocalDate today = null;
            today = LocalDate.now();
            // Define the date format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Format the date
            return today.format(formatter);
        } else {
            return "";
        }
    }

    public static String codeToName(String code) {
        switch (code) {
            case "XJA":
                return "West Kowloon";
            case "NZQ":
                return "Futian";
            case "IOQ":
                return "Shenzhen North";
            case "DNA":
                return "Dongguan South";
            case "RTQ":
                return "Dongguan";
            case "GGQ":
                return "Guangzhou East";
            case "IZQ":
                return "Guangzhou South";
            default:
                return "Unknown";
        }
    }
}
