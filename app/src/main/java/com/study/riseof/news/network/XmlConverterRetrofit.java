package com.study.riseof.news.network;

import com.study.riseof.news.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public enum XmlConverterRetrofit {
    NGS(BuildConfig.URL_RSS_NGS),
    MEDUZA(BuildConfig.URL_RSS_MEDUZA),
    WASHINGTONPOST(BuildConfig.URL_RSS_WASHINGTONPOST),
    LENTA(BuildConfig.URL_RSS_LENTA),
    RT(BuildConfig.URL_RSS_RT);

    private final String baseUrl;
    private RetrofitApi.Xml retrofitApiXml;

    XmlConverterRetrofit(String baseUrl) {
        this.baseUrl = baseUrl;
        buildRetrofit();
    }

    private void buildRetrofit() {
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
