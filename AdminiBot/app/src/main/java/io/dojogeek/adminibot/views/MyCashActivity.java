package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SimpleItemAdapter;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.presenters.MyCashPresenter;

public class MyCashActivity extends BaseActivity implements MyCash {

    private RecyclerView mRecyclerView;

    private TextView mTotalCash;

    @Inject
    public MyCashPresenter mMyCashPresenter;

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
        mTotalCash = (TextView) findViewById(R.id.total_cash);

    }

    @Override
    protected void addListenersToViews() {

    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_my_cash_activity);

        loadMyCash();
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_my_cash;
    }

    @Override
    protected void closeConnections() {
        mMyCashPresenter.unnusedView();
    }

    private void loadMyCash() {
        mMyCashPresenter.obtainCash();
    }

    @Override
    public void listMyCash(List<DtoSimpleAdapter> cashList) {

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        SimpleItemAdapter simpleItemAdapter = new SimpleItemAdapter(this, cashList);

        mRecyclerView.setAdapter(simpleItemAdapter);

    }

    @Override
    public void showTotalCash(BigDecimal total) {
        mTotalCash.setText(total.toString());
    }
}
