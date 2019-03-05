package com.study.riseof.news.presenter;

import android.util.Log;

import com.study.riseof.news.ui.activity.Navigation;
import com.study.riseof.news.ui.activity.NavigationManager;

public class WebViewFragmentNavigator implements WebViewFragmentContract.Navigator {

    private static WebViewFragmentNavigator instance;
    private Navigation.Manager manager;

    private WebViewFragmentNavigator() {
        manager = NavigationManager.getManagerInstance();
        Log.d("myLog", " WebViewFragmentNavigator CONSTRUCTOR ");
    }

    public static WebViewFragmentContract.Navigator getInstance() {
        if (instance == null) {
            instance = new WebViewFragmentNavigator();

        }
        return instance;
    }

    @Override
    public void showShortToast(String message) {
            manager.showShortToast(message);
    }

}
