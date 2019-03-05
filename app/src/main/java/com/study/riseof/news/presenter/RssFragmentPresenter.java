package com.study.riseof.news.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Channel;
import com.study.riseof.news.model.xml.Rss;
import com.study.riseof.news.network.JsonConverterRetrofit;
import com.study.riseof.news.network.RetrofitApi;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RssFragmentPresenter implements RssFragmentContract.Presenter {

    private final String EMPTY_STRING = "";
    private static RssFragmentPresenter instance;
    private RssFragmentContract.Navigator navigator;

    private RssFragmentPresenter(RssFragmentContract.Navigator navigator) {
        this.navigator = navigator;
        Log.d("myLog", " RssFragmentPresenter CONSTRUCTOR ");
    }

    public static RssFragmentContract.Presenter getInstance(RssFragmentContract.Navigator navigator) {
        if (instance == null) {
            instance = new RssFragmentPresenter(navigator);
        }
        return instance;
    }


    @Override
    public void rssNewsClick(int position, String newsUrl, int sourceNameId) {
        if (sourceNameId == NewsSource.MEDUZA.getNameId()) {
            getNewsFromJsonAndSetToView(newsUrl, JsonConverterRetrofit.MEDUZA.getRetrofitApi());
        } else {
            if (navigator != null) {
                navigator.createWebViewFragment(newsUrl);
            }
        }

/*        currentFragmentContent = MainActivityPresenter.FragmentContent.NOT_RSS;
        if (currentNewsSource == NewsSource.MEDUZA) {
            getNewsFromJsonAndSetToView(position, JsonConverterRetrofit.MEDUZA.getRetrofitApi());
        } else {
            if (navigator != null) {
                navigator.createWebViewFragment(newsUrl);
                navigator.replaceRssFragmentWithWebViewFragment();
            }
        }*/
    }

    private void getNewsFromJsonAndSetToView(String newsUrl, RetrofitApi.Json jsonApi) {
        String url = newsUrl.replace(JsonConverterRetrofit.MEDUZA.getMainUrl(), EMPTY_STRING);

        // retrofit с rxjava observer
        Observer<MeduzaNews> observer = new Observer<MeduzaNews>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(MeduzaNews meduzaNews) {
                Log.d("myLog", "onSuccess " + meduzaNews.getRoot().getTitle());
                navigator.createNewsFromJsonFragment(meduzaNews);
            }

            @Override
            public void onError(Throwable e) {
                navigator.showShortToast(e.toString());
                Log.d("myLog", "onError " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("myLog", "onComplete ");
            }
        };

        jsonApi.getObservableData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
