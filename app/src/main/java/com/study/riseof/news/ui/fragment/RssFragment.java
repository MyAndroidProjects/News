package com.study.riseof.news.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.riseof.news.R;
import com.study.riseof.news.model.MeduzaCutNews;
import com.study.riseof.news.ui.adapter.RssRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RssFragment extends BaseFragment {
    @BindView(R.id.recycler_view_rss)
    RecyclerView recyclerView;

    RssFragmentListener rssFragmentListener;
    List<MeduzaCutNews> rssList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rss;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        setRecyclerAdapter(getContext());
    }

    public void setNewsList(List<MeduzaCutNews> rssList) {
        this.rssList = rssList;
    }

    public void setRecyclerAdapter(Context context) {
        if(rssList!=null){
            RssRecyclerViewAdapter adapter = new RssRecyclerViewAdapter(context, rssList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    public void setRssFragmentListener(RssFragmentListener rssFragmentListener) {
        this.rssFragmentListener = rssFragmentListener;
    }

    public interface RssFragmentListener {

    }

}
