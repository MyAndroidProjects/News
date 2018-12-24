package com.study.riseof.news.network;


import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Rss;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {
    interface Xml {
        @GET(".")
        Call<Rss> getRss();
    }

    interface Json {
        @GET()
        Call<MeduzaNews> getData(@Url String url);
    }

}
