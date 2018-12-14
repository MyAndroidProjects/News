package com.study.riseof.news.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.model.xml.Rss;
import com.study.riseof.news.network.RetrofitApi;
import com.study.riseof.news.network.XmlConverterRetrofit;
import com.study.riseof.news.ui.NewsSource;
import com.study.riseof.news.ui.activity.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityContract.MainActivityPresenter {

    private static MainActivityPresenter instance;
    private MainActivityContract.MainActivityView activityView;
    private List<Item> rssList;


    private MainActivityPresenter() {
    }

    public static MainActivityPresenter getInstance() {
        if (instance == null) {
            instance = new MainActivityPresenter();
        }
        return instance;
    }

    @Override
    public void attachView(MainActivity activity) {
        this.activityView = activity;
    }

    @Override
    public void activityOnStart() {
        if (activityView != null) {
            activityView.createNewsSourceNavigationViewFragment();
        }

    }

    @Override
    public void deAttachView() {
        this.activityView = null;
    }


    @Override
    public void onMenuButtonHome() {
        Log.d("myLog", "onMenuButtonHome");
        if (activityView != null) {
            activityView.openDrawer();
        }
    }

    @Override
    public void onActivityDestroy() {
        deAttachView();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (activityView != null) {
            // activityView.showShortToast("onDrawerSlide");
        }

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        if (activityView != null) {
            //activityView.showShortToast("onDrawerOpened");
        }

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (activityView != null) {
            //  activityView.showShortToast("onDrawerClosed");
        }

    }

    @Override
    public void onDrawerStateChanged(int newState) {
        if (activityView != null) {
            //    activityView.showShortToast("onDrawerStateChanged");
        }
    }


    @Override
    public void onMenuItemCloseMainDrawer() {
        if (activityView != null) {
            //  activityView.showShortToast("onMenuItemCloseMainDrawer");
            activityView.closeDrawer();
        }
    }

    @Override
    public void onNavigationMenuAnyItem() {
        if (activityView != null) {
            activityView.closeDrawer();
        }
    }

    @Override
    public void onNavigationMenuItemYandex() {
        getRssListAndSetToActivityView(XmlConverterRetrofit.YANDEX.getRetrofitApi());
        setNewsSourceAttributesInActivityView(NewsSource.YANDEX);
    }

    @Override
    public void onNavigationMenuItemMeduza() {
        getRssListAndSetToActivityView(XmlConverterRetrofit.MEDUZA.getRetrofitApi());
        setNewsSourceAttributesInActivityView(NewsSource.MEDUZA);
    }

    @Override
    public void onNavigationMenuItemNgs() {
        getRssListAndSetToActivityView(XmlConverterRetrofit.NGS.getRetrofitApi());
        setNewsSourceAttributesInActivityView(NewsSource.NGS);
    }

    @Override
    public void onNavigationMenuItemLenta() {
        getRssListAndSetToActivityView(XmlConverterRetrofit.LENTA.getRetrofitApi());
        setNewsSourceAttributesInActivityView(NewsSource.LENTA);
    }

    @Override
    public void onNavigationMenuItemRia() {
        if (activityView != null) {
            activityView.showShortToast("onNavigationMenuItemRia");
        }
        setNewsSourceAttributesInActivityView(NewsSource.RIA);
    }


    @Override
    public void rssNewsClick(int position, String newsUrl) {
        activityView.showShortToast("позиция: " + position + " newsUrl "+ newsUrl);
        activityView.createWebViewFragment(newsUrl);
        activityView.replaceRssFragmentWithWebViewFragment();
    }

    private void testLogInfo(Response<Rss> response) {
        Log.d("myLog", "testLogInfo");
        Log.d("myLog", response.body().toString());
        Log.d("myLog", response.body().getChannel().getTitle());
        Log.d("myLog", response.body().getChannel().getDescription());
        Log.d("myLog", response.body().getChannel().getItemList().get(0).getTitle());
        Log.d("myLog", response.body().getChannel().getItemList().get(0).getAuthor());
        Log.d("myLog", response.body().getChannel().getItemList().get(0).getDescription());
        Log.d("myLog", response.body().getChannel().getItemList().get(0).getPubDate());
        Log.d("myLog", response.body().getChannel().getItemList().get(1).getTitle());
        Log.d("myLog", response.body().getChannel().getItemList().get(1).getAuthor());
        Log.d("myLog", response.body().getChannel().getItemList().get(1).getDescription());
        Log.d("myLog", response.body().getChannel().getItemList().get(1).getPubDate());
    }

    private void setNewsSourceAttributesInActivityView(NewsSource source){
        if (activityView != null) {
            activityView.setActionBarTitle(source.getNameId());
            activityView.setActionBarColor(source.getActionBarColorId());
            activityView.setStatusBarColor(source.getStatusBarColorId());
        }
    }

    private void getRssListAndSetToActivityView(RetrofitApi.Xml xmlApi){
        xmlApi.getRss().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call, @NonNull Response<Rss> response) {
                if (activityView != null) {
                    if (response.body() != null) {
                        activityView.setRssList(response.body().getChannel().getItemList());
                    }
                    activityView.createRssFragment();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {
                Log.d("myLog", "NewsTestList onFailure " + t);
            }
        });
    }

    //--------------


}
