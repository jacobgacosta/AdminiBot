package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SimpleItemAdapter;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.presenters.MyFoodCouponsPresenter;

public class MyFoodCouponsActivity extends BaseActivity implements MyFoodCoupons {

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
        setTitle(R.string.title_my_food_coupons_activity);

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
}
