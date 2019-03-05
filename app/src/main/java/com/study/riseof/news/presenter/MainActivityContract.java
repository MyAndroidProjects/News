package com.study.riseof.news.presenter;


public interface MainActivityContract {

    interface View  extends BaseContract.View {

    }

    interface Presenter  extends BaseContract.Presenter  {
        void onMenuButtonHome();

        void onBackButtonPressed();

        void activityFirstLaunch();
    }

    interface Navigator  extends BaseContract.Navigator {

        void closeDrawer();

        void openDrawer();
        //todo посмотреть создание навигатор view при нажатии кнопки
        void createNavigatorViewFragment();

        void onBackButtonPressed();
    }

}
