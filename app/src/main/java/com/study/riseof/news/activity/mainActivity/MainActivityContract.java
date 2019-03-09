package com.study.riseof.news.activity.mainActivity;


import android.support.v4.app.FragmentManager;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.BaseContract;

public interface MainActivityContract {

    interface View extends BaseContract.View {

        void openDrawer();

        void closeDrawer();

        void setNewsSourceAttributes(NewsSource currentNewsSource);

        void callSuperOnBackPressed();

        FragmentManager getCurrentFragmentManager();
    }

    interface Presenter extends BaseContract.Presenter {
        void menuButtonHomeSelected();

        void backButtonSelected();

        void activityFirstLaunch();

        void setActivityView(MainActivityContract.View activityView);
    }

    interface Navigator extends BaseContract.Navigator {

        void createNavigatorViewFragment();
    }
}
