package io.dojogeek.adminibot.views;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.DatePickerFragment;
import io.dojogeek.adminibot.components.SpinnerWithInternalImage;
import io.dojogeek.adminibot.enums.BankEnum;

public class CreditCardActivity extends BaseActivity implements CreditCard, View.OnClickListener {

    private EditText mCardName;
    private EditText mCardNumber;
    private EditText mCurrentBalance;
    private EditText mPayDayLimit;
    private EditText mCreditLimit;
    private EditText mCuttoffDate;
    private SpinnerWithInternalImage mSpinnerWithInternalImage;
    private RadioButton mVisa;
    private RadioButton mMasterCard;
    private RadioButton mAmex;
    private LinearLayout mCardsBrands;
    private static String DATEPICKER_TAG = "datePicker";
    private Button mCreateCreditCard;

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
        mCardName = (EditText) findViewById(R.id.card_name);
        mCardNumber = (EditText) findViewById(R.id.card_number);
        mSpinnerWithInternalImage = (SpinnerWithInternalImage) findViewById(R.id.banks);
        mCreditLimit = (EditText) findViewById(R.id.credit_limit);
        mCardsBrands = (LinearLayout) findViewById(R.id.cards_brands);
        mVisa = (RadioButton) findViewById(R.id.visa);
        mMasterCard = (RadioButton) findViewById(R.id.mastercard);
        mAmex = (RadioButton) findViewById(R.id.amex);
        mCuttoffDate = (EditText) findViewById(R.id.cuttoff_date);
        mCurrentBalance = (EditText) findViewById(R.id.current_balance);
        mPayDayLimit = (EditText) findViewById(R.id.pay_day_limit);
        mCreateCreditCard = (Button) findViewById(R.id.create_credit_card);
    }

    @Override
    protected void addListenersToViews() {
        mCuttoffDate.setOnClickListener(this);
        mPayDayLimit.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_activity_add_card);

        Map<String, Integer> map = createItemsMapFromBankEnum(BankEnum.values());

        mSpinnerWithInternalImage.createSpinner(R.string.add_card_banks_hint, map);

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_credit_card;
    }

    @Override
    protected void closeConnections() {

    }

    private Map<String, Integer> createItemsMapFromBankEnum(BankEnum[] banks) {

        Map<String, Integer> banksMap = new HashMap<>();

        banksMap.put("ic_amex", R.string.banks_amex);
        banksMap.put("ic_visa", R.string.banks_banamex);
        banksMap.put("ic_mastercard", R.string.banks_bancoopel);
//        for (BankEnum bank : banks) {
//
//        }

        return banksMap;
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        switch (viewId) {
            case R.id.cuttoff_date:
                DatePickerFragment cuttoffDateFragment = new DatePickerFragment();
                cuttoffDateFragment.setIdWidgetContainerDate(R.id.cuttoff_date);
                cuttoffDateFragment.show(getSupportFragmentManager(), DATEPICKER_TAG);
                break;
            case R.id.pay_day_limit:
                DatePickerFragment payDayLimitFragment = new DatePickerFragment();
                payDayLimitFragment.setIdWidgetContainerDate(R.id.pay_day_limit);
                payDayLimitFragment.show(getSupportFragmentManager(), DATEPICKER_TAG);
                break;
        }

    }
}
