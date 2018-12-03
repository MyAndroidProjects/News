package com.study.riseof.news.presenter;

import android.view.View;

import com.study.riseof.news.ui.activity.MainActivity;

public interface MainActivityContract {
    interface MainActivityView {
        void showShortToast(String message);
    }

    interface MainActivityPresenter {
        void attachView(MainActivity activity);

        void viewIsAvailable();

        void deAttachView();

        void onMenuButtonUpdate();

        void onMenuButtonHome();

        void onActivityDestroy();

        void onDrawerSlide(View drawerView, float slideOffset);

        void onDrawerOpened(View drawerView);

        void onDrawerClosed(View drawerView);

        void onDrawerStateChanged(int newState);

        void onMenuItemCloseMainDrawer();

        void onMenuItemYandex();

        void onMenuItemMeduza();

        void onMenuItemNgs();

        void onMenuItemLenta();

        void onMenuItemRia();
    }

    interface MainActivityDataLoader {
    }

}
