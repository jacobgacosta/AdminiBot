package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dagger.App;
import dagger.AppComponent;

public abstract class BaseActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        setupComponent(App.get(this).component());

        int idLayoutActivity = getLayoutActivity();
        super.setContentView(idLayoutActivity);

        prepareViewComponentsAndListeners();

        loadDataView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeConnections();
    }

    private void prepareViewComponentsAndListeners() {

        loadViews();

        addListenersToViews();
    }

    protected abstract void setupComponent(AppComponent appComponent);

    protected abstract void loadViews();

    protected abstract void addListenersToViews();

    protected abstract void loadDataView();

    protected abstract int getLayoutActivity();

    protected abstract void closeConnections();

}
