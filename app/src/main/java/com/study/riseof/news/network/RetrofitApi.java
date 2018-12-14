package com.study.riseof.news.network;


import com.study.riseof.news.model.xml.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {
    interface Xml{
        @GET(".")
        Call<Rss> getRss();

/*        @GET()
        Call<Rss> getRss(@Url String url);*/

    }
    interface Json{

    }

}
