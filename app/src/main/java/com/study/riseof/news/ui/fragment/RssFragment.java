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
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.ui.adapter.RssRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;

public class RssFragment extends BaseFragment implements
        RssRecyclerViewAdapter.RssNewsClickListener {

    @BindView(R.id.recycler_view_rss)
    RecyclerView recyclerView;

    private RssFragmentListener rssFragmentListener;
    private List<Item> rssList;

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
        if (rssList != null) {
            Log.d("myLog", "rssList != null");
        } else {
            Log.d("myLog", "rss == null");
        }
        setRecyclerAdapter(getContext());

    }

    public void setNewsList(List<Item> rssList) {
        this.rssList = rssList;
    }

    public void setRecyclerAdapter(Context context) {
        if (rssList != null) {
            RssRecyclerViewAdapter adapter = new RssRecyclerViewAdapter(context, rssList);
            adapter.setRssNewsClickListener(this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            if (recyclerView != null) {
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        } else {
            Log.d("myLog", "setRecyclerAdapter rss == null");
        }
    }

    public void setRssFragmentListener(RssFragmentListener rssFragmentListener) {
        this.rssFragmentListener = rssFragmentListener;
    }

    public interface RssFragmentListener {
        void rssNewsClick(int position, String newsUrl);
    }

    @Override
    public void rssNewsClick(int position, String newsUrl) {
        if (rssFragmentListener != null) {
            rssFragmentListener.rssNewsClick(position, newsUrl);
        }

    }
}
