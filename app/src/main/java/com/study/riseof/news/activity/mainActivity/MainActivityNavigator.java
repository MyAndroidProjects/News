package com.study.riseof.news.activity.mainActivity;

import android.util.Log;

import com.study.riseof.news.Navigation;
import com.study.riseof.news.NavigationManager;

public class MainActivityNavigator implements MainActivityContract.Navigator {

    private static MainActivityNavigator instance;
    private Navigation.Manager manager;

    private MainActivityNavigator() {
        manager = NavigationManager.getManagerInstance();
        Log.d("myLog", " MainActivityNavigator CONSTRUCTOR ");
    }

    public static MainActivityContract.Navigator getInstance() {
        if (instance == null) {
            instance = new MainActivityNavigator();
        }
        return instance;
    }


    @Override
    public void showShortToast(String message) {
        manager.showShortToast(message);
    }

    @Override
    public void createNavigatorViewFragment() {
        if(manager!=null) {
            manager.createNavigatorViewFragment();
        }
    }

}
