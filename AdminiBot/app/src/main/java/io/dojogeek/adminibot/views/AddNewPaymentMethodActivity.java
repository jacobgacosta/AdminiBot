package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class AddNewPaymentMethodActivity extends BaseActivity implements
        AddNewPaymentMethod, View.OnClickListener {

    public static final String TAG = "AddNewPaymentMethodAct";

    private Button mAddNewPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

        AdminiBotModule adminiBotModule = new AdminiBotModule(this);
        appComponent.plus(adminiBotModule).inject(this);

    }

    @Override
    protected void loadViews() {
        mAddNewPaymentMethod = (Button) findViewById(R.id.add_payment_method);

    }

    @Override
    protected void addListenersToViews() {
        mAddNewPaymentMethod.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_activity_add_payment_method);

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_add_new_payment_method;
    }

    @Override
    protected void closeConnections() {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.add_payment_method:
                LaunchIntents.launchIntentClearTop(this, AddPaymentMethodActivity.class);
                break;
            default:
                Log.v(TAG, "No events for this view");
                break;
        }

    }
}
