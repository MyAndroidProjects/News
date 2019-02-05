package com.study.riseof.news.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.model.xml.Channel;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.model.xml.Rss;
import com.study.riseof.news.network.JsonConverterRetrofit;
import com.study.riseof.news.network.RetrofitApi;
import com.study.riseof.news.NewsSource;
import com.study.riseof.news.ui.activity.MainActivity;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityContract.MainActivityPresenter {

    enum FragmentContent {
        RSS,
        NOT_RSS
    }

    private static boolean firstStart = true;

    private static MainActivityPresenter instance;
    private MainActivityContract.MainActivityView activityView;
    private List<Item> rssList;
    private NewsSource currentNewsSource = NewsSource.EMPTY;
    private final String EMPTY_STRING = "";
    private final int openDrawerDelay = 20;

    private FragmentContent currentFragmentContent = FragmentContent.NOT_RSS;

    private MainActivityPresenter() {
        Log.d("myLog", " MainActivityPresenter CONSTRUCTOR ");
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
            if (firstStart) {
                activityView.createNewsSourceNavigationViewFragment();
                firstStart = false;
            }
            if (currentNewsSource == NewsSource.EMPTY) {
                activityView.openDrawer();
            }
        }
        setNewsSourceAttributesInActivityView(currentNewsSource);
    }

    @Override
    public void onMenuButtonHome() {
        if (activityView != null) {
            if (activityView.isNavigationViewFragmentExist()) {
                activityView.openDrawer();
            } else {
                activityView.createNewsSourceNavigationViewFragment();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        activityView.openDrawer();
                    }
                }, openDrawerDelay);
            }
        }
    }

    @Override
    public void onActivityDestroy() {
        this.activityView = null;
    }

    @Override
    public void onMenuItemCloseMainDrawer() {
        if (activityView != null) {
            activityView.closeDrawer();
        }
    }

    @Override
    public void onBackButtonPressed() {
        if (activityView != null) {
            activityView.findAllFragmentsByTags();
            activityView.closeDrawer();
            if (!activityView.webViewGoBack()) {
                activityView.callSuperOnBackPressed();
                if (currentFragmentContent == FragmentContent.RSS) {
                    currentNewsSource = NewsSource.EMPTY;
                    setNewsSourceAttributesInActivityView(currentNewsSource);
                    activityView.openDrawer();
                    activityView.uncheckAllNavigationMenuItems();
                    currentFragmentContent = FragmentContent.NOT_RSS;
                } else {
                    currentFragmentContent = FragmentContent.RSS;
                }
            }
        }
    }

    @Override
    public void onNavigationMenuSelectAnyItem() {
        if (activityView != null) {
            activityView.findAllFragmentsByTags();
        }
        if (currentFragmentContent == FragmentContent.RSS) {
            getRssListFromNetAndCreateOrUpdateRssFragment(currentNewsSource.getXmlApi(), false);
        } else {
            if (activityView != null) {
                activityView.cleanBackStack();
            }
            getRssListFromNetAndCreateOrUpdateRssFragment(currentNewsSource.getXmlApi(), true);
            currentFragmentContent = FragmentContent.RSS;
        }
        if (activityView != null) {
            activityView.closeDrawer();
        }
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
    public void rssNewsClick(int position, String newsUrl) {
        currentFragmentContent = FragmentContent.NOT_RSS;
        if (currentNewsSource == NewsSource.MEDUZA) {
            getNewsFromJsonAndSetToView(position, JsonConverterRetrofit.MEDUZA.getRetrofitApi());
        } else {
            if (activityView != null) {
                activityView.createWebViewFragment(newsUrl);
                activityView.replaceRssFragmentWithWebViewFragment();
            }
        }
    }

    private void setNewsSourceAttributesInActivityView(NewsSource source) {
        if (activityView != null) {
            activityView.setActionBarTitle(source.getNameId());
            activityView.setActionBarColor(source.getActionBarColorId());
            activityView.setStatusBarColor(source.getStatusBarColorId());
        }
    }

    private void getRssListFromNetAndCreateOrUpdateRssFragment(RetrofitApi.Xml xmlApi, final boolean needToCreateRssFragment) {
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
                if (activityView != null) {
                    if (needToCreateRssFragment) {
                        activityView.createRssFragment(rssList);
                    } else {
                        activityView.updateRssListAndAdapter(rssList);
                    }
                }
                setNewsSourceAttributesInActivityView(currentNewsSource);
            }

            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {
                activityView.showShortToast(t.toString());
                Log.d("myLog", "onFailure " + t);
            }
        });
    }


    private void getNewsFromJsonAndSetToView(int position, RetrofitApi.Json jsonApi) {
        String newsUrl = rssList.get(position).getLink();
        newsUrl = newsUrl.replace(JsonConverterRetrofit.MEDUZA.getMainUrl(), EMPTY_STRING);

/*
        // retrofit без rxjava
        jsonApi.getCallData(newsUrl).enqueue(new Callback<MeduzaNews>() {
            @Override
            public void onResponse(@NonNull Call<MeduzaNews> call, @NonNull Response<MeduzaNews> response) {
                if (activityView != null) {
                    Log.d("myLog", "getNewsFromJsonAndSetToView activityView != null");

                    Log.d("myLog", " getNewsFromJsonAndSetToView " + response.body().getRoot().getTitle());
                    MeduzaNews meduzaNews = response.body();
                    setDataFromModelToNews(meduzaNews);
                    activityView.replaceRssFragmentWithNewsFromJsonFragment();

                }
            }

            @Override
            public void onFailure(@NonNull Call<MeduzaNews> call, @NonNull Throwable t) {
                Log.d("myLog", "getNewsFromJsonAndSetToView onFailure " + t);
                activityView.showShortToast(t.toString());

            }
        });*/

/*
      // retrofit с rxjava Single
    jsonApi.getSingleData(newsUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<MeduzaNews>() {
                               @Override
                               public void onSuccess(@NonNull MeduzaNews meduzaNews) {
                               setDataFromModelToNews(meduzaNews);
                               activityView.replaceRssFragmentWithNewsFromJsonFragment();
                                   Log.d("myLog", "onSuccess " +  meduzaNews.getRoot().getTitle());
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                               activityView.showShortToast(e.toString());
                                 Log.d("myLog", "onError " + e.toString());
                               }
                           }
                );*/

/*

        // retrofit с rxjava Maybe
        jsonApi.getMaybeData(newsUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<MeduzaNews>() {

                    @Override
                    public void onSuccess(MeduzaNews meduzaNews) {
                        setDataFromModelToNews(meduzaNews);
                        activityView.replaceRssFragmentWithNewsFromJsonFragment();
                        Log.d("myLog", "onSuccess " + meduzaNews.getRoot().getTitle());

                    }

                    @Override
                    public void onError(Throwable e) {
                        activityView.showShortToast(e.toString());
                        Log.d("myLog", "onError " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("myLog", "onComplete ");
                    }
                });
*/

        // retrofit с rxjava observer
        Observer<MeduzaNews> observer = new Observer<MeduzaNews>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(MeduzaNews meduzaNews) {
                Log.d("myLog", "onSuccess " + meduzaNews.getRoot().getTitle());
                setDataFromModelToNews(meduzaNews);
                activityView.replaceRssFragmentWithNewsFromJsonFragment();
            }

            @Override
            public void onError(Throwable e) {
                activityView.showShortToast(e.toString());
                Log.d("myLog", "onError " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("myLog", "onComplete ");
            }
        };

        jsonApi.getObservableData(newsUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void setDataFromModelToNews(MeduzaNews meduzaNews) {
        if (meduzaNews != null) {
            String titleText = meduzaNews.getRoot().getTitle();
            String descriptionText = meduzaNews.getRoot().getDescription();
            String bodyTextHtml = meduzaNews.getRoot().getContent().getBody();
            String bodyText;
            if (bodyTextHtml != null) {
                bodyText = Jsoup.parse(bodyTextHtml).text();
            } else {
                bodyText = EMPTY_STRING;
            }
            String pubDateText = meduzaNews.getRoot().getPubDate();
            String tempUrl = JsonConverterRetrofit.MEDUZA.getMainUrl() + meduzaNews.getRoot().getShareImage();
            ArrayList<String> imageUrlList = new ArrayList<>();
            imageUrlList.add(tempUrl);
            if (activityView != null) {
                activityView.createNewsFromJsonFragment(titleText, descriptionText, bodyText, pubDateText, imageUrlList);
            }
        }
    }

    @Override
    public void webViewFragmentMessage(String message) {
        if (activityView != null) {
            activityView.showShortToast(message);
        }
    }
}