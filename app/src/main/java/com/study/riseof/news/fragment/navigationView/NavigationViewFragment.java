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
    }

    @Override
    public void onStart() {
        super.onStart();
        widgetNavigationView.setNavigationItemSelectedListener(this);
    }

/*    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.setView(this);
        }
    }*/

/*    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.setView(null);
        }
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (presenter != null) {
            int id = menuItem.getItemId();
            if (id == R.id.menu_item_back) {
                presenter.menuItemBackSelected();
                return true;
            }
            switch (id) {
                case R.id.menu_item_ngs:
                    presenter.menuItemNgsSelected();
                    break;
                case R.id.menu_item_meduza:
                    presenter.menuItemMeduzaSelected();
                    break;
                case R.id.menu_item_washingtonpost:
                    presenter.menuItemWashingtonpostSelected();
                    break;
                case R.id.menu_item_lenta:
                    presenter.menuItemLentaSelected();
                    break;
                case R.id.menu_item_rt:
                    presenter.menuItemRtSelected();
                    break;
            }
            presenter.menuItemSelectionIsCompleted();
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