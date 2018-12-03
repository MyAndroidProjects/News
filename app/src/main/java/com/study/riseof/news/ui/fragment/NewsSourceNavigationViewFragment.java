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

    private MenuListener menuListener;

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
                menuListener.onMenuItemCloseMainDrawer();
                return true;
            case R.id.menu_item_yandex:
                menuListener.onMenuItemYandex();
                return true;
            case R.id.menu_item_meduza:
                menuListener.onMenuItemMeduza();
                return true;
            case R.id.menu_item_ngs:
                menuListener.onMenuItemNgs();
                return true;
            case R.id.menu_item_lenta:
                menuListener.onMenuItemLenta();
                return true;
            case R.id.menu_item_ria:
                menuListener.onMenuItemRia();
                return true;
        }
        return true;
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public interface MenuListener {
        void onMenuItemCloseMainDrawer();

        void onMenuItemYandex();

        void onMenuItemMeduza();

        void onMenuItemNgs();

        void onMenuItemLenta();

        void onMenuItemRia();
    }
}
