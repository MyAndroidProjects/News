package com.study.riseof.news.presenter;

import android.view.View;

import com.study.riseof.news.ui.activity.MainActivity;

public class MainActivityPresenter implements MainActivityContract.MainActivityPresenter {

    private static MainActivityPresenter instance;
    private MainActivityContract.MainActivityView activityView;

    private MainActivityPresenter() {
    }

    public static MainActivityPresenter getInstance() {
        if (instance == null) {
            instance = new MainActivityPresenter();
        }
        return instance;
    }

    @Override
    public void attachView(MainActivity activity) {
        this.activityView = activity;
    }

    @Override
    public void viewIsAvailable() {

    }

    @Override
    public void deAttachView() {
        this.activityView = null;
    }

    @Override
    public void onMenuButtonUpdate() {

    }

    @Override
    public void onMenuButtonHome() {

    }

    @Override
    public void onActivityDestroy() {
        deAttachView();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
       // activityView.showShortToast("onDrawerSlide");
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        activityView.showShortToast("onDrawerOpened");
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        activityView.showShortToast("onDrawerClosed");
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        activityView.showShortToast("onDrawerStateChanged");
    }


    @Override
    public void onMenuItemCloseMainDrawer() {
        activityView.showShortToast("onMenuItemCloseMainDrawer");
    }

    @Override
    public void onMenuItemYandex() {
        activityView.showShortToast("onMenuItemYandex");
    }

    @Override
    public void onMenuItemMeduza() {
        activityView.showShortToast("onMenuItemMeduza");
    }

    @Override
    public void onMenuItemNgs() {
        activityView.showShortToast("onMenuItemNgs");
    }

    @Override
    public void onMenuItemLenta() {
        activityView.showShortToast("onMenuItemLenta");
    }

    @Override
    public void onMenuItemRia() {
        activityView.showShortToast("onMenuItemRia");
    }
}
