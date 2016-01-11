package io.dojogeek.adminibot.listeners;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActionBarDrawerToggleListener extends ActionBarDrawerToggle {


    public ActionBarDrawerToggleListener(Activity activity, DrawerLayout drawerLayout,
                                         Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {

        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
        super.onDrawerClosed(drawerView);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

        super.onDrawerOpened(drawerView);
    }
}
