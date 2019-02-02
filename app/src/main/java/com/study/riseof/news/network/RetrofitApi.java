package com.study.riseof.news.network;


import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Rss;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
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
        Call<MeduzaNews> getCallData(@Url String url);

        // с помощью rxjava
        // Single
        @GET()
        Single<MeduzaNews> getSingleData(@Url String url);

        // Maybe
        @GET()
        Maybe<MeduzaNews> getMaybeData(@Url String url);

        //   Observable
        @GET()
        Observable<MeduzaNews> getObservableData(@Url String url);
    }

}
