package com.codepath.nytimes.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Saranu on 3/15/17.
 */

public class NYTArticleKeyword implements Parcelable {

    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("is_major")
    @Expose
    private String isMajor;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;

    protected NYTArticleKeyword(Parcel in) {
        rank = in.readString();
        isMajor = in.readString();
        name = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rank);
        dest.writeString(isMajor);
        dest.writeString(name);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NYTArticleKeyword> CREATOR = new Creator<NYTArticleKeyword>() {
        @Override
        public NYTArticleKeyword createFromParcel(Parcel in) {
            return new NYTArticleKeyword(in);
        }

        @Override
        public NYTArticleKeyword[] newArray(int size) {
            return new NYTArticleKeyword[size];
        }
    };

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(String isMajor) {
        this.isMajor = isMajor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

