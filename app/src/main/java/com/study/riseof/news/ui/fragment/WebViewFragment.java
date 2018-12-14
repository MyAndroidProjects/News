package com.study.riseof.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.riseof.news.R;

import butterknife.BindView;

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.web_loading_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.web_view)
    WebView webView;



    private String newsUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web_view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getBundleArgs();

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);
                if(progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }

        });

        webView.loadUrl(newsUrl);
        return view;
    }

    public void setWebView() {

    }

    protected void getBundleArgs() {
        if (this.getArguments() != null) {
            Bundle args = getArguments();
            newsUrl = args.getString("newsUrl", EMPTY_STRING);
        } else {
            newsUrl = EMPTY_STRING;
        }
        Log.d("myLog"," newsUrl "+newsUrl+" HHH");
    }
}
