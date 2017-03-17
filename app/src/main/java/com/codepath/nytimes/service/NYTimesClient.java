package com.codepath.nytimes.service;

/**
 * Created by Saranu on 3/16/17.
 */

import com.codepath.nytimes.models.NYTArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public class NYTimesClient {
    private final String API_KEY = "a5ac3eb802f44561b5fa0f398b07f65f";
    private final String API_BASE_URL = "https://api.nytimes.com/";

    public interface NYTimesService
    {
            @GET("svc/search/v2/articlesearch.json?begin_date=20160112&sort=oldest&fq=news_desk:(%22Politics%22)&api-key=a5ac3eb802f44561b5fa0f398b07f65f")
            Call<NYTArticleResponse> getArticlesFromServer();
    }


    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
}