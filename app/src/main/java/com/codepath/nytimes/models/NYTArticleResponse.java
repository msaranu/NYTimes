package com.codepath.nytimes.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Saranu on 3/16/17.
 */

public class NYTArticleResponse implements Parcelable {

    @SerializedName("response")
    @Expose
    private NYTArticleListResponse response;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;

    protected NYTArticleResponse(Parcel in) {
        response = in.readParcelable(NYTArticleListResponse.class.getClassLoader());
        status = in.readString();
        copyright = in.readString();
    }

    public static final Creator<NYTArticleResponse> CREATOR = new Creator<NYTArticleResponse>() {
        @Override
        public NYTArticleResponse createFromParcel(Parcel in) {
            return new NYTArticleResponse(in);
        }

        @Override
        public NYTArticleResponse[] newArray(int size) {
            return new NYTArticleResponse[size];
        }
    };

    public NYTArticleListResponse getResponse() {
        return response;
    }

    public void setResponse(NYTArticleListResponse response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(response, flags);
        dest.writeString(status);
        dest.writeString(copyright);
    }
}
