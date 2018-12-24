package com.study.riseof.news.network;

import com.study.riseof.news.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum JsonConverterRetrofit {
    MEDUZA(BuildConfig.BASE_JSON_URL_MEDUZA, BuildConfig.MAIN_URL_MEDUZA);


    private String baseUrl;



    private String mainUrl;
    private RetrofitApi.Json retrofitApiJson;

    JsonConverterRetrofit(String baseUrl, String mainUrl) {
        this.baseUrl = baseUrl;
        this.mainUrl = mainUrl;
        buildRetrofit();
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApiJson = retrofit.create(RetrofitApi.Json.class);
    }
    public String getMainUrl() {
        return mainUrl;
    }
    public RetrofitApi.Json getRetrofitApi() {
        return retrofitApiJson;
    }
}
