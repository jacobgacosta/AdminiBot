package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SimpleItemAdapter;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.presenters.MyFoodCouponsPresenter;

public class MyFoodCouponsActivity extends BaseActivity2 implements MyFoodCoupons {

    private RecyclerView mRecyclerView;

    @Inject
    public MyFoodCouponsPresenter mMyFoodCouponsPresenter;

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
        mRecyclerView = (RecyclerView) findViewById(R.id.my_food_coupons_recycler_view);
    }

    @Override
    protected void addListenersToViews() {

    }

    @Override
    protected void loadDataView() {

        setToolBarData();

        requestFoodCoupons();
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_my_food_coupons;
    }

    @Override
    protected void closeConnections() {

    }

    @Override
    public void listFoodCoupons(List<DtoSimpleAdapter> dtoSimpleAdapterList) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        SimpleItemAdapter simpleItemAdapter = new SimpleItemAdapter(this, dtoSimpleAdapterList);
        mRecyclerView.setAdapter(simpleItemAdapter);

    }

    private void requestFoodCoupons() {
        mMyFoodCouponsPresenter.obtainFoodCoupons();
    }

    private void setToolBarData() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_with_image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        ImageView icon = (ImageView) toolbar.findViewById(R.id.toolbar_icon);
//        Drawable drawable = getResources().getDrawable(R.drawable.ic_food_coupon);
//        icon.setImageDrawable(drawable);

//        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        title.setText(R.string.title_my_food_coupons_activity);
    }
}
