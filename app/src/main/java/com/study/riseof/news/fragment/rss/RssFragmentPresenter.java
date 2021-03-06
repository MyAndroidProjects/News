package com.study.riseof.news.fragment.rss;

import android.util.Log;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.network.JsonConverterRetrofit;
import com.study.riseof.news.network.RetrofitApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RssFragmentPresenter implements RssFragmentContract.Presenter {

    @SuppressWarnings("FieldCanBeLocal")
    private final String EMPTY_STRING = "";
    private static RssFragmentPresenter instance;
    private final RssFragmentContract.Navigator navigator;

    private RssFragmentPresenter() {
        navigator = RssFragmentNavigator.getInstance();
        // Log.d("myLog", " RssFragmentPresenter CONSTRUCTOR ");
    }

    public static RssFragmentContract.Presenter getInstance() {
        if (instance == null) {
            instance = new RssFragmentPresenter();
        }
        return instance;
    }

    @Override
    public void rssNewsSelected(int position, String newsUrl, int sourceNameId) {
        if (sourceNameId == NewsSource.MEDUZA.getNameId()) {
            getNewsFromJsonAndSetToView(newsUrl, JsonConverterRetrofit.MEDUZA.getRetrofitApi());
        } else {
            if (navigator != null) {
                navigator.createWebViewFragment(newsUrl);
            }
        }
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
                //        Log.d("myLog", "onSuccess " + meduzaNews.getRoot().getTitle());
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
