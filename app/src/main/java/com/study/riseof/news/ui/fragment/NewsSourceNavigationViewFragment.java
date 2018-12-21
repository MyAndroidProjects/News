package com.study.riseof.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.study.riseof.news.R;

import butterknife.BindView;

public class NewsSourceNavigationViewFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private NavigationViewListener navigationViewListener;

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
                navigationViewListener.onMenuItemCloseMainDrawer();
                break;
            case R.id.menu_item_yandex:
                navigationViewListener.onNavigationMenuItemYandex();
                break;
            case R.id.menu_item_meduza:
                navigationViewListener.onNavigationMenuItemMeduza();
                break;
            case R.id.menu_item_ngs:
                navigationViewListener.onNavigationMenuItemNgs();
                break;
            case R.id.menu_item_lenta:
                navigationViewListener.onNavigationMenuItemLenta();
                break;
            case R.id.menu_item_ria:
                navigationViewListener.onNavigationMenuItemRbc();
                break;
        }
        navigationViewListener.onNavigationMenuAnyItem();
        return true;
    }

    public void setNavigationViewListener(NavigationViewListener navigationViewListener) {
        this.navigationViewListener = navigationViewListener;
    }

    public interface NavigationViewListener {
        void onMenuItemCloseMainDrawer();

        void onNavigationMenuItemYandex();

        void onNavigationMenuItemMeduza();

        void onNavigationMenuItemNgs();

        void onNavigationMenuItemLenta();

        void onNavigationMenuItemRbc();

        void onNavigationMenuAnyItem();
    }
}
