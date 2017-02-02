package io.dojogeek.adminibot.views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.MyCreditCardsAdapter;
import io.dojogeek.adminibot.dtos.DtoCreditCardAdapter;
import io.dojogeek.adminibot.exceptions.ArgumentException;
import io.dojogeek.adminibot.exceptions.RecyclerItemException;
import io.dojogeek.adminibot.listeners.RecyclerItemOnClickListener;
import io.dojogeek.adminibot.presenters.MyCreditCardsPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;

public class MyCreditCardsActivity extends BaseActivity implements MyCreditCards, RecyclerItemOnClickListener {

    public static final String CREDIT_CARD_ID = "CREDIT_CARD_ID";
    public static final String TAG = "MyCreditCardsActivity";
    private RecyclerView  mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Inject
    public MyCreditCardsPresenter myCreditCardsPresenter;

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

        setToolBarData();

        showCreditCardsList();

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_my_credit_cards;
    }

    @Override
    protected void closeConnections() {
        myCreditCardsPresenter.unnusedView();
    }

    private void showCreditCardsList() {

        myCreditCardsPresenter.obtainMyCreditCards();

    }

    @Override
    public void listMyCreditCards(List<DtoCreditCardAdapter> creditCards) {

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            mAdapter = new MyCreditCardsAdapter(this, creditCards);
        } catch (RecyclerItemException e) {
            Log.v(TAG, e.getMessage());
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View itemView, long selectedRecordId) {

        Map<String, Serializable> tagValues = new HashMap<>();
        tagValues.put(CREDIT_CARD_ID, selectedRecordId);

        try {
            LaunchIntents.launchIntentWithExtraValues(this, CreditCardDetailActivity.class, tagValues);
        } catch (ArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void setToolBarData() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_with_image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView icon = (ImageView) toolbar.findViewById(R.id.toolbar_icon);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_card);
        icon.setImageDrawable(drawable);

        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.title_activity_my_credit_cards);
    }
}
