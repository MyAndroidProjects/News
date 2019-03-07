package com.study.riseof.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.riseof.news.BaseContract;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseContract.View {
    protected Unbinder unbinder;
    protected View view;
    protected final String EMPTY_STRING = "";

    protected abstract int getLayoutId();


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.d("myLog", " NavigationView onCreateView " + this.toString());
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        setPresenter();
        Log.d("myLog", " Fragment on START " + this.toString());
    }


    @Override
    public void onStop() {
        super.onStop();
        nullifyPresenter();
        Log.d("myLog", " Fragment on STOP " + this.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d("myLog", " Fragment  onDestroyView " + this.toString());
    }

    abstract public void setPresenter();

    abstract public void nullifyPresenter();


}
