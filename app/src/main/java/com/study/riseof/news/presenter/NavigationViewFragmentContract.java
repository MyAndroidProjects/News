package com.study.riseof.news.presenter;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.model.xml.Item;

import java.util.ArrayList;
import java.util.List;

public interface NavigationViewFragmentContract {
    interface View extends BaseContract.View {

        void uncheckAllNavigationMenuItems();
    }

    interface Presenter extends BaseContract.Presenter {
        void onMenuItemCloseMainDrawer();

        void onNavigationMenuItemYandex();

        void onNavigationMenuItemMeduza();

        void onNavigationMenuItemNgs();

        void onNavigationMenuItemLenta();

        void onNavigationMenuItemRbc();

        void onNavigationMenuSelectAnyItem();

        void fragmentIsOnResume();
    }

    interface Navigator extends BaseContract.Navigator {

        void closeDrawer();

        void cleanBackStack();

        void setNewsSourceAttributesInActivityView(NewsSource currentNewsSource);

        void createRssFragment(ArrayList<Item> rssList, int sourceNameId);

        void openDrawer();

        void setCurrentNewsSource(NewsSource currentNewsSource);
    }
}
