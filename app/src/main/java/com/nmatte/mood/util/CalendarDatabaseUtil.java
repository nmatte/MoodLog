package com.nmatte.mood.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.valueOf;

public class CalendarDatabaseUtil {

    public static int dayDiff(Calendar startDate, Calendar endDate){
        int result = 0;


        // swap if out of order
        if (startDate.after(endDate)){
            Calendar tmp = (Calendar) startDate.clone();
            startDate = (Calendar) endDate.clone();
            endDate = (Calendar) tmp.clone();
        }

        int startDay = startDate.get(Calendar.DAY_OF_YEAR);
        int endDay = endDate.get(Calendar.DAY_OF_YEAR);

        if (endDay - startDay >= 0){
            result = (endDay - startDay) + 1;
        } else {
            int firstYearDiff = (startDate.getActualMaximum(Calendar.DAY_OF_YEAR) - startDay) + 1;
            result = endDay + firstYearDiff + 1;
        }



        return result;
    }

    public static ArrayList<Calendar> datesBetween(Calendar startDate, Calendar endDate){
        ArrayList<Calendar> result = new ArrayList<>();
        return result;
    }

    public static int calendarToInt(Calendar date){
        DateFormat df = new SimpleDateFormat("yyyyDDD");
        String ds = df.format(date.getTime());
        return valueOf(ds);
    }

    public static Calendar intToCalendar(int dateNum){
        Calendar result = Calendar.getInstance();
        result.clear();
        int year = dateNum / 1000;
        int dayOfYear = dateNum % 1000;
        result.set(Calendar.YEAR,year);
        result.set(Calendar.DAY_OF_YEAR,dayOfYear);

        return result;
    }

    public static boolean sameDayOfYear(Calendar firstDay, Calendar secondDay){
        return (firstDay.get(Calendar.DAY_OF_YEAR) == secondDay.get(Calendar.DAY_OF_YEAR) &&
                firstDay.get(Calendar.YEAR) == secondDay.get(Calendar.YEAR));
    }
}
