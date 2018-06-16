package com.watabou.utils;

import java.util.Calendar;

public class Holidays {

    public enum Holiday {
        NONE,
        EASTER,             // T BD
        BREAD_INDEPENDENT,  // 6월 4일 빵복절
        HWEEN,              // 2nd week of october though first day of november
        XMAS                // 3rd week of december through first week of january
    }

    private static Holiday holiday;

    static {
        holiday = Holiday.NONE;

        final Calendar calendar = Calendar.getInstance();

        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                if (calendar.get(Calendar.WEEK_OF_MONTH) == 1)
                    holiday = Holiday.XMAS;
                break;

            case Calendar.MAY:
                if (calendar.get(Calendar.WEEK_OF_MONTH) == 4)
                    holiday = Holiday.BREAD_INDEPENDENT;
                break;
            case Calendar.JUNE:
                if (calendar.get(Calendar.WEEK_OF_MONTH) <= 2)
                    holiday = Holiday.BREAD_INDEPENDENT;
                break;

            case Calendar.OCTOBER:
                if (calendar.get(Calendar.WEEK_OF_MONTH) >= 2)
                    holiday = Holiday.HWEEN;
                break;
            case Calendar.NOVEMBER:
                if (calendar.get(Calendar.DAY_OF_MONTH) == 1)
                    holiday = Holiday.HWEEN;
                break;

            case Calendar.DECEMBER:
                if (calendar.get(Calendar.WEEK_OF_MONTH) >= 3)
                    holiday = Holiday.XMAS;
                break;
        }
    }

    public static Holiday getHolidays() { return holiday; }
}