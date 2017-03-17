package com.codepath.nytimes.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saranu on 3/16/17.
 */

public class NYTArticleListResponse implements Parcelable{

    @SerializedName("docs")
    @Expose
    private List<NYTArticle> articles = null;

    public NYTArticleListResponse(){
      articles=new ArrayList<NYTArticle>();
    }

    protected NYTArticleListResponse(Parcel in) {
        articles = in.createTypedArrayList(NYTArticle.CREATOR);
    }

    public static final Creator<NYTArticleListResponse> CREATOR = new Creator<NYTArticleListResponse>() {
        @Override
        public NYTArticleListResponse createFromParcel(Parcel in) {
            return new NYTArticleListResponse(in);
        }

        @Override
        public NYTArticleListResponse[] newArray(int size) {
            return new NYTArticleListResponse[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(articles);
    }

    public List<NYTArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NYTArticle> articles) {
        this.articles = articles;
    }

}
