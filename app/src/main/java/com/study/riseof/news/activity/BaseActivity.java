package com.study.riseof.news.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.study.riseof.news.BaseContract;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {
    protected abstract int getLayoutId();

    protected abstract void setActionBar();

    protected abstract void setActionBarTitle(int textId);

    protected abstract void setActionBarColor(int color);

    protected abstract void setStatusBarColor(int color);

    protected abstract void setPresenter();

    protected abstract void nullifyPresenter();

    protected abstract void setActivityToManager();

    protected abstract void nullifyActivityInManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setActionBar();
        Log.d("myLog", " onCreate "+this.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        setPresenter();
        setActivityToManager();
        Log.d("myLog", " onStart "+this.toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        nullifyPresenter();
        nullifyActivityInManager();
        Log.d("myLog", " onStop "+this.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myLog", " onResume "+this.toString());
    }
}
