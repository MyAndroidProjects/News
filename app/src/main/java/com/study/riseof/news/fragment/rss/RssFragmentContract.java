package com.study.riseof.news.fragment.rss;

import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.BaseContract;

public interface RssFragmentContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {

        void rssNewsSelected(int position, String newsUrl, int sourceNameId);

    }

    interface Navigator extends BaseContract.Navigator {
        void createNewsFromJsonFragment(MeduzaNews meduzaNews);

        void createWebViewFragment(String newsUrl);
    }
}
