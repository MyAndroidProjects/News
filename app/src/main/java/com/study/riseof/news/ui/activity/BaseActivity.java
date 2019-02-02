package com.study.riseof.news.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutId();
    protected abstract void setActionBar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setActionBar();
        Log.d("myLog","Activity onCreate " + this.toString());
    }




    @Override
    public void onStop() {
        Log.d("myLog"," onStop " + this.toString());

        super.onStop();
    }

    @Override
    public void onPause() {
        Log.d("myLog"," onPause " + this.toString());
        super.onPause();
    }
}
