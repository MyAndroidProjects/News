package com.study.riseof.news.presenter;

import android.view.View;

import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public interface MainActivityContract {
    interface MainActivityView {
        void showShortToast(String message);

        void openDrawer();

        void closeDrawer();

        void callSuperOnBackPressed();

        void uncheckAllNavigationMenuItems();

        boolean webViewGoBack();

        void cleanBackStack();

        //      void createRssFragmentOld();

        void createWebViewFragment(String newsUrl);

        void replaceRssFragmentWithWebViewFragment();

        void createNewsFromJsonFragment(String titleText, String descriptionText, String bodyText, String pubDateText, ArrayList<String> imageUrlList);

        void replaceRssFragmentWithNewsFromJsonFragment();

        void setActionBarTitle(int textId);

        void setActionBarColor(int color);

        void setStatusBarColor(int color);

        void createNewsSourceNavigationViewFragment();


        void setRssList(List<Item> rssList);

        void createRssFragment(List<Item> rssList);

        void updateRssListAndAdapter(List<Item> rssList);


        boolean isNavigationViewFragmentExist();
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

        void onBackButtonPressed();

        void onMenuItemCloseMainDrawer();

        void onNavigationMenuItemYandex();

        void onNavigationMenuItemMeduza();

        void onNavigationMenuItemNgs();

        void onNavigationMenuItemLenta();

        void onNavigationMenuItemRbc();

        void onNavigationMenuSelectAnyItem();
        //    void onNavigationMenuStartOfSelectAnyItem();

        void rssNewsClick(int position, String newsUrl);

        List<Item> getRssList();


    }

    interface MainActivityDataLoader {
    }

}
