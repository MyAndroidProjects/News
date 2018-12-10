package com.study.riseof.news.presenter;

import android.util.Log;
import android.view.View;

import com.study.riseof.news.model.ngs.Item;
import com.study.riseof.news.model.ngs.NgsRss;
import com.study.riseof.news.network.RetrofitApi;
import com.study.riseof.news.network.ngs.NgsXmlConverterRetrofit;
import com.study.riseof.news.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityContract.MainActivityPresenter {

    private static MainActivityPresenter instance;
    private MainActivityContract.MainActivityView activityView;
    private String baseUrlNgsRss ="https://news.ngs.ru/news/rss/";

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
        activityView.createNewsSourceNavigationViewFragment();
    }

    @Override
    public void deAttachView() {
        this.activityView = null;
    }


    @Override
    public void onMenuButtonHome() {
        Log.d("myLog", "onMenuButtonHome");
        activityView.openDrawer();
    }

    @Override
    public void onActivityDestroy() {
        deAttachView();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
       // activityView.showShortToast("onDrawerSlide");
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        //activityView.showShortToast("onDrawerOpened");
    }

    @Override
    public void onDrawerClosed(View drawerView) {
      //  activityView.showShortToast("onDrawerClosed");
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        activityView.showShortToast("onDrawerStateChanged");
    }


    @Override
    public void onMenuItemCloseMainDrawer() {
        activityView.showShortToast("onMenuItemCloseMainDrawer");
        activityView.closeDrawer();
    }

    @Override
    public void onNavigationMenuAnyItem() {
        activityView.closeDrawer();
    }

    @Override
    public void onNavigationMenuItemYandex() {
        activityView.createRssFragment();
    }

    @Override
    public void onNavigationMenuItemMeduza() {
/*       // setRssList();
  *//*      MeduzaRetrofit meduzaRetrofit = MeduzaRetrofit.getInstance();
        meduzaRetrofit.buildRetrofit();
        Log.d("myLog","ДАЛЬШЕ");
        meduzaRetrofit.getApi().getRssNews().enqueue(new Callback<List<MeduzaCutNewsTest>>() {
            @Override
            public void onResponse(Call<List<MeduzaCutNewsTest>> call, Response<List<MeduzaCutNewsTest>> response) {
                Log.d("myLog","public void onResponse");
                Log.d("myLog",String.valueOf(response.body().size()));
                //Данные успешно пришли, но надо проверить response.body() на null
                if(response.body()==null){
                    Log.d("myLog","response.body()==null");
                } else {
                    Log.d("myLog",String.valueOf(response.body().size()));
                }
            }
            @Override
            public void onFailure(Call<List<MeduzaCutNewsTest>> call, Throwable t) {
                //Произошла ошибка
            }
        });*//*

        meduzaRetrofit = MeduzaRetrofit.getInstance();
        meduzaRetrofit.buildRetrofit();
        MeduzaApi meduzaApi = meduzaRetrofit.getMeduzaApi();
       *//* meduzaApi.getRssXmlNews().enqueue(new Callback<MeduzaCutNewsTest>(){
            @Override
            public void onResponse(Call<MeduzaCutNewsTest> call, Response<MeduzaCutNewsTest> response) {
                Log.d("myLog","onResponse");
                Log.d("myLog","response.body().size() "+response.body().getTitle());
            }
            @Override
            public void onFailure(Call<MeduzaCutNewsTest> call, Throwable t) {
                Log.d("myLog","onFailure");

            }



        });*//*
        meduzaApi.getRssXmlNewsList("https://meduza.io/rss/news").enqueue(new Callback<MeduzaRss>(){
            @Override
            public void onResponse(Call<MeduzaRss> call, Response<MeduzaRss> response) {
                Log.d("myLog","getRssXmlNewsList onResponse");
                MeduzaRss meduzaRss =response.body();
                Log.d("myLog","getRssXmlNewsList meduzaRss.getVersion() "+meduzaRss.getVersion());
                Log.d("myLog","getRssXmlNewsList response.body() "+response.body());
            }
            @Override
            public void onFailure(Call<MeduzaRss> call, Throwable t) {
                Log.d("myLog","NewsTestList onFailure " +t);

            }
        });
        //Log.d("myLog",news.toString());

      //  activityView.setRssList(rssList);
       // activityView.createRssFragment();*/
    }

    @Override
    public void onNavigationMenuItemNgs() {

        NgsXmlConverterRetrofit ngsXmlConverterRetrofit = NgsXmlConverterRetrofit.getInstance();
        ngsXmlConverterRetrofit.setBaseUrl(baseUrlNgsRss);
        ngsXmlConverterRetrofit.buildRetrofit();
        RetrofitApi.Ngs ngsApi = ngsXmlConverterRetrofit.getRetrofitApi();

        ngsApi.getRss().enqueue(new Callback<NgsRss>(){
            @Override
            public void onResponse(Call<NgsRss> call, Response<NgsRss> response) {
                activityView.setRssList(response.body().getChannel().getItemList());
                activityView.createRssFragment();
            }
            @Override
            public void onFailure(Call<NgsRss> call, Throwable t) {
                Log.d("myLog","NewsTestList onFailure " +t);
            }
        });

    }

    @Override
    public void onNavigationMenuItemLenta() {
        activityView.showShortToast("onNavigationMenuItemLenta");
    }

    @Override
    public void onNavigationMenuItemRia() {
        activityView.showShortToast("onNavigationMenuItemRia");
    }


    private void testLogInfo(Response<NgsRss> response){
        Log.d("myLog","testLogInfo");
        Log.d("myLog",response.body().toString());
        Log.d("myLog",response.body().getChannel().getTitle());
        Log.d("myLog",response.body().getChannel().getDescription());
        Log.d("myLog",response.body().getChannel().getItemList().get(0).getTitle());
        Log.d("myLog",response.body().getChannel().getItemList().get(0).getAuthor());
        Log.d("myLog",response.body().getChannel().getItemList().get(0).getDescription());
        Log.d("myLog",response.body().getChannel().getItemList().get(0).getPubDate());
        Log.d("myLog",response.body().getChannel().getItemList().get(1).getTitle());
        Log.d("myLog",response.body().getChannel().getItemList().get(1).getAuthor());
        Log.d("myLog",response.body().getChannel().getItemList().get(1).getDescription());
        Log.d("myLog",response.body().getChannel().getItemList().get(1).getPubDate());
    }
}
