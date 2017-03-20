package com.codepath.nytimes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Saranu on 3/17/17.
 */

public class DateUtil{

    public static String formatDatetoAPI(String dateString) {
        String formattedDate=null;
        try {
            formattedDate =  new SimpleDateFormat("yyyyMMdd", Locale.US).
                    format(new SimpleDateFormat("MM/dd/yyyy",Locale.US).parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    public static Date formatDatefromAPI(String dateString) {
        Date formattedDate=null;
        try {
            formattedDate =   new SimpleDateFormat("yyyyMMdd",Locale.US).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    public static String formatDate(Calendar dateString) {
        String formattedDate=null;
        formattedDate =   new SimpleDateFormat("MM/dd/yyyy",Locale.US).format(dateString.getTime());
        return formattedDate;
    }
}