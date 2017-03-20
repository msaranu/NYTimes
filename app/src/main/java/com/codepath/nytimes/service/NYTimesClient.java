package com.codepath.nytimes.service;

/**
 * Created by Saranu on 3/16/17.
 */

import com.codepath.nytimes.models.NYTArticleResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NYTimesClient {
    public static final String API_BASE_URL = "https://api.nytimes.com/";
    NYTimesClient.NYTimesService NYservice;

    public NYTimesClient.NYTimesService NYTimesClientFactory(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NYservice = retrofit.create(NYTimesClient.NYTimesService.class);

        return NYservice;

    }

    public static NYTimesClient getNewInstance() {
        return new NYTimesClient();
    }

    public interface NYTimesService
    {
            @GET("/svc/search/v2/articlesearch.json")
            Call<NYTArticleResponse> getArticlesFromServer(@Query("api-key") String api_key,
                                                           @Query("page") Integer page_number,
                                                           @Query("q") String search_string,
                                                           @Query("begin_date") String begin_date,
                                                           @Query("sort") String sort_order,
                                                           @Query("fq") String news_desk);

            @GET("/svc/search/v2/articlesearch.json")
            Call<NYTArticleResponse> getArticlesFromServer(@Query("api-key") String api_key);
    }


    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }


}