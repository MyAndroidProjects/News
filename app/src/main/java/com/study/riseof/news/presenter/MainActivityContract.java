package com.study.riseof.news.presenter;


import com.study.riseof.news.NewsSource;
import com.study.riseof.news.ui.activity.MainActivity;

public interface MainActivityContract {

    interface View  extends BaseContract.View {

        void openDrawer();

        void setCurrentNewsSource(NewsSource currentNewsSource);

        void setNewsSourceAttributes(NewsSource currentNewsSource);
    }

    interface Presenter  extends BaseContract.Presenter  {
        void onMenuButtonHome();

        void onBackButtonPressed();

        void activityFirstLaunch();

        void backStackIsEmpty();

        void setActivityView(MainActivityContract.View activityView);
    }

    interface Navigator  extends BaseContract.Navigator {

        void closeDrawer();

        void openDrawer();
        //todo посмотреть создание навигатор view при нажатии кнопки
        void createNavigatorViewFragment();

        void onBackButtonPressed();
    }

}
