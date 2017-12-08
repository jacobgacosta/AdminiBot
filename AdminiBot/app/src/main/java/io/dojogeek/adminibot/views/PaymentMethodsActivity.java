package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;

import dagger.AdminiBot;
import dagger.AdminiBotModule;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.components.AlertDialogFragment;
import io.dojogeek.adminibot.components.CashDialogFragment;
import io.dojogeek.adminibot.components.FoodCouponDialog;
import io.dojogeek.adminibot.components.IncomeConceptDialog;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods,
        IncomeConceptDialog.Acceptable, CashDialogFragment.Acceptable, FoodCouponDialog.Acceptable,
        AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = "PaymentMethodsActivity";

    @Inject
    public PaymentMethodsPresenter mPresenter;
    private TextView mTotalIncome;
    private TextView mIncomeConcept;
    private ListView mPaymentMethods;
    private FloatingActionButton mStoreIncome;
    private BigDecimal mTotalAmount = new BigDecimal(0.0);
    private BigDecimal mTotalCash = new BigDecimal(0.0);
    private BigDecimal mTotalFoodCoupons = new BigDecimal(0.0);

    @Override
    public int getToolbarTitle() {
        return R.string.title_payment_methods;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_payment_methods;
    }

    @Override
    public void acceptIncomeConcept(String value) {
        mIncomeConcept.setText(value);
    }

    @Override
    public void acceptCashAmount(BigDecimal amount) {
        mTotalAmount = mTotalAmount.add(mTotalCash.add(amount));

        mTotalIncome.setText("$" + mTotalAmount);
    }

    @Override
    public void acceptFoodCouponAmount(BigDecimal amount) {
        mTotalAmount = mTotalAmount.add(mTotalFoodCoupons.add(amount));

        mTotalIncome.setText("$" + mTotalAmount);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch ((TypePaymentMethodEnum) view.getTag()) {
            case CASH:
                new CashDialogFragment().show(getSupportFragmentManager(), "cashDialog");
                break;
            case FOOD_COUPONS:
                new FoodCouponDialog().show(getSupportFragmentManager(), "foodCoupon");
                break;
            case DEBIT_CARD:
                Intent intent = new Intent(this, DebitCardActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save_payment_methods:
                new AlertDialogFragment().setText(R.string.msg_alert_empty_payment_methods)
                        .show(getSupportFragmentManager(), "alertDialog");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setupInjector();

        this.initializeViews();

        this.setListeners();

        this.prepareView();

        this.showIncomeConceptInput();
    }

    private void setListeners() {
        mPaymentMethods.setOnItemClickListener(this);
        mStoreIncome.setOnClickListener(this);
    }

    private void setupInjector() {
        ((AdminiBot) getApplication()).getComponent().plus(new AdminiBotModule(this)).inject(this);
    }

    private void initializeViews() {
        mTotalIncome = (TextView) findViewById(R.id.text_total_amount);
        mIncomeConcept = (TextView) findViewById(R.id.text_income_concept);
        mPaymentMethods = (ListView) findViewById(R.id.list_payment_methods);
        mStoreIncome = (FloatingActionButton) findViewById(R.id.button_save_payment_methods);
    }

    private void prepareView() {
        PaymentMethodAdapter adapter = new PaymentMethodAdapter(this,
                Arrays.asList(TypePaymentMethodEnum.FOOD_COUPONS, TypePaymentMethodEnum.CASH,
                        TypePaymentMethodEnum.DEBIT_CARD));

        mPaymentMethods.setAdapter(adapter);
    }

    private void showIncomeConceptInput() {
        DialogFragment dialogFragment = new IncomeConceptDialog();
        dialogFragment.show(getSupportFragmentManager(), "incomeConcept");
        dialogFragment.setCancelable(false);
    }

}
