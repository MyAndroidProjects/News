package com.study.riseof.news.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.riseof.news.NewsSource;
import com.study.riseof.news.R;
import com.study.riseof.news.model.xml.Item;
import com.study.riseof.news.presenter.RssFragmentContract;
import com.study.riseof.news.presenter.RssFragmentNavigator;
import com.study.riseof.news.presenter.RssFragmentPresenter;
import com.study.riseof.news.ui.adapter.RssRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class RssFragment extends BaseFragment implements
        RssRecyclerViewAdapter.RssNewsClickListener, RssFragmentContract.View {

    @BindView(R.id.recycler_view_rss)
    RecyclerView recyclerView;


    private RssFragmentContract.Presenter presenter;
    private RssFragmentContract.Navigator navigator;

    private ArrayList<Item> rssList;
    private int sourceNameId = NewsSource.EMPTY.getNameId();

    private final String arrayListName = "rssList";
    private final String sourceNameIdVarName = "sourceNameId";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rss;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getBundleArgs();
        if (savedInstanceState != null) {
            rssList = savedInstanceState.getParcelableArrayList(arrayListName);
            sourceNameId = savedInstanceState.getInt(sourceNameIdVarName, NewsSource.EMPTY.getNameId());
        }
        setRecyclerAdapter();
        return view;
    }

    protected void getBundleArgs() {
        Bundle args = getArguments();
        if (args != null) {
            rssList = args.getParcelableArrayList(arrayListName);
            sourceNameId = args.getInt(sourceNameIdVarName, NewsSource.EMPTY.getNameId());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(arrayListName, rssList);
        outState.putInt(sourceNameIdVarName, sourceNameId);
    }

    public void setRecyclerAdapter() {
        RssRecyclerViewAdapter adapter;
        LinearLayoutManager layoutManager;
        Context context = getContext();
        if (context != null) {
            adapter = new RssRecyclerViewAdapter(context, rssList);
            adapter.setRssNewsClickListener(this);
            layoutManager = new LinearLayoutManager(context);
            if (rssList != null) {
                if (recyclerView != null) {
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }


    @Override
    public void rssNewsClick(int position, String newsUrl) {
        if (presenter != null) {
            presenter.rssNewsClick(position, newsUrl, sourceNameId);
        }
    }

    @Override
    public void setPresenterAndNavigator() {
        navigator = RssFragmentNavigator.getInstance();
        presenter = RssFragmentPresenter.getInstance(navigator);
    }

    @Override
    public void nullifyPresenterAndNavigator() {
        navigator = null;
        presenter = null;
    }
}
