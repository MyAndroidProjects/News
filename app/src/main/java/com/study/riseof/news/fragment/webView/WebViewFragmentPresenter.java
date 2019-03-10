package com.study.riseof.news.fragment.webView;

import android.util.Log;

public class WebViewFragmentPresenter implements WebViewFragmentContract.Presenter {

    private static WebViewFragmentPresenter instance;
    private final WebViewFragmentContract.Navigator navigator;

    private WebViewFragmentPresenter() {
        navigator = WebViewFragmentNavigator.getInstance();
      //  Log.d("myLog", " WebViewFragmentPresenter CONSTRUCTOR ");
    }

    public static WebViewFragmentContract.Presenter getInstance() {
        if (instance == null) {
            instance = new WebViewFragmentPresenter();
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
