package com.study.riseof.news.presenter;

import android.app.Activity;

import com.study.riseof.news.model.meduza.MeduzaNews;

public interface RssFragmentContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter{

        void rssNewsClick(int position, String newsUrl, int sourceNameId);

    }

    interface Navigator extends BaseContract.Navigator {
        void createNewsFromJsonFragment(MeduzaNews meduzaNews);

        void createWebViewFragment(String newsUrl);

    }
}
