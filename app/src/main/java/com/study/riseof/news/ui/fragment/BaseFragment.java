package com.study.riseof.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Unbinder unbinder;
    protected View view;
    protected final String EMPTY_STRING = "";

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
       // Log.d("myLog"," onCreateView " + this.toString());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("myLog"," onCreate " + this.toString());
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
       // Log.d("myLog"," onStart " + this.toString());
        super.onStart();
    }

    @Override
    public void onDestroyView() {
       //     Log.d("myLog"," onDestroyView " + this.toString());
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     //      Log.d("myLog"," onDestroy " + this.toString());
    }

    @Override
    public void onStop() {
   //     Log.d("myLog"," onStop " + this.toString());

        super.onStop();
    }

    @Override
    public void onPause() {
      //  Log.d("myLog"," onPause " + this.toString());
        super.onPause();
    }
}
