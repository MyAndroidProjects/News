package com.study.riseof.news.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.model.xml.Rss;
import com.study.riseof.news.network.JsonConverterRetrofit;
import com.study.riseof.news.network.RetrofitApi;
import com.study.riseof.news.NewsSource;
import com.study.riseof.news.ui.activity.MainActivity;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityContract.MainActivityPresenter {

    private static MainActivityPresenter instance;
    private MainActivityContract.MainActivityView activityView;
    private List<Item> rssList;
    private NewsSource currentNewsSource = NewsSource.EMPTY;
    private String EMPTY_STRING = "";

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
            if (currentNewsSource == NewsSource.EMPTY) {
                activityView.openDrawer();
            }
        }
        setNewsSourceAttributesInActivityView(currentNewsSource);

    }

    @Override
    public void deAttachView() {
        this.activityView = null;
    }


    @Override
    public void onMenuButtonHome() {
        if (activityView != null) {
            activityView.openDrawer();
        }
    }

    @Override
    public void onActivityDestroy() {
        deAttachView();
    }

    public List<Item> getRssList() {
        return rssList;
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
    public void onBackButtonPressed() {
        // todo оработка нажатия back
        if (activityView != null) {
            activityView.closeDrawer();
        }
    }

    @Override
    public void onNavigationMenuAnyItem() {
        setNewsSourceAttributesInActivityView(currentNewsSource);
        getRssListFromNetAndSetToView(currentNewsSource.getXmlApi());
        if (activityView != null) {
            activityView.closeDrawer();
        }

    }

    @Override
    public void onNavigationMenuItemYandex() {
        currentNewsSource = NewsSource.YANDEX;
    }

    @Override
    public void onNavigationMenuItemMeduza() {
        currentNewsSource = NewsSource.MEDUZA;
    }

    @Override
    public void onNavigationMenuItemNgs() {
        currentNewsSource = NewsSource.NGS;
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
    public void rssNewsClick(int position, String newsUrl) {
        //   activityView.showShortToast("позиция: " + position + " newsUrl " + newsUrl);

        if (currentNewsSource == NewsSource.MEDUZA) {
            getNewsFromJsonAndSetToView(position, JsonConverterRetrofit.MEDUZA.getRetrofitApi());

        } else {
            if (activityView != null) {
                activityView.createWebViewFragment(newsUrl);
                activityView.replaceRssFragmentWithWebViewFragment();
            }
        }

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

    private void setNewsSourceAttributesInActivityView(NewsSource source) {
        if (activityView != null) {
            activityView.setActionBarTitle(source.getNameId());
            activityView.setActionBarColor(source.getActionBarColorId());
            activityView.setStatusBarColor(source.getStatusBarColorId());
        }
    }

    private void getRssListFromNetAndSetToView(RetrofitApi.Xml xmlApi) {
        xmlApi.getRss().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call, @NonNull Response<Rss> response) {
                if (response.body() != null) {
                    Log.d("myLog", "get(0).getTitle() " + response.body().getChannel().getItemList().get(0).getTitle());
                    rssList = response.body().getChannel().getItemList();
                }
                setRssListToView();
            }

            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {
                Log.d("myLog", "NewsTestList onFailure " + t);
            }
        });
    }

    private void setRssListToView() {
        if (activityView != null) {
            activityView.setRssList(rssList);
            activityView.createRssFragment();
        }
    }

    private void getNewsFromJsonAndSetToView(int position, RetrofitApi.Json jsonApi) {
        String newsUrl = rssList.get(position).getLink();
        newsUrl = newsUrl.replace(JsonConverterRetrofit.MEDUZA.getMainUrl(),EMPTY_STRING);
        jsonApi.getData(newsUrl).enqueue(new Callback<MeduzaNews>() {
            @Override
            public void onResponse(@NonNull Call<MeduzaNews> call, @NonNull Response<MeduzaNews> response) {
                if (activityView != null) {
                    Log.d("myLog", "getNewsFromJsonAndSetToView activityView != null");

                    Log.d("myLog", " getNewsFromJsonAndSetToView " + response.body().getRoot().getTitle());
                    MeduzaNews meduzaNews = response.body();
                    if (meduzaNews != null) {
                        String titleText = meduzaNews.getRoot().getTitle();
                        String descriptionText = meduzaNews.getRoot().getDescription();
                        String bodyTextHtml = meduzaNews.getRoot().getContent().getBody();
                        String bodyText = Jsoup.parse(bodyTextHtml).text();
                        String pubDateText = meduzaNews.getRoot().getPubDate();
                        String tempUrl=JsonConverterRetrofit.MEDUZA.getMainUrl()+meduzaNews.getRoot().getShareImage();
                        ArrayList<String> imageUrlList = new ArrayList<>();
                        imageUrlList.add(tempUrl);

                        activityView.createNewsFromJsonFragment(titleText, descriptionText, bodyText, pubDateText, imageUrlList);
                        activityView.replaceRssFragmentWithNewsFromJsonFragment();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MeduzaNews> call, @NonNull Throwable t) {
                Log.d("myLog", "getNewsFromJsonAndSetToView onFailure " + t);
            }
        });
    }

// todo сделать свой backStack из новостных ресурсов чтобы цвет toobar соответствовал новости
    // todo подумать над backStack rss источника, т.к. при возврате в фрагмент rss загружается последний rssList, а не в обратном порядке.
}
