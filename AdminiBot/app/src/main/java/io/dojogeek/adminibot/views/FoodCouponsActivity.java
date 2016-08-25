package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.DatePickerFragment;

public class FoodCouponsActivity extends BaseActivity implements FoodCoupons , View.OnClickListener {

    private static String DATEPICKER_TAG = "datePicker";

    private EditText mNumber;
    private EditText mAmount;
    private EditText mExpirationDate;
    private Button mAddFoodCoupon;

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

        mNumber = (EditText) findViewById(R.id.food_coupon_number);
        mAmount = (EditText) findViewById(R.id.food_coupon_amount);
        mExpirationDate = (EditText) findViewById(R.id.food_coupon_expiration_date);
        mAddFoodCoupon = (Button) findViewById(R.id.add_food_coupond);
    }

    @Override
    protected void addListenersToViews() {
        mExpirationDate.setOnClickListener(this);
        mAddFoodCoupon.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_activity_add_food_coupon);

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_food_coupons;
    }

    @Override
    protected void closeConnections() {

    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        switch (viewId) {
            case R.id.food_coupon_expiration_date:
                DatePickerFragment cuttoffDateFragment = new DatePickerFragment();
                cuttoffDateFragment.setIdWidgetContainerDate(R.id.food_coupon_expiration_date);
                cuttoffDateFragment.show(getSupportFragmentManager(), DATEPICKER_TAG);
                break;
            default:
                Log.v(this.getClass().getName(), "No events for this action");
                break;
        }

    }
}
