package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.listeners.ActionBarDrawerToggleListener;
import io.dojogeek.adminibot.listeners.NavigationViewListener;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView mCounter;
    private View mNotificationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_global, menu);

        MenuItem item = menu.findItem(R.id.action_notifications);

        MenuItemCompat.setActionView(item, R.layout.action_bar_notification_icon);

        mNotificationView = MenuItemCompat.getActionView(item);

        loadNotifications();

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showInboxContent();

    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }


    @Override
    protected void loadViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void addListenersToViews() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationViewListener(mDrawerLayout));

        ActionBarDrawerToggleListener actionBarDrawerToggleListener = new ActionBarDrawerToggleListener(this,
                mDrawerLayout, mToolbar, R.string.navigation_open_message,
                R.string.navigation_close_message);

        mDrawerLayout.setDrawerListener(actionBarDrawerToggleListener);

        actionBarDrawerToggleListener.syncState();

    }

    @Override
    protected void loadDataView() {

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.main;
    }

    private void showInboxContent() {

        InboxFragment inboxFragment = new InboxFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, inboxFragment);
        fragmentTransaction.commit();

    }

    private void loadNotifications() {

        // TODO: load views to set number notifications

        mNotificationView.findViewById(R.id.number_notifications).setVisibility(View.GONE);

    }

}
