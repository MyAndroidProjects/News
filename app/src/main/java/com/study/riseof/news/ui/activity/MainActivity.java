package com.study.riseof.news.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.icu.util.Currency;
import android.os.Build;
import android.os.Handler;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.R;
import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.presenter.MainActivityContract;
import com.study.riseof.news.presenter.MainActivityNavigator;
import com.study.riseof.news.presenter.MainActivityPresenter;
import com.study.riseof.news.ui.fragment.NavigationViewFragment;
import com.study.riseof.news.ui.fragment.NewsFromJsonFragment;
import com.study.riseof.news.ui.fragment.RssFragment;
import com.study.riseof.news.ui.fragment.WebViewFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        MainActivityContract.View,
        Navigation.MainActivity {


    private MainActivityContract.Presenter presenter;
    private MainActivityContract.Navigator navigator;
    private Navigation.SetActivities manager;

    private ActionBar actionbar;
    private NewsSource currentNewsSource = NewsSource.EMPTY;
    private String currentNewsSourceName = NewsSource.EMPTY.name();
    private String currentNewsSourceNameVarName = "currentNewsSourceName";
    private boolean isFirstLaunch = true;
    private String isFirsLaunchVarName = "isFirstLaunch";

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
        Log.d("myLog", " FIRST Launch " + isFirstLaunch);
    }

    protected void getBundleArgs(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isFirstLaunch = savedInstanceState.getBoolean(isFirsLaunchVarName, true);
            currentNewsSourceName =savedInstanceState.getString(currentNewsSourceNameVarName,NewsSource.EMPTY.name());
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
        if (manager != null) {
            manager.setMainActivityToNavigationManager(this);
        } else {
            Log.d("myLog", " this manager == null ");
        }
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

        //todo
    }

    @Override
    public void onStop() {
        super.onStop();
        if (manager != null) {
            manager.setMainActivityToNavigationManager(null);
        } else {
            Log.d("myLog", " null manager == null ");
        }
// todo отписать активити от менеджера
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
    public void setPresenterAndNavigator() {
        navigator = MainActivityNavigator.getInstance();
        presenter = MainActivityPresenter.getInstance(navigator);
    }

    @Override
    public void nullifyPresenterAndNavigator() {
        presenter = null;
        navigator = null;
    }

    @Override
    public void setActivityToManager() {
        //todo setActivityToManager
    }

    @Override
    public void nullifyActivityInManager() {
        //todo nullifyActivityInManager
    }

    //------------------------------

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
        presenter.onBackButtonPressed();
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
        Log.d("myLog", " openDrawer(GravityCompat.START) ");
    }

    @Override
    public void onBackButtonPressed() {
        Log.d("myLog", " onBackButtonPressed ");
        super.onBackPressed();
    }

    @Override
    public void cleanBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fragmentManager.popBackStackImmediate();
        }
        Log.d("myLog", " cleanBackStack ");
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
        replaceFragment(R.id.navigation_view_fragment, fragment);
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

    private void addFragment(int fragmentView, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(fragmentView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void replaceFragment(int fragmentView, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(fragmentView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
//-------------------------


    private void oldActivity() {
        /*




        private NavigationViewFragment navigationViewFragment;
        private RssFragment rssFragment;
        private WebViewFragment webViewFragment;
        private NewsFromJsonFragment newsFromJsonFragment;



        private void addOrReplaceFragment ( int fragmentView, Fragment fragment, String tag,
        boolean addingFragment, boolean addTransactionToBackStack){
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
    }

        @Override
       public void createWebViewFragment (String newsUrl){
            webViewFragment = new WebViewFragment();
            Bundle webViewFragmentArgs = new Bundle();
            webViewFragmentArgs.putString(newsUrlArgName, newsUrl);
            webViewFragment.setArguments(webViewFragmentArgs);
            webViewFragment.setWebViewListener(this);
        }

        @Override
        public void replaceRssFragmentWithWebViewFragment () {
            if (webViewFragment == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                webViewFragment = (WebViewFragment) fragmentManager.findFragmentByTag(tagWebViewFragment);
            }
            if (webViewFragment != null) {
                addOrReplaceFragment(R.id.news_fragment_container, webViewFragment, tagWebViewFragment, false, true);
                //   replaceFragment(R.id.news_fragment_container, webViewFragment, null, true);
            }
        }

        @Override
        public void createNewsFromJsonFragment (String titleText,
                String descriptionText,
                String bodyText,
                String pubDateText,
                ArrayList < String > imageUrlList){
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
        public void replaceRssFragmentWithNewsFromJsonFragment () {
            if (newsFromJsonFragment == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                newsFromJsonFragment = (NewsFromJsonFragment) fragmentManager.findFragmentByTag(tagNewsFromJsonFragment);
            }
            if (newsFromJsonFragment != null) {
                addOrReplaceFragment(R.id.news_fragment_container, newsFromJsonFragment, tagNewsFromJsonFragment, false, true);
                //    replaceFragment(R.id.news_fragment_container, newsFromJsonFragment, null, true);
            }
        }

        @Override
        public void createRssFragment (ArrayList < Item > rssList, NewsSource currentNewsSource){
            if (rssFragment == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                rssFragment = (RssFragment) fragmentManager.findFragmentByTag(tagRssFragment);
            }
            if (rssFragment == null) {
                rssFragment = new RssFragment();
                //   addFragment(R.id.news_fragment_container, rssFragment, null, true);
                addOrReplaceFragment(R.id.news_fragment_container, rssFragment, tagRssFragment, true, true);
            } else {
                //  replaceFragment(R.id.news_fragment_container, rssFragment, null, true);
                addOrReplaceFragment(R.id.news_fragment_container, rssFragment, tagRssFragment, false, true);
            }
            rssFragment.setRssFragmentListener(this);
            updateRssListAndAdapter(rssList);
        }

        @Override
        public void updateRssListAndAdapter (ArrayList < Item > rssList) {
            if (rssList != null) {
                if (rssFragment != null) {
                    rssFragment.setNewsListAndContext(rssList, this);
                    rssFragment.setRecyclerAdapter();
                } else {
                    createRssFragment(rssList, currentNewsSource);
                }
            }
        }

        @Override
        public void createNewsSourceNavigationViewFragment () {
            if (navigationViewFragment == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                navigationViewFragment = (NavigationViewFragment) fragmentManager.findFragmentByTag(tagNavigationViewFragment);
                if (navigationViewFragment == null) {
                    navigationViewFragment = new NavigationViewFragment();
                    //   addFragment(R.id.navigation_view_fragment, navigationViewFragment, tagNavigationViewFragment, false);
                    addOrReplaceFragment(R.id.navigation_view_fragment, navigationViewFragment, tagNavigationViewFragment, true, false);
                }
            }
            navigationViewFragment.setNavigationViewListener(this);
        }



        @Override
        public void callSuperOnBackPressed () {
            super.onBackPressed();
        }

        @Override
        public void uncheckAllNavigationMenuItems () {
            if (navigationViewFragment != null) {
                navigationViewFragment.uncheckAllNavigationMenuItems();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                navigationViewFragment = (NavigationViewFragment) fragmentManager.findFragmentByTag(tagNavigationViewFragment);
                if (navigationViewFragment != null) {
                    navigationViewFragment.uncheckAllNavigationMenuItems();
                }
            }
        }

        @Override
        public void cleanBackStack () {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int count = fragmentManager.getBackStackEntryCount();
            for (int i = 0; i < count; ++i) {
                fragmentManager.popBackStackImmediate();
            }
        }

        @Override
        public boolean webViewGoBack () {
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


*/
    }
}