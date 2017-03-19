package com.codepath.nytimes.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Saranu on 3/17/17.
 */

public class FilterSettings implements Parcelable {

    public String beginDate;
    public String sortOrder;
    public String ndArtsCheck;
    public String ndFashionCheck;
    public String ndSportsCheck;

    public FilterSettings(){

    }

    public FilterSettings(Parcel in) {
        beginDate = in.readString();
        sortOrder = in.readString();
        ndArtsCheck = in.readString();
        ndFashionCheck = in.readString();
        ndSportsCheck = in.readString();
    }

    public static final Creator<FilterSettings> CREATOR = new Creator<FilterSettings>() {
        @Override
        public FilterSettings createFromParcel(Parcel in) {
            return new FilterSettings(in);
        }

        @Override
        public FilterSettings[] newArray(int size) {
            return new FilterSettings[size];
        }
    };

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getNdArtsCheck() {
        return ndArtsCheck;
    }

    public void setNdArtsCheck(String ndArtsCheck) {
        this.ndArtsCheck = ndArtsCheck;
    }

    public String getNdFashionCheck() {
        return ndFashionCheck;
    }

    public void setNdFashionCheck(String ndFashionCheck) {
        this.ndFashionCheck = ndFashionCheck;
    }

    public String getNdSportsCheck() {
        return ndSportsCheck;
    }

    public void setNdSportsCheck(String ndSportsCheck) {
        this.ndSportsCheck = ndSportsCheck;
    }

    public String getNewsDeskQuery(){
        String ndStringQuery=null;
        if(getNdArtsCheck()!=null || getNdFashionCheck() !=null || getNdSportsCheck()!=null){
            ndStringQuery="news_desk:(";
            if(getNdArtsCheck() != null){ ndStringQuery += getNdArtsCheck() + ",";}
            if(getNdFashionCheck() != null){ ndStringQuery += getNdFashionCheck()+ ",";}
            if(getNdSportsCheck() != null){ ndStringQuery += getNdSportsCheck();}
            ndStringQuery=ndStringQuery+")";
        }
        return ndStringQuery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(beginDate);
        parcel.writeString(sortOrder);
        parcel.writeString(ndArtsCheck);
        parcel.writeString(ndFashionCheck);
        parcel.writeString(ndSportsCheck);
    }
}
