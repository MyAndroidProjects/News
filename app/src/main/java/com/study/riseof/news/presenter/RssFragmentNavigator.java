package com.study.riseof.news.presenter;

import android.util.Log;

import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.ui.activity.Navigation;
import com.study.riseof.news.ui.activity.NavigationManager;


public class RssFragmentNavigator implements RssFragmentContract.Navigator {

    private static RssFragmentNavigator instance;
    private Navigation.Manager manager;

    private RssFragmentNavigator() {
        manager = NavigationManager.getManagerInstance();
        Log.d("myLog", " RssFragmentNavigator CONSTRUCTOR ");
    }

    public static RssFragmentContract.Navigator getInstance() {
        if (instance == null) {
            instance = new RssFragmentNavigator();
        }
        return instance;
    }

    @Override
    public void createNewsFromJsonFragment(MeduzaNews meduzaNews) {
        if (manager != null) {
            manager.createNewsFromJsonFragment(meduzaNews);
        }
    }

    @Override
    public void createWebViewFragment(String newsUrl) {
        if (manager != null) {
            manager.createWebViewFragment(newsUrl);
        }
    }

    @Override
    public void showShortToast(String message) {
        if (manager != null) {
            manager.showShortToast(message);
        }
    }
//todo RssFragmentNavigator

}
