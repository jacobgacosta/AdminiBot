package io.dojogeek.adminibot.views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
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

public class MyCashActivity extends BaseActivity2 implements MyCash {

    private RecyclerView mRecyclerView;

    private TextView mTotalCash;

    private FloatingActionButton mSpend;

    @Inject
    public MyCashPresenter mMyCashPresenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_payment_method, menu);

        return true;
    }

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

    private void setToolBarData() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_with_image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ImageView icon = (ImageView) toolbar.findViewById(R.id.toolbar_icon);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_cash);
//        icon.setImageDrawable(drawable);

//        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        title.setText(R.string.title_my_cash_activity);
    }
}
