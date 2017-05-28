package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.WelcomeAdapter;

public class WelcomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static String TAG = "WelcomeActivity";
    private ListView mItemsContainer;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                this.launchView(PaymentMethodsActivity.class);
                break;
            case 1:
                this.launchView(CreditCardActivity.class);
                break;
            default:
                Log.i(TAG, "No actions for the selected option");
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        this.instantiateViews();

        this.setListeners();

        this.loadViewData();

    }

    private void instantiateViews() {

        mItemsContainer = (ListView) findViewById(R.id.list_options);

    }

    private void setListeners() {

        mItemsContainer.setOnItemClickListener(this);

    }

    private void loadViewData() {

        setTitle(R.string.welcome_title);

        List<Integer[]> items = new ArrayList<>();
        items.add(new Integer[]{R.drawable.ic_cash, R.string.title1, R.string.description1});
        items.add(new Integer[]{R.drawable.ic_cash, R.string.title2, R.string.description2});

        WelcomeAdapter adapter = new WelcomeAdapter(this, items);

        mItemsContainer.setAdapter(adapter);

    }

    private void launchView(Class activity) {

        Intent intent = new Intent(this, activity);
        startActivity(intent);

    }

}
