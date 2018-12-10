package com.study.riseof.news.network;


import com.study.riseof.news.model.ngs.NgsRss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {
    interface Ngs{
        @GET(".")
        Call<NgsRss> getRss();
/*        @GET()
        Call<NgsRss> getRss(@Url String url);*/
    }
}
