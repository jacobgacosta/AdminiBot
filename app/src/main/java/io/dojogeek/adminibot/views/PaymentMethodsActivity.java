package io.dojogeek.adminibot.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import io.dojogeek.adminibot.components.CashDialogFragment;
import io.dojogeek.adminibot.components.FoodCouponDialogFragment;
import io.dojogeek.adminibot.components.IncomeConceptDialog;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.dtos.IncomeDto;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

public class PaymentMethodsActivity extends BaseActivity implements PaymentMethods,
        CashDialogFragment.Acceptable, FoodCouponDialogFragment.Acceptable,
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
                startActivityForResult(intent, 1);
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
                    this.confirmSavingIncome();
                }
                break;
        }
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

        mDebitCard = (DebitCardDto) data.getSerializableExtra("debit_card");

        BigDecimal amount = new BigDecimal(mDebitCard.getAmount());

        mTotalDebitCards = mTotalDebitCards.add(amount);

        mDebitCardMovements.add(mDebitCard);

        refreshTotalIncome(amount);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AdminiBotAlertDialog);
        builder.setView(view);
        builder.setPositiveButton(R.string.msg_accept, null);
        builder.show();
    }

    private void confirmSavingIncome() {
        Resources res = getResources();

        View view = this.getLayoutInflater().inflate(R.layout.dialog_confirm_save_income, null);
        ((TextView) view.findViewById(R.id.total_amount_cash))
                .setText(res.getString(R.string.msg_total_amount_cash, mTotalCash));
        ((TextView) view.findViewById(R.id.total_food_coupons))
                .setText(res.getString(R.string.msg_total_amount_cash, mTotalFoodCoupons));
        ((TextView) view.findViewById(R.id.total_amount_debit_cards))
                .setText(res.getString(R.string.msg_total_amount_debit_cards, mTotalDebitCards));
        ((TextView) view.findViewById(R.id.total_amount_income))
                .setText(res.getString(R.string.msg_total_income, mTotalAmount));

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

}
