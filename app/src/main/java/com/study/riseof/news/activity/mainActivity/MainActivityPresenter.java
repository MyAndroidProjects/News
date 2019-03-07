package com.study.riseof.news.activity.mainActivity;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;


public class MainActivityPresenter implements MainActivityContract.Presenter {


    private static MainActivityPresenter instance;
    private MainActivityContract.Navigator navigator;
    private MainActivityContract.View activityView;

    private int delayOpenNavigatorView = 20;

    private MainActivityPresenter() {
        navigator = MainActivityNavigator.getInstance();
        Log.d("myLog", " MainActivityPresenter CONSTRUCTOR ");
    }

    public static MainActivityContract.Presenter getInstance() {
        if (instance == null) {
            instance = new MainActivityPresenter();
        }
        return instance;
    }

    @Override
    public void setActivityView(MainActivityContract.View activityView) {
        this.activityView = activityView;
    }

    @Override
    public void activityFirstLaunch() {
        if (navigator != null) {
            navigator.createNavigatorViewFragment();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (activityView != null) {
                        activityView.openDrawer();
                    }else {
                        Log.d("myLog", "postDelayed activityView == null ");
                    }
                }
            }, delayOpenNavigatorView);
        }
    }


    @Override
    public void onMenuButtonHome() {
        if (activityView != null) {
            activityView.openDrawer();
        }
    }

    @Override
    public void onBackButtonPressed() {
        if (activityView != null) {
            activityView.closeDrawer();
            activityView.callSuperOnBackPressed();
            if (isBackStackEmpty()) {
                Log.d("myLog", " isBackStackEmpty() FIRST BACK");
                activityView.callSuperOnBackPressed();
            }
        }
    }


    private boolean isBackStackEmpty() {
        FragmentManager fragmentManager = activityView.getCurrentFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        return (count == 0);
    }

    private void oldPresenter() {
/*
    enum FragmentContent {
        RSS,
        NOT_RSS
    }

    private static boolean firstStart = true;

    private static MainActivityPresenter instance;
    private MainActivityContract.View activityView;
    private ArrayList<Item> rssList;
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







/*    private void getRssListFromNetAndCreateOrUpdateRssFragment(RetrofitApi.Xml xmlApi, final boolean needToCreateRssFragment) {
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

*//*
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
        });*//*

         *//*
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
                );*//*

         *//*

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
*//*

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
        // todo передать объект MeduzaNews meduzaNews в активити
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
    */
    }
}