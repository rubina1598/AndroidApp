package com.hazyaz.mysapling.model;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Raff on 1/14/2020.
 */

public class dateConvertor {


    public static String timeLele(String mill) {
        long l = Long.parseLong(mill);

        SimpleDateFormat formatter = new SimpleDateFormat(" hh:mm:ss dd/MM/yyyy");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return formatter.format(calendar.getTime());

    }


}

