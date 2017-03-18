package com.codepath.nytimes.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Saranu on 3/15/17.
 */

public class NYTArticleHeadLine implements Parcelable{


    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("kicker")
    @Expose
    private String kicker;

    public NYTArticleHeadLine(Parcel in) {

    }

    public String getMain() {
        return StringEscapeUtils.unescapeHtml3(main);
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<NYTArticleHeadLine> CREATOR
            = new Parcelable.Creator<NYTArticleHeadLine>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public NYTArticleHeadLine createFromParcel(Parcel in) {
            return new NYTArticleHeadLine(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public NYTArticleHeadLine[] newArray(int size) {
            return new NYTArticleHeadLine[size];
        }
    };

    }
