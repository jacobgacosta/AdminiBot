package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dagger.AppComponent;
import io.dojogeek.adminibot.R;

public class MyPaymentMethodsActivity extends BaseActivity implements MyPaymentMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment_methods);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

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
        return R.layout.activity_my_payment_methods;
    }

    @Override
    protected void closeConnections() {

    }
}
