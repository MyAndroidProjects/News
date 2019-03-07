package com.study.riseof.news.fragment.navigationView;

import android.support.annotation.NonNull;
import android.util.Log;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.model.xml.Channel;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.model.xml.Rss;
import com.study.riseof.news.network.RetrofitApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationViewFragmentPresenter implements NavigationViewFragmentContract.Presenter {


    private static NavigationViewFragmentPresenter instance;
    private NavigationViewFragmentContract.Navigator navigator;
    private NavigationViewFragmentContract.View view;

    private NewsSource currentNewsSource = NewsSource.EMPTY;
    private ArrayList<Item> rssList;


    private NavigationViewFragmentPresenter() {
        navigator = NavigationViewFragmentNavigator.getInstance();
        Log.d("myLog", " NavigationViewFragmentPresenter CONSTRUCTOR ");
    }

    public static NavigationViewFragmentContract.Presenter getInstance() {
        if (instance == null) {
            instance = new NavigationViewFragmentPresenter();
        }
        return instance;
    }

    @Override
    public void setView(NavigationViewFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void onNavigationMenuItemNgs() {
        currentNewsSource = NewsSource.NGS;

    }

    @Override
    public void onNavigationMenuItemMeduza() {
        currentNewsSource = NewsSource.MEDUZA;
    }

    @Override
    public void onNavigationMenuItemYandex() {
        currentNewsSource = NewsSource.YANDEX;
    }

    @Override
    public void onNavigationMenuItemLenta() {
        currentNewsSource = NewsSource.LENTA;
    }

    @Override
    public void onNavigationMenuItemRbc() {
        currentNewsSource = NewsSource.RBC;
    }

    @Override
    public void onMenuItemCloseMainDrawer() {
        if (navigator != null) {
            navigator.closeDrawer();
        }
    }

    @Override
    public void onNavigationMenuSelectAnyItem() {
        Log.d("myLog", " onNavigationMenuSelectAnyItem  " + currentNewsSource.toString());
        if (navigator != null) {
            navigator.cleanBackStack();
            navigator.setCurrentNewsSource(currentNewsSource);
            navigator.setNewsSourceAttributesInActivityView(currentNewsSource);
        }
        getRssListFromNetAndCreateRssFragment();

        if (navigator != null) {
            navigator.closeDrawer();
        }
    }

    private void getRssListFromNetAndCreateRssFragment() {
        RetrofitApi.Xml xmlApi = currentNewsSource.getXmlApi();
        xmlApi.getRss().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call, @NonNull Response<Rss> response) {
                Log.d("myLog", " onResponse  ");
                Rss rss = response.body();
                if (rss != null) {
                    Log.d("myLog", " rss != null  ");
                    Channel chanel = rss.getChannel();
                    if (chanel != null) {
                        rssList = chanel.getItemList();
                    }
                } else {
                    Log.d("myLog", " rss == null  ");
                }
                if (navigator != null) {
                    navigator.setNewsSourceAttributesInActivityView(currentNewsSource);
                    if (rssList != null) {
                        navigator.createRssFragment(rssList, currentNewsSource.getNameId());
                        Log.d("myLog", " currentNewsSource == " + currentNewsSource.toString());
                    }
                } else {
                    Log.d("myLog", " navigator == null  ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {
                navigator.showShortToast(t.toString());
                Log.d("myLog", "onFailure " + t);
            }
        });
    }
}