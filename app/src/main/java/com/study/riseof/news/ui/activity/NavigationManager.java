package com.study.riseof.news.ui.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Item;

import java.util.ArrayList;

public class NavigationManager implements Navigation.Manager, Navigation.SetActivities {

    private static NavigationManager instance;
    private Navigation.MainActivity mainActivity;
    private Navigation.SecondActivity secondActivity;

    private NavigationManager() {
        Log.d("myLog", " NavigationManager CONSTRUCTOR ");
    }

    public static Navigation.SetActivities getSetActivitiesInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public static Navigation.Manager getManagerInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void setMainActivityToNavigationManager(Navigation.MainActivity mainActivity) {
        if (mainActivity != null) {
            Log.d("myLog", " setMainActivityToNavigationManager " + mainActivity.toString());
        }

        this.mainActivity = mainActivity;
    }


    @Override
    public void setSecondActivityToNavigationManager(Navigation.SecondActivity secondActivity) {
        // for testing
        this.secondActivity = secondActivity;
    }

    @Override
    public void showShortToast(String message) {
        if (mainActivity != null) {
            mainActivity.showShortToast(message);
        }

    }

    @Override
    public void closeDrawer() {
        mainActivity.closeDrawer();
    }

    @Override
    public void openDrawer() {
        mainActivity.openDrawer();
        Log.d("myLog", " NavigationManager openDrawer(); ");
    }

    @Override
    public void cleanBackStack() {
        mainActivity.cleanBackStack();
    }

    @Override
    public void setNewsSourceAttributes(NewsSource currentNewsSource) {
        mainActivity.setNewsSourceAttributes(currentNewsSource);
    }

    @Override
    public void createNavigatorViewFragment() {
        mainActivity.createNavigatorViewFragment();
    }

    @Override
    public void createRssFragment(ArrayList<Item> rssList, int sourceNameId) {
        mainActivity.createRssFragment(rssList, sourceNameId);
    }

    @Override
    public void createNewsFromJsonFragment(MeduzaNews meduzaNews) {
        mainActivity.createNewsFromJsonFragment(meduzaNews);
    }

    @Override
    public void createWebViewFragment(String newsUrl) {
        mainActivity.createWebViewFragment(newsUrl);
    }

    @Override
    public void onBackButtonPressed() {
        mainActivity.onBackButtonPressed();
    }

    @Override
    public void setCurrentNewsSource(NewsSource currentNewsSource) {
        mainActivity.setCurrentNewsSource(currentNewsSource);
    }
}
