package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;

public class MyCashActivity extends BaseActivity implements MyCash {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setupComponent(AppComponent appComponent) {
        AdminiBotComponent adminiBotComponent = appComponent.plus(new AdminiBotModule(this));
        adminiBotComponent.inject(this);
    }

    @Override
    protected void loadViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    }

    @Override
    protected void addListenersToViews() {

    }

    @Override
    protected void loadDataView() {

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_my_cash;
    }

    @Override
    protected void closeConnections() {

    }
}
