package com.study.riseof.news.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.riseof.news.R;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.presenter.MainActivityContract;
import com.study.riseof.news.presenter.MainActivityPresenter;
import com.study.riseof.news.ui.fragment.NewsFromJsonFragment;
import com.study.riseof.news.ui.fragment.NewsSourceNavigationViewFragment;
import com.study.riseof.news.ui.fragment.RssFragment;
import com.study.riseof.news.ui.fragment.WebViewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        MainActivityContract.MainActivityView,
        NewsSourceNavigationViewFragment.NavigationViewListener,
        RssFragment.RssFragmentListener,
        WebViewFragment.WebViewListener {

    @BindView(R.id.toolbar_news)
    Toolbar toolbar;

    @BindView(R.id.toolbar_news_title)
    TextView toolbarTitle;

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;

    @BindView(R.id.frame_news)
    FrameLayout frameNews;


    private NewsSourceNavigationViewFragment newsSourceNavigationViewFragment;
    private RssFragment rssFragment;
    private WebViewFragment webViewFragment;
    private NewsFromJsonFragment newsFromJsonFragment;
    private final String tagNavigationViewFragment = "navigationViewFragment";
    private final String tagRssFragment = "rssFragment";
    private final String tagWebViewFragment = "webViewFragment";
    private final String tagNewsFromJsonFragment = "newsFromJsonFragment";

    private MainActivityContract.MainActivityPresenter presenter;
    private ActionBar actionbar;

    private final String titleTextArgName = "titleText";
    private final String descriptionTextArgName = "descriptionText";
    private final String bodyTextArgName = "bodyText";
    private final String pubDateTextArgName = "pubDateText";
    private final String imageUrlListArgName = "imageUrlList";
    private final String newsUrlArgName = "newsUrl";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.activityOnStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onActivityDestroy();
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
        }
    }

    private void setPresenter() {
        presenter = MainActivityPresenter.getInstance();
        presenter.attachView(this);
    }

    @Override
    public void setActionBarTitle(int textId) {
        toolbarTitle.setText(textId);
    }

    @Override
    public void setActionBarColor(int color) {
        if (actionbar != null) {
            actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(color)));
        }
    }

    @Override
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    private void addOrReplaceFragment(int fragmentView, Fragment fragment, String tag, boolean addingFragment, boolean addTransactionToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (addingFragment) {
            fragmentTransaction.add(fragmentView, fragment, tag);
        } else {
            fragmentTransaction.replace(fragmentView, fragment, tag);
        }
        if (addTransactionToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

/*    private void addFragment(int fragmentView, Fragment fragment, String tag, boolean addTransactionToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(fragmentView, fragment, tag);
        if (addTransactionToBackStack) {
            fragmentTransaction.addToBackStack(null);
            Log.d("myLog", "addFragment addToBackStack ");
        }
        fragmentTransaction.commit();
    } */

/*
    private void replaceFragment(int fragmentView, Fragment fragment, String tag, boolean addTransactionToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(fragmentView, fragment, tag);
        if (addTransactionToBackStack) {
            fragmentTransaction.addToBackStack(null);
            Log.d("myLog", "replaceFragment addToBackStack ");
        }
        fragmentTransaction.commit();
    }*/

    @Override
    public void createWebViewFragment(String newsUrl) {
        webViewFragment = new WebViewFragment();
        Bundle webViewFragmentArgs = new Bundle();
        webViewFragmentArgs.putString(newsUrlArgName, newsUrl);
        webViewFragment.setArguments(webViewFragmentArgs);
        webViewFragment.setWebViewListener(this);
    }

    @Override
    public void replaceRssFragmentWithWebViewFragment() {
        if (webViewFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            webViewFragment = (WebViewFragment) fragmentManager.findFragmentByTag(tagWebViewFragment);
        }
        if (webViewFragment != null) {
            addOrReplaceFragment(R.id.frame_news, webViewFragment, tagWebViewFragment, false, true);
            //   replaceFragment(R.id.frame_news, webViewFragment, null, true);
        }
    }

    @Override
    public void createNewsFromJsonFragment(String titleText,
                                           String descriptionText,
                                           String bodyText,
                                           String pubDateText,
                                           ArrayList<String> imageUrlList) {
        newsFromJsonFragment = new NewsFromJsonFragment();
        Bundle newsFromJsonFragmentArgs = new Bundle();
        newsFromJsonFragmentArgs.putString(titleTextArgName, titleText);
        newsFromJsonFragmentArgs.putString(descriptionTextArgName, descriptionText);
        newsFromJsonFragmentArgs.putString(bodyTextArgName, bodyText);
        newsFromJsonFragmentArgs.putString(pubDateTextArgName, pubDateText);
        newsFromJsonFragmentArgs.putStringArrayList(imageUrlListArgName, imageUrlList);
        newsFromJsonFragment.setArguments(newsFromJsonFragmentArgs);
    }

    @Override
    public void replaceRssFragmentWithNewsFromJsonFragment() {
        if (newsFromJsonFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            newsFromJsonFragment = (NewsFromJsonFragment) fragmentManager.findFragmentByTag(tagNewsFromJsonFragment);
        }
        if (newsFromJsonFragment != null) {
            addOrReplaceFragment(R.id.frame_news, newsFromJsonFragment, tagNewsFromJsonFragment, false, true);
            //    replaceFragment(R.id.frame_news, newsFromJsonFragment, null, true);
        }
    }

    @Override
    public void createRssFragment(List<Item> rssList) {
        if (rssFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            rssFragment = (RssFragment) fragmentManager.findFragmentByTag(tagRssFragment);
        }
        if (rssFragment == null) {
            rssFragment = new RssFragment();
            //   addFragment(R.id.frame_news, rssFragment, null, true);
            addOrReplaceFragment(R.id.frame_news, rssFragment, tagRssFragment, true, true);
        } else {
            //  replaceFragment(R.id.frame_news, rssFragment, null, true);
            addOrReplaceFragment(R.id.frame_news, rssFragment, tagRssFragment, false, true);
        }
        rssFragment.setRssFragmentListener(this);
        updateRssListAndAdapter(rssList);
    }

    @Override
    public void updateRssListAndAdapter(List<Item> rssList) {
        if (rssList != null) {
            if (rssFragment != null) {
                rssFragment.setNewsListAndContext(rssList, this);
                rssFragment.setRecyclerAdapter();
            } else {
                createRssFragment(rssList);
            }
        }
    }

    @Override
    public void createNewsSourceNavigationViewFragment() {
        if (newsSourceNavigationViewFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            newsSourceNavigationViewFragment = (NewsSourceNavigationViewFragment) fragmentManager.findFragmentByTag(tagNavigationViewFragment);
            if (newsSourceNavigationViewFragment == null) {
                newsSourceNavigationViewFragment = new NewsSourceNavigationViewFragment();
                //   addFragment(R.id.fragment_navigation_view, newsSourceNavigationViewFragment, tagNavigationViewFragment, false);
                addOrReplaceFragment(R.id.fragment_navigation_view, newsSourceNavigationViewFragment, tagNavigationViewFragment, true, false);
            }
        }
        newsSourceNavigationViewFragment.setNavigationViewListener(this);
    }

    @Override
    public boolean isNavigationViewFragmentExist() {
        return newsSourceNavigationViewFragment != null;
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onMenuButtonHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void findAllFragmentsByTags() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (newsSourceNavigationViewFragment == null) {
            newsSourceNavigationViewFragment = (NewsSourceNavigationViewFragment) fragmentManager.findFragmentByTag(tagNavigationViewFragment);
        }
        if (rssFragment == null) {
            rssFragment = (RssFragment) fragmentManager.findFragmentByTag(tagRssFragment);
        }
        if (webViewFragment == null) {
            webViewFragment = (WebViewFragment) fragmentManager.findFragmentByTag(tagWebViewFragment);
        }
        if (newsFromJsonFragment == null) {
            newsFromJsonFragment = (NewsFromJsonFragment) fragmentManager.findFragmentByTag(tagNewsFromJsonFragment);
        }
    }

    @Override
    public void onBackPressed() {
        presenter.onBackButtonPressed();
    }

    @Override
    public void callSuperOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void uncheckAllNavigationMenuItems() {
        if (newsSourceNavigationViewFragment != null) {
            newsSourceNavigationViewFragment.uncheckAllNavigationMenuItems();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            newsSourceNavigationViewFragment = (NewsSourceNavigationViewFragment) fragmentManager.findFragmentByTag(tagNavigationViewFragment);
            if (newsSourceNavigationViewFragment != null) {
                newsSourceNavigationViewFragment.uncheckAllNavigationMenuItems();
            }
        }
    }

    @Override
    public void cleanBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public boolean webViewGoBack() {
        if (webViewFragment == null || webViewFragment.webView == null) {
            return false;
        }
        if (webViewFragment.webView.canGoBack()) {
            webViewFragment.webView.goBack();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMenuItemCloseMainDrawer() {
        presenter.onMenuItemCloseMainDrawer();
    }

    @Override
    public void onNavigationMenuItemYandex() {
        presenter.onNavigationMenuItemYandex();
    }

    @Override
    public void onNavigationMenuItemMeduza() {
        presenter.onNavigationMenuItemMeduza();
    }

    @Override
    public void onNavigationMenuItemNgs() {
        presenter.onNavigationMenuItemNgs();
    }

    @Override
    public void onNavigationMenuItemLenta() {
        presenter.onNavigationMenuItemLenta();
    }

    @Override
    public void onNavigationMenuItemRbc() {
        presenter.onNavigationMenuItemRbc();
    }

    @Override
    public void onNavigationMenuSelectAnyItem() {
        presenter.onNavigationMenuSelectAnyItem();
    }

    @Override
    public void rssNewsClick(int position, String newsUrl) {
        presenter.rssNewsClick(position, newsUrl);
    }

    @Override
    public void webViewFragmentMessage(String message) {
        presenter.webViewFragmentMessage(message);
    }

    @Override
    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}