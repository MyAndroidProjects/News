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
    private final NavigationViewFragmentContract.Navigator navigator;
/*    // for check
    @SuppressWarnings("FieldCanBeLocal")
    private NavigationViewFragmentContract.View view;*/

    private NewsSource currentNewsSource = NewsSource.EMPTY;
    private ArrayList<Item> rssList;

    private boolean userIsWaitingForCurrentRss = false;

    private NavigationViewFragmentPresenter() {
        navigator = NavigationViewFragmentNavigator.getInstance();
        //     Log.d("myLog", " NavigationViewFragmentPresenter CONSTRUCTOR ");
    }

    public static NavigationViewFragmentContract.Presenter getInstance() {
        if (instance == null) {
            instance = new NavigationViewFragmentPresenter();
        }
        return instance;
    }

/*    @Override
    public void setView(NavigationViewFragmentContract.View view) {
        this.view = view;
    }*/

    @Override
    public void menuItemNgsSelected() {
        currentNewsSource = NewsSource.NGS;
    }

    @Override
    public void menuItemMeduzaSelected() {
        currentNewsSource = NewsSource.MEDUZA;
    }

    @Override
    public void menuItemWashingtonpostSelected() {
        currentNewsSource = NewsSource.WASHINGTONPOST;
    }

    @Override
    public void menuItemLentaSelected() {
        currentNewsSource = NewsSource.LENTA;
    }

    @Override
    public void menuItemRtSelected() {
        currentNewsSource = NewsSource.RT;
    }

    @Override
    public void menuItemBackSelected() {
        if (navigator != null) {
            navigator.closeDrawer();
        }
    }

    @Override
    public void menuItemSelectionIsCompleted() {
        userIsWaitingForCurrentRss = false;
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
        navigator.startMainProgressBar();
        userIsWaitingForCurrentRss = true;
        RetrofitApi.Xml xmlApi = currentNewsSource.getXmlApi();
        xmlApi.getRss().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call, @NonNull Response<Rss> response) {
                Rss rss = response.body();
                if (rss != null) {
                    Channel chanel = rss.getChannel();
                    if (chanel != null) {
                        rssList = chanel.getItemList();
                    }
                }
                navigator.setNewsSourceAttributesInActivityView(currentNewsSource);
                if (rssList != null) {
                    navigator.stopMainProgressBar();
                    navigator.createRssFragment(rssList, currentNewsSource.getNameId());
                    userIsWaitingForCurrentRss = false;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {
                navigator.stopMainProgressBar();
                if (userIsWaitingForCurrentRss) {
                    navigator.openDrawer();
                }

                navigator.showShortToast(t.toString());
                userIsWaitingForCurrentRss = false;
                Log.d("myLog", "onFailure " + t);
            }
        });
    }
}