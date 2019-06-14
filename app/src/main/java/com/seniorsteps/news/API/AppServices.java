package com.seniorsteps.news.API;

import com.seniorsteps.news.API.Responses.ArticlesResponse.ArticlesResponse;
import com.seniorsteps.news.API.Responses.SourcesResponse.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppServices {

    @GET("sources")
    Call<SourcesResponse> getNewsSources(@Query("language") String language,@Query("apiKey") String apiKey);
    @GET("everything")
    Call<ArticlesResponse> getArticles(@Query("language") String language, @Query("apiKey") String apiKey
    , @Query("sources") String sources);
}
