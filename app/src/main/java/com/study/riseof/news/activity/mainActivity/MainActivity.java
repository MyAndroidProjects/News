package com.study.riseof.news.activity.mainActivity;

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

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.R;
import com.study.riseof.news.activity.BaseActivity;
import com.study.riseof.news.Navigation;
import com.study.riseof.news.NavigationManager;
import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.fragment.navigationView.NavigationViewFragment;
import com.study.riseof.news.fragment.newsFromJson.NewsFromJsonFragment;
import com.study.riseof.news.fragment.rss.RssFragment;
import com.study.riseof.news.fragment.webView.WebViewFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        MainActivityContract.View,
        Navigation.MainActivity {

    private MainActivityContract.Presenter presenter;
    private Navigation.SetActivities manager;

    private ActionBar actionbar;
    private NewsSource currentNewsSource = NewsSource.EMPTY;
    @SuppressWarnings("FieldCanBeLocal")
    private String currentNewsSourceName = NewsSource.EMPTY.name();
    private final String currentNewsSourceNameVarName = "currentNewsSourceName";
    private boolean isFirstLaunch = true;
    private final String isFirsLaunchVarName = "isFirstLaunch";

    @BindView(R.id.toolbar_news)
    Toolbar toolbar;

    @BindView(R.id.toolbar_news_title)
    TextView toolbarTitle;

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;

    @BindView(R.id.news_fragment_container)
    FrameLayout frameNews;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleArgs(savedInstanceState);
        manager = NavigationManager.getSetActivitiesInstance();
    }

    protected void getBundleArgs(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isFirstLaunch = savedInstanceState.getBoolean(isFirsLaunchVarName, true);
            currentNewsSourceName = savedInstanceState.getString(currentNewsSourceNameVarName, NewsSource.EMPTY.name());
            currentNewsSource = NewsSource.valueOf(currentNewsSourceName);
        } else {
            isFirstLaunch = true;
            currentNewsSource = NewsSource.EMPTY;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setNewsSourceAttributes(currentNewsSource);
        if (isFirstLaunch) {
            if (presenter != null) {
                presenter.activityFirstLaunch();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(isFirsLaunchVarName, false);
        outState.putString(currentNewsSourceNameVarName, currentNewsSource.name());
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

    @Override
    protected void setActionBarTitle(int textId) {
        toolbarTitle.setText(textId);
    }

    @Override
    protected void setActionBarColor(int color) {
        if (actionbar != null) {
            actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(color)));
        }
    }

    @Override
    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    @Override
    public void setPresenter() {
        presenter = MainActivityPresenter.getInstance();
        presenter.setActivityView(this);
    }

    @Override
    public void nullifyPresenter() {
        presenter.setActivityView(null);
        presenter = null;
    }

    @Override
    public void setActivityToManager() {
        if (manager != null) {
            manager.setMainActivityToNavigationManager(this);
        }
    }

    @Override
    public void nullifyActivityInManager() {
        if (manager != null) {
            manager.setMainActivityToNavigationManager(null);
        }
    }

    //------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.menuButtonHomeSelected();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        presenter.backButtonSelected();
    }

    @Override
    public void callSuperOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public FragmentManager getCurrentFragmentManager() {
        return getSupportFragmentManager();
    }

    //-----------------------------
//  Override from Manager
    @Override
    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
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
    public void setCurrentNewsSource(NewsSource currentNewsSource) {
        this.currentNewsSource = currentNewsSource;
    }

    @Override
    public void setNewsSourceAttributes(NewsSource currentNewsSource) {
        setActionBarTitle(currentNewsSource.getNameId());
        setActionBarColor(currentNewsSource.getActionBarColorId());
        setStatusBarColor(currentNewsSource.getStatusBarColorId());
    }

    @Override
    public void createNavigatorViewFragment() {
        NavigationViewFragment fragment = new NavigationViewFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(R.id.navigation_view_fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void createRssFragment(ArrayList<Item> rssList, int sourceNameId) {
        RssFragment fragment = new RssFragment();
        final String arrayListName = "rssList";
        final String sourceNameIdVarName = "sourceNameId";
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putParcelableArrayList(arrayListName, rssList);
        fragmentArgs.putInt(sourceNameIdVarName, sourceNameId);
        fragment.setArguments(fragmentArgs);
        replaceFragment(R.id.news_fragment_container, fragment);
    }

    @Override
    public void createNewsFromJsonFragment(MeduzaNews meduzaNews) {
        NewsFromJsonFragment fragment = new NewsFromJsonFragment();
        String argName = "meduzaNews";
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putParcelable(argName, meduzaNews);
        fragment.setArguments(fragmentArgs);
        replaceFragment(R.id.news_fragment_container, fragment);
    }

    @Override
    public void createWebViewFragment(String newsUrl) {
        WebViewFragment fragment = new WebViewFragment();
        String argName = "newsUrl";
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString(argName, newsUrl);
        fragment.setArguments(fragmentArgs);
        replaceFragment(R.id.news_fragment_container, fragment);
    }

/*    private void addFragment(int fragmentView, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(fragmentView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    private void replaceFragment(int fragmentView, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(fragmentView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}