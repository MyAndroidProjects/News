package com.study.riseof.news.presenter;



public interface BaseContract {
    interface View {
/*        void setNavigator();

        void setActivityToNavigator();

        void setPresenter();

        void nullifyPresenter();

        void nullifyNavigator();*/
    }

    interface Presenter {

      //  void viewOnStart();
      //  void viewOnStop();
    }

    interface Navigator {
        void showShortToast(String message);
    }

}
