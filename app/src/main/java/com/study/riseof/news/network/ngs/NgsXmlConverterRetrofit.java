package com.study.riseof.news.network.ngs;

import com.study.riseof.news.network.BaseXmlConverterRetrofit;
import com.study.riseof.news.network.RetrofitApi;

public class NgsXmlConverterRetrofit extends BaseXmlConverterRetrofit {
    protected RetrofitApi.Ngs retrofitApi;

    private static NgsXmlConverterRetrofit instance;

    private NgsXmlConverterRetrofit() {
    }

    public static NgsXmlConverterRetrofit getInstance() {
        if (instance == null) {
            instance = new NgsXmlConverterRetrofit();
        }
        return instance;
    }


    @Override
    public void buildRetrofit() {
        super.buildRetrofit();
        retrofitApi = retrofit.create(RetrofitApi.Ngs.class);
    }

    public RetrofitApi.Ngs getRetrofitApi() {
        return retrofitApi;
    }

}
