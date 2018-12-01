package com.study.riseof.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.study.riseof.news.R;

import butterknife.BindView;

public class NewsSourceNavigationViewFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.navigation_view)
    NavigationView navigationView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_source_navigation_view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.menu_item_back:
                Toast.makeText(getContext(), "menu_item_back", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_yandex:
                Toast.makeText(getContext(), "menu_item_yandex", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_meduza:
                Toast.makeText(getContext(), "menu_item_meduza", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_ngs:
                Toast.makeText(getContext(), "menu_item_ngs", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_lenta:
                Toast.makeText(getContext(), "menu_item_lenta", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_ria:
                Toast.makeText(getContext(), "menu_item_ria", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }
}
