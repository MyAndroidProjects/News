package com.study.riseof.news.presenter;

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

        void createWebViewFragment(String newsUrl);

        void replaceRssFragmentWithWebViewFragment();

        void createNewsFromJsonFragment(String titleText,
                                        String descriptionText,
                                        String bodyText,
                                        String pubDateText,
                                        ArrayList<String> imageUrlList);

        void replaceRssFragmentWithNewsFromJsonFragment();

        void setActionBarTitle(int textId);

        void setActionBarColor(int color);

        void setStatusBarColor(int color);

        void createNewsSourceNavigationViewFragment();

        void createRssFragment(List<Item> rssList);

        void updateRssListAndAdapter(List<Item> rssList);

        boolean isNavigationViewFragmentExist();

        void findAllFragmentsByTags();
    }

    interface MainActivityPresenter {
        void attachView(MainActivity activity);

        void activityOnStart();

        void onMenuButtonHome();

        void onActivityDestroy();

        void onBackButtonPressed();

        void onMenuItemCloseMainDrawer();

        void onNavigationMenuItemYandex();

        void onNavigationMenuItemMeduza();

        void onNavigationMenuItemNgs();

        void onNavigationMenuItemLenta();

        void onNavigationMenuItemRbc();

        void onNavigationMenuSelectAnyItem();

        void rssNewsClick(int position, String newsUrl);

        void webViewFragmentMessage(String message);
    }
}
