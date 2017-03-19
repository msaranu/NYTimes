package com.codepath.nytimes.utils;

/**
 * Created by Saranu on 3/17/17.
 */

public class DateUtil{

    public static String formatDatetoString(String dateString) {
        String[] arrayDate = dateString.split("/");
        if(arrayDate[0].length() != 2){
            arrayDate[0] = '0'+ arrayDate[0];
        }
        if(arrayDate[1].length() != 2){
            arrayDate[1] = '0'+ arrayDate[1];
        }
        return arrayDate[2]+arrayDate[0]+arrayDate[1];
    }

    public static String formatStringtoDate(String dateString) {
        return dateString.substring(5,6)+"/" + dateString.substring(7,8)+"/"+dateString.substring(0,4);
    }



}