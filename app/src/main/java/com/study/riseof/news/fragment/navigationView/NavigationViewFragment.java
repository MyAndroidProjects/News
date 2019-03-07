package com.study.riseof.news.fragment.navigationView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.study.riseof.news.R;
import com.study.riseof.news.fragment.BaseFragment;

import butterknife.BindView;

public class NavigationViewFragment extends BaseFragment
        implements NavigationView.OnNavigationItemSelectedListener,
        NavigationViewFragmentContract.View {
    @BindView(R.id.widget_navigation_view)
    NavigationView widgetNavigationView;

    private NavigationViewFragmentContract.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation_view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("myLog", " NavigationView onCreate ");
    }

    @Override
    public void onStart() {
        super.onStart();
        widgetNavigationView.setNavigationItemSelectedListener(this);
        Log.d("myLog", " NavigationView onStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.setView(this);
            Log.d("myLog", " NavigationView onResume ");
        }
        else{
            Log.d("myLog", " NavigationView onResume presenter == null ");
        }
        //todo
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.setView(null);
            Log.d("myLog", " NavigationView onPause ");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (presenter != null) {
            int id = menuItem.getItemId();
            if (id == R.id.menu_item_back) {
                presenter.onMenuItemCloseMainDrawer();
                return true;
            }
            switch (id) {
                case R.id.menu_item_ngs:
                    presenter.onNavigationMenuItemNgs();
                    break;
                case R.id.menu_item_meduza:
                    presenter.onNavigationMenuItemMeduza();
                    break;
                case R.id.menu_item_yandex:
                    presenter.onNavigationMenuItemYandex();
                    break;
                case R.id.menu_item_lenta:
                    presenter.onNavigationMenuItemLenta();
                    break;
                case R.id.menu_item_rbc:
                    presenter.onNavigationMenuItemRbc();
                    break;
            }
            presenter.onNavigationMenuSelectAnyItem();
        } else {
            Log.d("myLog", " onNavigationItemSelected presenter ==null ");
        }
        return true;
    }

    @Override
    public void setPresenter() {
        presenter = NavigationViewFragmentPresenter.getInstance();
    }

    @Override
    public void nullifyPresenter() {
        presenter = null;
    }

}