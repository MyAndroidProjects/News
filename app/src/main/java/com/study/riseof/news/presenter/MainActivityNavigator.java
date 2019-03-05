package com.study.riseof.news.presenter;

import android.util.Log;

import com.study.riseof.news.ui.activity.Navigation;
import com.study.riseof.news.ui.activity.NavigationManager;

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
    public void closeDrawer() {
        if(manager!=null){
            manager.closeDrawer();
        }
    }

    @Override
    public void openDrawer() {
        if(manager!=null){
            manager.openDrawer();
        }
    }

    @Override
    public void createNavigatorViewFragment() {
        if(manager!=null) {
            manager.createNavigatorViewFragment();
        }
    }

    @Override
    public void onBackButtonPressed() {
        if(manager!=null) {
            manager.onBackButtonPressed();
        }
    }
}
