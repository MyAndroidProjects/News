package com.study.riseof.news.fragment.webView;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.study.riseof.news.R;
import com.study.riseof.news.fragment.BaseFragment;

import butterknife.BindView;

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.web_loading_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.web_view)
    public WebView webView;

    private String newsUrl;
    @SuppressWarnings("FieldCanBeLocal")
    private final String newsUrlArgName = "newsUrl";

    private WebViewFragmentContract.Presenter presenter;

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
                if (progressBar != null) {
                    if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    progressBar.setProgress(progress);
                    if (progress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                return false;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                return false;
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (presenter != null) {
                    presenter.receivedError(description);
                }
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (presenter != null) {
                    presenter.receivedError(error.toString());
                }
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                if (presenter != null) {
                    presenter.receivedHttpError(errorResponse.toString());
                }
            }
        });

        webView.loadUrl(newsUrl);
        return view;
    }

    private void getBundleArgs() {
        if (this.getArguments() != null) {
            Bundle args = getArguments();
            newsUrl = args.getString(newsUrlArgName, EMPTY_STRING);
        } else {
            newsUrl = EMPTY_STRING;
        }
    }


    @Override
    public void setPresenter() {
        presenter = WebViewFragmentPresenter.getInstance();
    }

    @Override
    public void nullifyPresenter() {
        presenter = null;
    }
}
