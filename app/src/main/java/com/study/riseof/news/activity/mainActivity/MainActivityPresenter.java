package com.study.riseof.news.activity.mainActivity;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;


public class MainActivityPresenter implements MainActivityContract.Presenter {

    private static MainActivityPresenter instance;
    private final MainActivityContract.Navigator navigator;
    private MainActivityContract.View activityView;
    @SuppressWarnings("FieldCanBeLocal")
    private final int delayOpenNavigatorView = 20;

    private MainActivityPresenter() {
        navigator = MainActivityNavigator.getInstance();
        //  Log.d("myLog", " MainActivityPresenter CONSTRUCTOR ");
    }

    public static MainActivityContract.Presenter getInstance() {
        if (instance == null) {
            instance = new MainActivityPresenter();
        }
        return instance;
    }

    @Override
    public void setActivityView(MainActivityContract.View activityView) {
        this.activityView = activityView;
    }

    @Override
    public void activityFirstLaunch() {
        if (navigator != null) {
            navigator.createNavigatorViewFragment();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (activityView != null) {
                        activityView.openDrawer();
                    }
                }
            }, delayOpenNavigatorView);
        }
    }

    @Override
    public void menuButtonHomeSelected() {
        if (activityView != null) {
            activityView.openDrawer();
        }
    }

    @Override
    public void backButtonSelected() {
        if (activityView != null) {
            activityView.closeDrawer();
            activityView.callSuperOnBackPressed();
            if (isBackStackEmpty()) {
                activityView.callSuperOnBackPressed();
            }
        }
    }

    private boolean isBackStackEmpty() {
        FragmentManager fragmentManager = activityView.getCurrentFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        return (count == 0);
    }
}