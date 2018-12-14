package com.study.riseof.news.presenter;

import android.view.View;

import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.ui.activity.MainActivity;

import java.util.List;

public interface MainActivityContract {
    interface MainActivityView {
        void showShortToast(String message);

        void openDrawer();

        void closeDrawer();

        void createRssFragment();

        void createWebViewFragment(String newsUrl);

        void replaceRssFragmentWithWebViewFragment();

        void setActionBarTitle(int textId);

        void setActionBarColor(int color);

        void setStatusBarColor(int color);

        void createNewsSourceNavigationViewFragment();

        void setRssList(List<Item> rssList);
    }

    interface MainActivityPresenter {
        void attachView(MainActivity activity);

        void activityOnStart();

        void deAttachView();

        void onMenuButtonHome();

        void onActivityDestroy();

        void onDrawerSlide(View drawerView, float slideOffset);

        void onDrawerOpened(View drawerView);

        void onDrawerClosed(View drawerView);

        void onDrawerStateChanged(int newState);

        void onMenuItemCloseMainDrawer();

        void onNavigationMenuItemYandex();

        void onNavigationMenuItemMeduza();

        void onNavigationMenuItemNgs();

        void onNavigationMenuItemLenta();

        void onNavigationMenuItemRia();

        void onNavigationMenuAnyItem();

        void rssNewsClick(int position,String newsUrl);
    }

    interface MainActivityDataLoader {
    }

}
