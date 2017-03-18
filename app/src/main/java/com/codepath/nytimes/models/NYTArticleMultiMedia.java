package com.codepath.nytimes.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Saranu on 3/15/17.
 */

public class NYTArticleMultiMedia implements Parcelable {

    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("type")
    @Expose
    private String type;

    protected NYTArticleMultiMedia(Parcel in) {
        width = in.readInt();
        url = in.readString();
        height = in.readInt();
        subtype = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeString(url);
        dest.writeInt(height);
        dest.writeString(subtype);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NYTArticleMultiMedia> CREATOR = new Creator<NYTArticleMultiMedia>() {
        @Override
        public NYTArticleMultiMedia createFromParcel(Parcel in) {
            return new NYTArticleMultiMedia(in);
        }

        @Override
        public NYTArticleMultiMedia[] newArray(int size) {
            return new NYTArticleMultiMedia[size];
        }
    };

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        if(url !=null){
            return "http://www.nytimes.com/" + url.replace("\\",""); // TODO: Add the URL to properties file
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}