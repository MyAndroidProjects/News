package com.study.riseof.news.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
        RssFragment.RssFragmentListener {

    @BindView(R.id.toolbar_news)
    Toolbar toolbar;

    @BindView(R.id.toolbar_news_title)
    TextView toolbarTitle;

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;

/*    @BindView(R.id.fragment_navigation_view)
    FrameLayout navigationView;*/

    @BindView(R.id.frame_news)
    FrameLayout frameNews;


    private NewsSourceNavigationViewFragment newsSourceNavigationViewFragment;
    private String tagNavigationViewFragment = "NavigationViewFragment";
    private RssFragment rssFragment;
    private WebViewFragment webViewFragment;
    private NewsFromJsonFragment newsFromJsonFragment;
    private MainActivityContract.MainActivityPresenter presenter;
    private ActionBar actionbar;

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
        Log.d("myLog", " onDestroy " + this.toString());
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

    private void addFragment(int fragmentView, Fragment fragment, String tag, boolean addTransactionToBackStack) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(fragmentView, fragment, tag);
        if (addTransactionToBackStack) {
            fragmentTransaction.addToBackStack(null);
            Log.d("myLog", "addFragment addToBackStack ");
        }
        fragmentTransaction.commit();
    }

    private void replaceFragment(int fragmentView, Fragment fragment, String tag, boolean addTransactionToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(fragmentView, fragment, tag);
        if (addTransactionToBackStack) {
            fragmentTransaction.addToBackStack(null);
            Log.d("myLog", "replaceFragment addToBackStack ");
        }
        fragmentTransaction.commit();
    }

    @Override
    public void createWebViewFragment(String newsUrl) {
        webViewFragment = new WebViewFragment();
        Bundle webViewFragmentArgs = new Bundle();
        webViewFragmentArgs.putString("newsUrl", newsUrl);
        webViewFragment.setArguments(webViewFragmentArgs);
    }

    @Override
    public void replaceRssFragmentWithWebViewFragment() {
        if (webViewFragment != null) {
            replaceFragment(R.id.frame_news, webViewFragment, null, true);
        }
    }

    @Override
    public void createNewsFromJsonFragment(String titleText, String descriptionText, String bodyText, String pubDateText, ArrayList<String> imageUrlList) {

        newsFromJsonFragment = new NewsFromJsonFragment();
        Bundle newsFromJsonFragmentArgs = new Bundle();
        newsFromJsonFragmentArgs.putString("titleText", titleText);
        newsFromJsonFragmentArgs.putString("descriptionText", descriptionText);
        newsFromJsonFragmentArgs.putString("bodyText", bodyText);
        newsFromJsonFragmentArgs.putString("pubDateText", pubDateText);
        newsFromJsonFragmentArgs.putStringArrayList("imageUrlList", imageUrlList);
        newsFromJsonFragment.setArguments(newsFromJsonFragmentArgs);
    }

    @Override
    public void replaceRssFragmentWithNewsFromJsonFragment() {
        if (newsFromJsonFragment != null) {
            replaceFragment(R.id.frame_news, newsFromJsonFragment, null, true);
        }
    }

    @Override
    public void createRssFragment(List<Item> rssList) {
        Log.d("myLog", "createRssFragment");
        if (rssFragment == null) {
            rssFragment = new RssFragment();
            addFragment(R.id.frame_news, rssFragment, null, true);
        } else {
            replaceFragment(R.id.frame_news, rssFragment, null, true);
        }
        rssFragment.setRssFragmentListener(this);

        updateRssListAndAdapter(rssList);
    }

    @Override
    public void updateRssListAndAdapter(List<Item> rssList) {
        Log.d("myLog", "updateRssListAndAdapter");
        if (rssList != null) {
            if (rssFragment != null) {
                rssFragment.setNewsListAndContext(rssList, this);
                rssFragment.setRecyclerAdapter();
            } else {
                createRssFragment(rssList);
                Log.d("myLog", "rssFragment==null");
            }
        }
    }

    @Override
    public void createNewsSourceNavigationViewFragment() {
        Log.d("myLog", "createNewsSourceNavigationViewFragment");

        if (newsSourceNavigationViewFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            newsSourceNavigationViewFragment = (NewsSourceNavigationViewFragment) fragmentManager.findFragmentByTag(tagNavigationViewFragment);
            Log.d("myLog", "newsSourceNavigationViewFragment == null");

            if (newsSourceNavigationViewFragment == null) {
                newsSourceNavigationViewFragment = new NewsSourceNavigationViewFragment();
                addFragment(R.id.fragment_navigation_view, newsSourceNavigationViewFragment, tagNavigationViewFragment, false);
            } else {
                Log.d("myLog", " findFragmentByTag ");
            }
        } else {
            Log.d("myLog", "newsSourceNavigationViewFragment !!= null");
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
        Log.d("myLog", "  public void openDrawer() ");
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
    public void onBackPressed() {
        Log.d("myLog", "onBackPressed");
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
            Log.d("myLog", "  newsSourceNavigationViewFragment.uncheckAllNavigationMenuItems() ");
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            newsSourceNavigationViewFragment = (NewsSourceNavigationViewFragment) fragmentManager.findFragmentByTag(tagNavigationViewFragment);
            if (newsSourceNavigationViewFragment != null) {
                newsSourceNavigationViewFragment.uncheckAllNavigationMenuItems();
                Log.d("myLog", " findFragmentByTag ");
            }
            Log.d("myLog", " uncheckAllNavigationMenuItems (newsSourceNavigationViewFragment == null) ");
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
    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rssNewsClick(int position, String newsUrl) {
        presenter.rssNewsClick(position, newsUrl);
    }
}