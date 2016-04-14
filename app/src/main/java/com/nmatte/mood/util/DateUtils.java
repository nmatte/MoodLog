package com.nmatte.mood.util;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.ModuleConfig;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class DateUtils {
    public static final String DATE_PATTERN = "YYYYDDD";
    public static final DateTimeFormatter YEAR_DAY_FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN),
            EDIT_ENTRY_FORMATTER = DateTimeFormat.shortDate();

    public static ArrayList<DateTime> getDatesInRange (DateTime start, DateTime end) {
        ArrayList<DateTime> dates = new ArrayList<>();

        if (start.isAfter(end)) {
            DateTime tmp = start;
            start = end;
            end = tmp;
        }

        DateTime current = start;

        while (current.isBefore(end)) {
            dates.add(current);
            current = current.plusDays(1);
        }

        return dates;
    }

    public static DateTime fromInt(int dateInt) {
        return DateTime.parse(String.valueOf(dateInt), YEAR_DAY_FORMATTER);
    }

    public static int getDateInt(DateTime date) {
        return Integer.valueOf(date.toString(YEAR_DAY_FORMATTER));
    }

    public static String getString(DateTime date) {
        return date.toString(YEAR_DAY_FORMATTER);
    }

    public static SimpleArrayMap<DateTime, ChartEntry> getEmptyMapInRange(DateTime start, DateTime end, ModuleConfig config) {
        SimpleArrayMap<DateTime, ChartEntry> result = new SimpleArrayMap<>();

        for (DateTime date : getDatesInRange(start, end)) {
            result.put(date, config.getBlank(date));
        }

        return result;
    }

}
