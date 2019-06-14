package com.seniorsteps.news.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static Retrofit retrofitInstance;

    static Retrofit getInstacne(){
        if(retrofitInstance==null){
            //build retrofit
             retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofitInstance;
    }

   public static AppServices getAPIs(){
        return getInstacne().create(AppServices.class);
    }

}
