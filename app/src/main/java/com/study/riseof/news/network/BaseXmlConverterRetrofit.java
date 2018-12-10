package com.study.riseof.news.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public abstract class BaseXmlConverterRetrofit {
    protected Retrofit retrofit;
    protected String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public void buildRetrofit() {
        Log.d("myLog", "buildRetrofit");
        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

}
