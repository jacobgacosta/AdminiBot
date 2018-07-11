package io.dojogeek.adminibot.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import dagger.AdminiBot;
import dagger.AdminiBotModule;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.components.IncomeConceptDialog;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.dtos.IncomeDto;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods,
        IncomeConceptDialog.Acceptable, AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = "PaymentMethodsActivity";

    @Inject
    public PaymentMethodsPresenter mPresenter;
    private TextView mTotalIncome;
    private TextView mIncomeConcept;
    private ListView mPaymentMethods;
    private FloatingActionButton mStoreIncome;
    private DebitCardDto mDebitCard;
    private ArrayList<DebitCardDto> mDebitCardMovements = new ArrayList<>();
    private BigDecimal mTotalCash = new BigDecimal(0.0);
    private BigDecimal mTotalAmount = new BigDecimal(0.0);
    private BigDecimal mTotalDebitCards = new BigDecimal(0.0);
    private BigDecimal mTotalFoodCoupons = new BigDecimal(0.0);

    @Override
    public int getToolbarTitle() {
        return R.string.title_payment_methods;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_payment_methods;
    }

    @Override
    public void acceptIncomeConcept(String value) {
        mIncomeConcept.setText(value);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch ((TypePaymentMethodEnum) view.getTag()) {
            case CASH:
                startActivity(new Intent(this, CashIncomeActivity.class));
                break;
            case FOOD_COUPONS:
                startActivity(new Intent(this, FoodCouponIncomeActivity.class));
                break;
            case DEBIT_CARD:
                startActivity(new Intent(this, DebitCardActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.menu_edit_action, Menu.NONE, R.string.msg_action_bar_edit_action)
                .setIcon(R.drawable.ic_action_edit)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_action:
                Intent intent = new Intent(this, MovementsActivity.class);
                intent.putExtra("income_concept", (String) mIncomeConcept.getText());
                intent.putExtra("cash", mTotalCash);
                intent.putExtra("food_coupons", mTotalFoodCoupons);
                intent.putExtra("debit_card", mDebitCardMovements);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;

        }

        mDebitCard = data.getParcelableExtra("debit_card");

        if (mDebitCard != null) {
            BigDecimal debitCardAmount = new BigDecimal(mDebitCard.getAmount());

            mTotalDebitCards = mTotalDebitCards.add(debitCardAmount);

            mDebitCardMovements.add(mDebitCard);

            refreshTotalIncome(debitCardAmount);
        }

        BigDecimal totalFoodCoupons = (BigDecimal) data.getSerializableExtra("food_coupon");

        if (totalFoodCoupons != null) {
            refreshTotalIncome(totalFoodCoupons);
        }

        BigDecimal totalCash = (BigDecimal) data.getSerializableExtra("cash");

        if (totalCash != null) {
            refreshTotalIncome(totalCash);
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

    private void confirmSavingIncome() {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_confirm_save_income, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setPositiveButton(R.string.msg_accept, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                PaymentMethodsActivity.this.saveIncome();
            }
        });
        builder.setNegativeButton(R.string.msg_cancel, null);
        builder.show();
    }

    private void saveIncome() {
        IncomeDto income = new IncomeDto();
        income.setName((String) mIncomeConcept.getText());
        income.setTotalAmount(mTotalAmount);

        if (mDebitCard != null) {
            income.setDebitCard(mDebitCard);
        }

        if (mTotalFoodCoupons.compareTo(BigDecimal.ZERO) != 0) {
            income.setTotalFoodCoupons(mTotalFoodCoupons);
        }

        if (mTotalCash.compareTo(BigDecimal.ZERO) != 0) {
            income.setTotalFoodCoupons(mTotalCash);
        }

        mPresenter.registerIncome(income);
    }

    private void refreshTotalIncome(BigDecimal amount) {
        mTotalAmount = mTotalAmount.add(amount);

        mTotalIncome.setText("$" + mTotalAmount);
    }

}
