package com.example.assignment2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date {

    public static String getDateAndTime(){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
}
