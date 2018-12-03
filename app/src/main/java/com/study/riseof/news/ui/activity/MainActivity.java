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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.study.riseof.news.R;
import com.study.riseof.news.presenter.MainActivityContract;
import com.study.riseof.news.presenter.MainActivityPresenter;
import com.study.riseof.news.ui.fragment.NewsSourceNavigationViewFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainActivityContract.MainActivityView, NewsSourceNavigationViewFragment.MenuListener {

    @BindView(R.id.toolbar_news)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;

    @BindView(R.id.fragment_navigation_view)
    FrameLayout navigationView;

    private NewsSourceNavigationViewFragment newsSourceNavigationViewFragment;
    private MainActivityContract.MainActivityPresenter presenter;

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
        newsSourceNavigationViewFragment = new NewsSourceNavigationViewFragment();
        addFragment(newsSourceNavigationViewFragment, R.id.fragment_navigation_view);
        setPresenter();
        setDrawerListener();
        setFragmentsListeners();
//        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
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

    private void setFragmentsListeners() {
        if (newsSourceNavigationViewFragment != null) {
            newsSourceNavigationViewFragment.setMenuListener(this);
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
        fragmentTransaction.add(fragmentView, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemCloseMainDrawer() {
        presenter.onMenuItemCloseMainDrawer();
    }

    @Override
    public void onMenuItemYandex() {
        presenter.onMenuItemYandex();
    }

    @Override
    public void onMenuItemMeduza() {
        presenter.onMenuItemMeduza();
    }

    @Override
    public void onMenuItemNgs() {
        presenter.onMenuItemNgs();
    }

    @Override
    public void onMenuItemLenta() {
        presenter.onMenuItemLenta();
    }

    @Override
    public void onMenuItemRia() {
        presenter.onMenuItemRia();
    }

    @Override
    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


