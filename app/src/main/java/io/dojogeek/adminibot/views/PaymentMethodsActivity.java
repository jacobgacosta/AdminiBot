package io.dojogeek.adminibot.views;

import android.app.AlertDialog;
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
import io.dojogeek.adminibot.components.CashDialogFragment;
import io.dojogeek.adminibot.components.FoodCouponDialogFragment;
import io.dojogeek.adminibot.components.IncomeConceptDialog;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.dtos.IncomeDto;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods,
        IncomeConceptDialog.Acceptable, CashDialogFragment.Acceptable, FoodCouponDialogFragment.Acceptable,
        AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = "PaymentMethodsActivity";

    @Inject
    public PaymentMethodsPresenter mPresenter;
    private TextView mTotalIncome;
    private TextView mIncomeConcept;
    private ListView mPaymentMethods;
    private FloatingActionButton mStoreIncome;
    private BigDecimal mTotalCash = new BigDecimal(0.0);
    private BigDecimal mTotalAmount = new BigDecimal(0.0);
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
        mTotalCash = mTotalCash.add(amount);
    }

    @Override
    public void acceptFoodCouponAmount(BigDecimal amount) {
        mTotalFoodCoupons = mTotalFoodCoupons.add(amount);
    }

    @Override
    public void refreshTotalIncome(BigDecimal amount) {
        mTotalAmount = mTotalAmount.add(amount);
        mTotalIncome.setText("$" + mTotalAmount);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch ((TypePaymentMethodEnum) view.getTag()) {
            case CASH:
                new CashDialogFragment().show(getSupportFragmentManager(), "cashDialog");
                break;
            case FOOD_COUPONS:
                new FoodCouponDialogFragment().show(getSupportFragmentManager(), "foodCoupon");
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
                if (mTotalAmount.compareTo(BigDecimal.ZERO) == 0) {
                    this.alertForNonExistentIncome();

                } else {
                    this.saveIncome();
                }
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
        mTotalIncome = findViewById(R.id.text_total_amount);
        mIncomeConcept = findViewById(R.id.text_income_concept);
        mPaymentMethods = findViewById(R.id.list_payment_methods);
        mStoreIncome = findViewById(R.id.button_save_payment_methods);
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

    private void alertForNonExistentIncome() {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_alert, null);
        ((TextView) view.findViewById(R.id.text_alert)).setText(R.string.msg_alert_empty_payment_methods);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setPositiveButton(R.string.msg_accept, null);
        builder.show();
    }

    private void saveIncome() {
        IncomeDto income = new IncomeDto();
        income.setName((String) mIncomeConcept.getText());
        income.setTotalAmount(mTotalAmount);

        DebitCardDto debitCard = (DebitCardDto) getIntent().getSerializableExtra("debit_card");

        if (debitCard != null) {
            income.setDebitCard(debitCard);
        }

        if (mTotalFoodCoupons.compareTo(BigDecimal.ZERO) != 0) {
            income.setTotalFoodCoupons(mTotalFoodCoupons);
        }

        if (mTotalCash.compareTo(BigDecimal.ZERO) != 0) {
            income.setTotalFoodCoupons(mTotalCash);
        }

        mPresenter.registerIncome(income);
    }

}
