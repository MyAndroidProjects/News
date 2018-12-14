package com.study.riseof.news.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
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
import com.study.riseof.news.ui.fragment.NewsSourceNavigationViewFragment;
import com.study.riseof.news.ui.fragment.RssFragment;
import com.study.riseof.news.ui.fragment.WebViewFragment;

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

    @BindView(R.id.fragment_navigation_view)
    FrameLayout navigationView;

    @BindView(R.id.frame_news)
    FrameLayout frameNews;


    private NewsSourceNavigationViewFragment newsSourceNavigationViewFragment;
    private RssFragment rssFragment;
    private WebViewFragment webViewFragment;
    private MainActivityContract.MainActivityPresenter presenter;
    private List<Item> rssList;
    ActionBar actionbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setPresenter();
        setDrawerListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.activityOnStart();
        setActionBarColor(R.color.default_action_bar);
        setStatusBarColor(R.color.default_status_bar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onActivityDestroy();
        // todo поместить в backstack
    }

    private void setDrawerListener() {
        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        if (drawerView != null) {
                            presenter.onDrawerSlide(drawerView, slideOffset);
                        }
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        if (drawerView != null) {
                            presenter.onDrawerOpened(drawerView);
                        }
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        if (drawerView != null) {
                            presenter.onDrawerClosed(drawerView);
                        }
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        presenter.onDrawerStateChanged(newState);
                    }
                }
        );
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

    private void addFragment(int fragmentView, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(fragmentView, fragment);
        fragmentTransaction.commit();
    }

    private void replaceFragment(int fragmentView, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
     //   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(fragmentView, fragment);
        fragmentTransaction.replace(fragmentView, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void createWebViewFragment(String newsUrl) {
        Log.d("myLog"," Activity newsUrl "+newsUrl+" HHH");
        webViewFragment = new WebViewFragment();
        Bundle webViewFragmentArgs = new Bundle();
        webViewFragmentArgs.putString("newsUrl", newsUrl);
        webViewFragment.setArguments(webViewFragmentArgs);
    }

    @Override
    public void replaceRssFragmentWithWebViewFragment() {
        if(webViewFragment!=null){
            replaceFragment(R.id.frame_news, webViewFragment);
        }

    }

    @Override
    public void setRssList(List<Item> rssList) {
        this.rssList = rssList;
    }

    @Override
    public void createRssFragment() {
        if (rssFragment == null) {
            rssFragment = new RssFragment();
            addFragment(R.id.frame_news, rssFragment);
            rssFragment.setNewsList(rssList);
            rssFragment.setRssFragmentListener(this);
        } else {
            rssFragment.setRecyclerAdapter(this);
        }
        rssFragment.setRssFragmentListener(this);
    }

    @Override
    public void createNewsSourceNavigationViewFragment() {
        if (newsSourceNavigationViewFragment == null) {
            newsSourceNavigationViewFragment = new NewsSourceNavigationViewFragment();
            addFragment(R.id.fragment_navigation_view, newsSourceNavigationViewFragment);
        }
        newsSourceNavigationViewFragment.setNavigationViewListener(this);
    }

    @Override
    public void openDrawer() {
        Log.d("myLog", "drawerLayout.openDrawer");
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
                Log.d("myLog", "android.R.id.home");
                presenter.onMenuButtonHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onNavigationMenuItemRia() {
        presenter.onNavigationMenuItemRia();
    }

    @Override
    public void onNavigationMenuAnyItem() {
        presenter.onNavigationMenuAnyItem();
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


