package io.dojogeek.adminibot.listeners.impl;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import io.dojogeek.adminibot.R;

public class NavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    public NavigationViewListener(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        int idSelectedItem = menuItem.getItemId();

        handlerItemStatusChecked(menuItem);

        mDrawerLayout.closeDrawers();

        switch (idSelectedItem) {
            case R.id.home:
                break;
            case R.id.my_cards:
                break;
            case R.id.my_savings:
                break;
            case R.id.accounts:
                break;
            case R.id.summary:
                break;
        }

        return false;
    }

    private void handlerItemStatusChecked(MenuItem menuItem) {
        if(menuItem.isChecked()) {
            menuItem.setChecked(false);
        } else {
            menuItem.setChecked(true);
        }
    }
}
