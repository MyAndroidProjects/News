package com.study.riseof.news.ui.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.study.riseof.news.R;
import com.study.riseof.news.model.MeduzaCutNews;
import com.study.riseof.news.presenter.MainActivityContract;
import com.study.riseof.news.presenter.MainActivityPresenter;
import com.study.riseof.news.ui.adapter.RssRecyclerViewAdapter;
import com.study.riseof.news.ui.fragment.NewsSourceNavigationViewFragment;
import com.study.riseof.news.ui.fragment.RssFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        MainActivityContract.MainActivityView,
        NewsSourceNavigationViewFragment.NavigationViewListener,
        RssFragment.RssFragmentListener {

    @BindView(R.id.toolbar_news)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;

    @BindView(R.id.fragment_navigation_view)
    FrameLayout navigationView;

    @BindView(R.id.frame_news)
    FrameLayout frameNews;


    private NewsSourceNavigationViewFragment newsSourceNavigationViewFragment;
    private RssFragment rssFragment;
    private MainActivityContract.MainActivityPresenter presenter;
    private List<MeduzaCutNews> rssList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(R.color.status_bar_default_background);
        }

        setPresenter();
        setDrawerListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.activityOnStart();
        setActionBarColor(R.color.action_bar_default_background);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onActivityDestroy();
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
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
        }
    }

    private void setPresenter() {
        presenter = MainActivityPresenter.getInstance();
        presenter.attachView(this);
    }

    private void setActionBarColor(int color) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(color)));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }

    private void addFragment(Fragment fragment, int fragmentView) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(fragmentView, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setRssList(List<MeduzaCutNews> rssList) {
        this.rssList = rssList;
    }

    @Override
    public void createRssFragment() {
        if (rssFragment == null) {
            rssFragment = new RssFragment();
            addFragment(rssFragment, R.id.frame_news);
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
            addFragment(newsSourceNavigationViewFragment, R.id.fragment_navigation_view);
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
}


