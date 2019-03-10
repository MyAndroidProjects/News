package com.study.riseof.news;


public interface BaseContract {
    interface View {
    }

    interface Presenter {
    }

    interface Navigator {
        void showShortToast(String message);
    }
}
