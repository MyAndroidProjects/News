package com.study.riseof.news.presenter;

import android.util.Log;
import android.view.View;

import com.study.riseof.news.model.MeduzaCutNews;
import com.study.riseof.news.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter implements MainActivityContract.MainActivityPresenter {

    private static MainActivityPresenter instance;
    private MainActivityContract.MainActivityView activityView;
    List<MeduzaCutNews> rssList;

    private MainActivityPresenter() {
    }

    public static MainActivityPresenter getInstance() {
        if (instance == null) {
            instance = new MainActivityPresenter();
        }
        return instance;
    }

    private void setRssList(/*List<MeduzaCutNews> rssList*/) {
        // todo сдеалать setter из model.getRssList();
        List<MeduzaCutNews> rssListTest = new ArrayList<>();
        for (int i =0;i<20;i++){
            MeduzaCutNews meduzaCutNews = new MeduzaCutNews();
            rssListTest.add(meduzaCutNews);
            Log.d("myLog",meduzaCutNews.getTitle());
        }
        this.rssList = rssListTest;

    }

    @Override
    public void attachView(MainActivity activity) {
        this.activityView = activity;
    }

    @Override
    public void activityOnStart() {
        activityView.createNewsSourceNavigationViewFragment();
    }

    @Override
    public void deAttachView() {
        this.activityView = null;
    }


    @Override
    public void onMenuButtonHome() {
        Log.d("myLog", "onMenuButtonHome");
        activityView.openDrawer();
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
        activityView.closeDrawer();
    }

    @Override
    public void onNavigationMenuAnyItem() {
        activityView.closeDrawer();
    }

    @Override
    public void onNavigationMenuItemYandex() {
        activityView.createRssFragment();
    }

    @Override
    public void onNavigationMenuItemMeduza() {
        setRssList();
        activityView.setRssList(rssList);
        activityView.createRssFragment();
    }

    @Override
    public void onNavigationMenuItemNgs() {
        activityView.showShortToast("onNavigationMenuItemNgs");
    }

    @Override
    public void onNavigationMenuItemLenta() {
        activityView.showShortToast("onNavigationMenuItemLenta");
    }

    @Override
    public void onNavigationMenuItemRia() {
        activityView.showShortToast("onNavigationMenuItemRia");
    }
}
