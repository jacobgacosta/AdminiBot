package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;

public class CardCreationActivity extends BaseActivity implements CardCreation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_creation);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        AdminiBotComponent adminiBotComponent = appComponent.plus(new AdminiBotModule(this));
        adminiBotComponent.inject(this);
    }

    @Override
    protected void loadViews() {

    }

    @Override
    protected void addListenersToViews() {

    }

    @Override
    protected void loadDataView() {

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_card_creation;
    }

    @Override
    protected void closeConnections() {

    }
}
