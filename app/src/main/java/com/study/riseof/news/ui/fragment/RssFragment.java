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
    private RssRecyclerViewAdapter adapter;
    private Context context;
    private LinearLayoutManager layoutManager;
   // private View view;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rss;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (rssList != null) {
            Log.d("myLog", "rssList != null");
        } else {
            Log.d("myLog", "rss == null");
        }
        setRecyclerAdapter();
    }

    public void setNewsListAndContext(List<Item> rssList, Context context) {
        this.rssList = rssList;
        this.context = context;
    }

    public void setRecyclerAdapter() {
        adapter = new RssRecyclerViewAdapter(context, rssList);
        adapter.setRssNewsClickListener(this);
        layoutManager = new LinearLayoutManager(context);
        if (context == null) {
            Log.d("myLog", "context == null");
        }
        if (rssList != null) {
            if (recyclerView != null) {
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }else {
                Log.d("myLog", "recyclerView == null");
            }
            Log.d("myLog", "setRecyclerAdapter rssList != null");
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
