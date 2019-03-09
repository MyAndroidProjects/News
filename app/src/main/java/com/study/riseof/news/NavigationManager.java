package com.study.riseof.news;

import android.util.Log;

import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Item;


import java.util.ArrayList;

public class NavigationManager implements Navigation.Manager, Navigation.SetActivities {

    private static NavigationManager instance;
    private Navigation.MainActivity mainActivity;
    // private Navigation.SecondActivity secondActivity;

    private NavigationManager() {
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
        this.mainActivity = mainActivity;
    }


    @Override
    public void setSecondActivityToNavigationManager(Navigation.SecondActivity secondActivity) {
        // for testing
        //  this.secondActivity = secondActivity;
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
        if (mainActivity != null) {
            mainActivity.openDrawer();
        }
    }

    @Override
    public void cleanBackStack() {
        if (mainActivity != null) {
            mainActivity.cleanBackStack();
        }
    }

    @Override
    public void setNewsSourceAttributes(NewsSource currentNewsSource) {
        if (mainActivity != null) {
            mainActivity.setNewsSourceAttributes(currentNewsSource);
        }
    }

    @Override
    public void createNavigatorViewFragment() {
        if (mainActivity != null) {
            mainActivity.createNavigatorViewFragment();
        }
    }

    @Override
    public void createRssFragment(ArrayList<Item> rssList, int sourceNameId) {
        if (mainActivity != null) {
            mainActivity.createRssFragment(rssList, sourceNameId);
        }
    }

    @Override
    public void createNewsFromJsonFragment(MeduzaNews meduzaNews) {
        if (mainActivity != null) {
            mainActivity.createNewsFromJsonFragment(meduzaNews);
        }
    }

    @Override
    public void createWebViewFragment(String newsUrl) {
        if (mainActivity != null) {
            mainActivity.createWebViewFragment(newsUrl);
        }
    }

    @Override
    public void setCurrentNewsSource(NewsSource currentNewsSource) {
        if (mainActivity != null) {
            mainActivity.setCurrentNewsSource(currentNewsSource);
        }
    }
}
