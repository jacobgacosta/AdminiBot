package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.presenters.FoodCouponPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.FoodCouponsValidator;

public class FoodCouponsActivity extends BaseActivity implements FoodCoupons , View.OnClickListener {

    public static final int SUCCESS_INSERTION_FOOD_COUPON = R.string.success_insertion_food_coupon;
    public static final int ERROR_INSERTION_FOOD_COUPON = R.string.error_insertion_food_coupon;

    @Inject
    public FoodCouponPresenter mFoodCouponPresenter;
    private EditText mNumber;
    private EditText mAmount;
    private EditText mAlias;

    private Button mAddFoodCoupon;

    @Override
    public void notifySuccessfulInsertion() {
        showMessage(SUCCESS_INSERTION_FOOD_COUPON);
    }

    @Override
    public void notifyErrorInsertion() {
        showMessage(ERROR_INSERTION_FOOD_COUPON);
    }

    @Override
    public void returnToMyPaymentsMethods() {
        LaunchIntents.launchIntentClearTop(this, MainActivity.class);
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

        mNumber = (EditText) findViewById(R.id.food_coupon_number);
        mAmount = (EditText) findViewById(R.id.food_coupon_amount);
        mAlias = (EditText) findViewById(R.id.food_coupon_alias);
        mAddFoodCoupon = (Button) findViewById(R.id.add_food_coupond);
    }

    @Override
    protected void addListenersToViews() {
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
        mFoodCouponPresenter.unnusedView();
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        switch (viewId) {
            case R.id.add_food_coupond:
                processInformation();
                break;
            default:
                Log.v(this.getClass().getName(), "No events for this action");
                break;
        }

    }

    private void processInformation() {

        FoodCouponsValidator foodCouponsValidator = applyFieldValidators();

        boolean isValidFoodCoupon = foodCouponsValidator.validate();

        if (isValidFoodCoupon) {
            processValidFoodCoupon();
        } else {
            showErrors(foodCouponsValidator);
        }
    }

    private FoodCouponsValidator applyFieldValidators() {

        String alias = mAlias.getText().toString();
        String amount = mAmount.getText().toString();
        String foodCouponNumber = mNumber.getText().toString();

        FoodCouponsValidator foodCouponsValidator = new FoodCouponsValidator();
        foodCouponsValidator.setAlias(alias);
        foodCouponsValidator.setAmount(amount);
        foodCouponsValidator.setCode(foodCouponNumber);

        return foodCouponsValidator;

    }

    private void processValidFoodCoupon() {

        OtherPaymentMethodModel otherPaymentMethodModel = new OtherPaymentMethodModel();

        BigDecimal amount = new BigDecimal(mAmount.getText().toString());

        otherPaymentMethodModel.setAvailableCredit(amount);
        otherPaymentMethodModel.setName(mAlias.getText().toString());
        otherPaymentMethodModel.setTypePaymentMethod(TypePaymentMethodEnum.FOOD_COUPONS);
        otherPaymentMethodModel.setReferenceNumber(mNumber.getText().toString());

        submitFoodCoupon(otherPaymentMethodModel);
    }

    private void submitFoodCoupon(OtherPaymentMethodModel otherPaymentMethodModel) {

        mFoodCouponPresenter.createFoodCoupon(otherPaymentMethodModel);
    }

    private void showErrors(FoodCouponsValidator foodCouponsValidator) {

        if (!foodCouponsValidator.isValidAlias()) {
            showError(mAlias, foodCouponsValidator.getErrorMessageAlias());
        } else if (!foodCouponsValidator.isValidCode()) {
            showError(mNumber, foodCouponsValidator.getErrorMessageCode());
        } else if (!foodCouponsValidator.isValidAmount()) {
            showError(mAmount, foodCouponsValidator.getErrorMessageAmount());
        }
    }

    private void showError(EditText editText, int resourceErrorMessage) {
        editText.setError(getString(resourceErrorMessage));
        editText.requestFocus();
    }

    private void showMessage(int resourceMessage) {
        Toast.makeText(this, resourceMessage, Toast.LENGTH_LONG).show();
    }
}
