package com.nmatte.mood.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.valueOf;

public class CalendarDatabaseUtil {

    public static int dayDiff(Calendar startDate, Calendar endDate){
        int result = 0;

        // TODO ensure this works properly for dates across years


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


        // clear all other fields to avoid potential logic errors
        int startYear = startDate.get(Calendar.YEAR);
        int startDay = startDate.get(Calendar.DAY_OF_YEAR);
        startDate.clear();
        startDate.set(Calendar.YEAR,startYear);
        startDate.set(Calendar.DAY_OF_YEAR,startDay);


        int endYear = endDate.get(Calendar.YEAR);
        int endDay = endDate.get(Calendar.DAY_OF_YEAR);
        endDate.clear();
        endDate.set(Calendar.YEAR,endYear);
        endDate.set(Calendar.DAY_OF_YEAR,endDay);


        // swap dates if out of order
        if (startDate.after(endDate)){
            Calendar tmp = (Calendar) startDate.clone();
            startDate = (Calendar) endDate.clone();
            endDate = (Calendar) tmp.clone();
        }


        Calendar currentDate = (Calendar) startDate.clone();

        do {
            Calendar newCalendar = (Calendar) currentDate.clone();
            result.add(newCalendar);
            currentDate.roll(Calendar.DAY_OF_YEAR,1);
        } while(currentDate.before(endDate));




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
