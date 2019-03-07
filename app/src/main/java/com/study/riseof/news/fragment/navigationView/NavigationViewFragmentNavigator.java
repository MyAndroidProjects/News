package com.study.riseof.news.fragment.navigationView;

import android.util.Log;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.Navigation;
import com.study.riseof.news.NavigationManager;

import java.util.ArrayList;


public class NavigationViewFragmentNavigator
        implements NavigationViewFragmentContract.Navigator {

    private static NavigationViewFragmentNavigator instance;
    private Navigation.Manager manager;


    private NavigationViewFragmentNavigator() {
        manager = NavigationManager.getManagerInstance();
        Log.d("myLog", " RssFragmentNavigator CONSTRUCTOR ");
    }

    public static NavigationViewFragmentContract.Navigator getInstance() {
        if (instance == null) {
            instance = new NavigationViewFragmentNavigator();
        }
        return instance;
    }

    @Override
    public void closeDrawer() {
        if (manager != null) {
            manager.closeDrawer();
        }
    }

    @Override
    public void cleanBackStack() {
        if (manager != null) {
            manager.cleanBackStack();
        }
    }

    @Override
    public void showShortToast(String message) {
        if (manager != null) {
            manager.showShortToast(message);
        }
    }

    @Override
    public void createRssFragment(ArrayList<Item> rssList, int sourceNameId) {
        if (manager != null) {
            manager.createRssFragment(rssList, sourceNameId);
            Log.d("myLog", " manager.createRssFragment ");
        }
    }

    @Override
    public void setCurrentNewsSource(NewsSource currentNewsSource) {
        manager.setCurrentNewsSource(currentNewsSource);
    }

    @Override
    public void setNewsSourceAttributesInActivityView(NewsSource currentNewsSource) {
        if (manager != null) {
            Log.d("myLog", " manager.setNewsSourceAttributes == " +currentNewsSource.toString());
            manager.setNewsSourceAttributes(currentNewsSource);
        }
        else {
            Log.d("myLog", " manager == null ");
        }
    }

}
