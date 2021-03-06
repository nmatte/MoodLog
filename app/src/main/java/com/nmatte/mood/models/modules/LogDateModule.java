package com.nmatte.mood.models.modules;


import com.nmatte.mood.adapters.LogDateAdapter;
import com.nmatte.mood.adapters.ModuleAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class LogDateModule extends Module {
    public static final String DATE_PATTERN = "YYYYDDD";
    public static final DateTimeFormatter YEAR_DAY_FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN),
            EDIT_ENTRY_FORMATTER = DateTimeFormat.shortDate();
    final DateTime date;


    public LogDateModule(DateTime date) {
        super(-1, "LogDateModule", true);
        this.date = date;
    }

    public LogDateModule() {
        super(-1, "LogDateModule", true);
        this.date = DateTime.now();
    }


    @Override
    public ModuleAdapter getViewAdapter() {
        return new LogDateAdapter( this);
    }

    public DateTime getDate() {
        return date;
    }

    public boolean isToday() {
        return date.getDayOfYear() == DateTime.now().getDayOfYear();
    }

    /**
     * Gets the date as mm/dd or dd/mm depending on DateTime's format for the locale.
     * @return The short date string for the date.
     */
    public String getMonthDayString(){
        String dateString = date.toString(EDIT_ENTRY_FORMATTER);

        DateTime testDate = date;

        // We need mm to be different from yy so we can tell them apart.
        while(testDate.toString("yy").equals(testDate.toString("mm")) || testDate.toString("yy").equals(testDate.toString("dd"))) {
            testDate = testDate.minusYears(1);
        }

        // it's ok to mess with the testDate's year because we're not going to display it.
        ArrayList<String> nums = new ArrayList<>();
        String currentNum = "";

        for (char c : dateString.toCharArray()) {
            if (Character.isDigit(c)){
                currentNum += c;
            } else {
                if (!currentNum.equals("")){
                    if (!currentNum.equals(testDate.toString("yy")) && !currentNum.equals(date.toString("yyyy"))){
                        nums.add(currentNum);
                    }
                    currentNum = "";
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        if (nums.size() >= 2){
            builder
                    .append(nums.get(0))
                    .append("/")
                    .append(nums.get(1));
        }
        return builder.toString();

    }
}
