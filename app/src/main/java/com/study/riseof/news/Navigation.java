package com.study.riseof.news;

import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Item;

import java.util.ArrayList;

public interface Navigation {
    interface SetActivities {
        void setMainActivityToNavigationManager(Navigation.MainActivity mainActivity);
        void setSecondActivityToNavigationManager(Navigation.SecondActivity secondActivity); //for test
    }

    interface Manager {

        void showShortToast(String message);

        void closeDrawer();

        void openDrawer(); // need for manager (the same method in view interface)

        void cleanBackStack();

        void setNewsSourceAttributes(NewsSource currentNewsSource);

        void createNavigatorViewFragment();

        void createRssFragment(ArrayList<Item> rssList, int sourceNameId);

        void createNewsFromJsonFragment(MeduzaNews meduzaNews);

        void createWebViewFragment(String newsUrl);

        void setCurrentNewsSource(NewsSource currentNewsSource);
    }

    interface MainActivity {
        void showShortToast(String message);

        void closeDrawer();

        void openDrawer();

        void cleanBackStack();

        void setNewsSourceAttributes(NewsSource currentNewsSource);

        void createNavigatorViewFragment();

        void createRssFragment(ArrayList<Item> rssList, int sourceNameId);

        void createNewsFromJsonFragment(MeduzaNews meduzaNews);

        void createWebViewFragment(String newsUrl);

        void setCurrentNewsSource(NewsSource currentNewsSource);
    }

    // for testing
    interface SecondActivity {

    }
}
