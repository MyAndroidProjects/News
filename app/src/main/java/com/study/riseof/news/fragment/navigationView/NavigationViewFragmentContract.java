package com.study.riseof.news.fragment.navigationView;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.BaseContract;

import java.util.ArrayList;

public interface NavigationViewFragmentContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {
        void onMenuItemCloseMainDrawer();

        void onNavigationMenuItemYandex();

        void onNavigationMenuItemMeduza();

        void onNavigationMenuItemNgs();

        void onNavigationMenuItemLenta();

        void onNavigationMenuItemRbc();

        void onNavigationMenuSelectAnyItem();

        void setView(NavigationViewFragmentContract.View view);
    }

    interface Navigator extends BaseContract.Navigator {

        void closeDrawer();

        void cleanBackStack();

        void setNewsSourceAttributesInActivityView(NewsSource currentNewsSource);

        void createRssFragment(ArrayList<Item> rssList, int sourceNameId);

        void setCurrentNewsSource(NewsSource currentNewsSource);

    }
}
