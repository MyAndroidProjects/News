package com.study.riseof.news.presenter;

import android.util.Log;

public class WebViewFragmentPresenter implements WebViewFragmentContract.Presenter {

    private static WebViewFragmentPresenter instance;
    private WebViewFragmentContract.Navigator navigator;

    private WebViewFragmentPresenter(WebViewFragmentContract.Navigator navigator) {
        this.navigator = navigator;
        Log.d("myLog", " WebViewFragmentPresenter CONSTRUCTOR ");
    }

    public static WebViewFragmentContract.Presenter getInstance(WebViewFragmentContract.Navigator navigator) {
        if (instance == null) {
            instance = new WebViewFragmentPresenter(navigator);
        }
        return instance;
    }

    @Override
    public void receivedHttpError(String message) {
        navigator.showShortToast(message);
    }

    @Override
    public void receivedError(String message) {
        navigator.showShortToast(message);
    }
}
