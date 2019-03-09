package com.study.riseof.news.fragment.webView;

import com.study.riseof.news.BaseContract;

public interface WebViewFragmentContract extends BaseContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {

        void receivedHttpError(String message);

        void receivedError(String message);
    }

    interface Navigator extends BaseContract.Navigator {

    }
}
