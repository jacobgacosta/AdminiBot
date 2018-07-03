package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.WelcomeAdapter;

public class WelcomeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static String TAG = "WelcomeActivity";

    private ListView mItemsContainer;

    @Override
    public int getToolbarTitle() {
        return R.string.title_welcome;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                this.launchView(PaymentMethodsActivity.class);
                break;
            case 1:
                break;
            default:
                Log.i(TAG, "No actions for the selected option");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeViews();

        this.setListeners();

        this.prepareView();
    }

    private void initializeViews() {
        mItemsContainer = findViewById(R.id.list_options);
    }

    private void setListeners() {
        mItemsContainer.setOnItemClickListener(this);
    }

    private void prepareView() {
        mItemsContainer.setAdapter(new WelcomeAdapter(this, Arrays.asList(
                new Integer[]{R.drawable.ic_cash, R.string.title_option_1, R.string.msg_description_1},
                new Integer[]{R.drawable.ic_card, R.string.title_option_2, R.string.msg_description_2},
                new Integer[]{R.drawable.ic_savings, R.string.title_option_3, R.string.msg_description_3}
        )));
    }

    private void launchView(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
