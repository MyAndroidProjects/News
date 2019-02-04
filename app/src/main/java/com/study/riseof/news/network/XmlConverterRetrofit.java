package com.study.riseof.news.network;

import com.study.riseof.news.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public enum XmlConverterRetrofit {
    NGS(BuildConfig.URL_RSS_NGS),
    MEDUZA(BuildConfig.URL_RSS_MEDUZA),
    YANDEX(BuildConfig.URL_RSS_YANDEX),
    LENTA(BuildConfig.URL_RSS_LENTA),
    RBC(BuildConfig.URL_RSS_RBC);

    private final String baseUrl;
    private RetrofitApi.Xml retrofitApiXml;

    XmlConverterRetrofit(String baseUrl) {
        this.baseUrl = baseUrl;
        buildRetrofit();
    }

    private void buildRetrofit() {
       // Log.d("myLog", "buildRetrofit " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        retrofitApiXml = retrofit.create(RetrofitApi.Xml.class);
    }

    public RetrofitApi.Xml getRetrofitApi() {
        return retrofitApiXml;
    }
}
