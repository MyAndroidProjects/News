package com.study.riseof.news.activity.mainActivity;


import android.support.v4.app.FragmentManager;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.BaseContract;

public interface MainActivityContract {

    interface View  extends BaseContract.View {

        void openDrawer();

        void setCurrentNewsSource(NewsSource currentNewsSource);

        void setNewsSourceAttributes(NewsSource currentNewsSource);

        FragmentManager getCurrentFragmentManager();

        void closeDrawer();

        void callSuperOnBackPressed();
    }

    interface Presenter  extends BaseContract.Presenter  {
        void onMenuButtonHome();

        void onBackButtonPressed();

        void activityFirstLaunch();

        void setActivityView(MainActivityContract.View activityView);
    }

    interface Navigator  extends BaseContract.Navigator {

        void createNavigatorViewFragment();
    }
}
