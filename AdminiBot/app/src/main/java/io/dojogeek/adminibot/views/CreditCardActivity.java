package io.dojogeek.adminibot.views;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.DatePickerFragment;
import io.dojogeek.adminibot.components.SpinnerWithInternalImage;
import io.dojogeek.adminibot.enums.BankEnum;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.models.CreditCardModel;
import io.dojogeek.adminibot.presenters.CreditCardPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.CreditCardValidator;

public class CreditCardActivity extends BaseActivity implements CreditCard, View.OnClickListener {

    public static final int SUCCESS_INSERTION_CREDIT_CARD = R.string.success_insertion_cc;
    public static final int ERROR_INSERTION_CREDIT_CARD = R.string.error_insertion_cc;
    private static final String VISA = "VISA";
    private static final String MASTER_CARD = "MASTER_CARD";
    private static final String AMEX = "AMERICAN_EXPRESS";
    private EditText mCardName;
    private EditText mCardNumber;
    private EditText mCurrentBalance;
    private EditText mPayDayLimit;
    private EditText mCreditLimit;
    private EditText mCuttoffDate;
    private SpinnerWithInternalImage mSpinnerWithInternalImage;
    private RadioButton mOption;
    private RadioGroup mCardBrands;
    private static String DATEPICKER_TAG = "datePicker";
    private Button mCreateCreditCard;

    @Inject
    public CreditCardPresenter mCreditCardPresenter;

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
            case R.id.create_credit_card:
                processInformation();
                break;
            default:
                Log.v(this.getClass().getName(), "No operations for this event");
                break;
        }
    }

    @Override
    public void notifySuccessfulInsertion() {
        showMessage(CreditCardActivity.SUCCESS_INSERTION_CREDIT_CARD);
    }

    @Override
    public void notifyErrorInsertion() {
        showMessage(CreditCardActivity.ERROR_INSERTION_CREDIT_CARD);
    }

    @Override
    public void fillBankItemsSpinner(Map<Long, Map<String, Integer>> items) {

        mSpinnerWithInternalImage.createSpinner(R.string.add_card_banks_hint, items);
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
        mCardName = (EditText) findViewById(R.id.card_name);
        mCardNumber = (EditText) findViewById(R.id.card_number);
        mSpinnerWithInternalImage = (SpinnerWithInternalImage) findViewById(R.id.banks);
        mCreditLimit = (EditText) findViewById(R.id.credit_limit);
        mCardBrands = (RadioGroup) findViewById(R.id.cards_brands);
        mCuttoffDate = (EditText) findViewById(R.id.cuttoff_date);
        mCurrentBalance = (EditText) findViewById(R.id.current_balance);
        mPayDayLimit = (EditText) findViewById(R.id.pay_day_limit);
        mCreateCreditCard = (Button) findViewById(R.id.create_credit_card);
    }

    @Override
    protected void addListenersToViews() {
        mCuttoffDate.setOnClickListener(this);
        mPayDayLimit.setOnClickListener(this);
        mCreateCreditCard.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_activity_add_card);

        mCreditCardPresenter.loadBanks();

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_credit_card;
    }

    @Override
    protected void closeConnections() {
        mCreditCardPresenter.unnusedView();
    }

    private void processInformation() {

        CreditCardValidator creditCardValidator = applyFieldValidators();

        boolean isValidCreditCard = creditCardValidator.validate();

        if (isValidCreditCard) {
            processValidCreditCard();
        } else {
            showErrors(creditCardValidator);
        }

    }

    private CreditCardValidator applyFieldValidators() {

        String cardName = mCardName.getText().toString();
        String cardNumber = mCardNumber.getText().toString();
        String cardBrand = getRadioButtonTextFromId(mCardBrands.getCheckedRadioButtonId());
        Long bankId = (Long) mSpinnerWithInternalImage.getSelectedItem();

        String bank = "";

        if (bankId != null) {
            bank = bankId.toString();
        }

        String creditLimit = mCreditLimit.getText().toString();
        String currentBalance = mCurrentBalance.getText().toString();
        String cuttoffDate = mCuttoffDate.getText().toString();
        String payDayLimit = mPayDayLimit.getText().toString();

        CreditCardValidator creditCardValidator = new CreditCardValidator();
        creditCardValidator.setCardName(cardName);
        creditCardValidator.setCardNumber(cardNumber);
        creditCardValidator.setCreditCardBrand(cardBrand);
        creditCardValidator.setBank(bank);
        creditCardValidator.setCreditLimit(creditLimit);
        creditCardValidator.setCurrentBalance(currentBalance);
        creditCardValidator.setCuttoffDate(cuttoffDate);
        creditCardValidator.setPaydayLimit(payDayLimit);

        return creditCardValidator;

    }

    private String getRadioButtonTextFromId(int radioButtonId) {

        String brandMark = null;

        switch (radioButtonId) {
            case R.id.visa:
                brandMark = VISA;
                break;
            case R.id.mastercard:
                brandMark = MASTER_CARD;
                break;
            case R.id.amex:
                brandMark = AMEX;
                break;
            default:
                Log.v(this.getClass().getName(), "No operations for this event");
                break;
        }
        return brandMark;
    }

    private void processValidCreditCard() {

        BankCardModel bankCardModel = buildBankCardModel();

        CardDetailModel cardDetailModel = buildCardDetailModel();

        CreditCardModel creditCardModel = new CreditCardModel();
        creditCardModel.setBankCardModel(bankCardModel);
        creditCardModel.setCardDetailModel(cardDetailModel);

        submitCreditCard(creditCardModel);
    }

    private void submitCreditCard(CreditCardModel creditCardModel) {

        mCreditCardPresenter.createCreditCard(creditCardModel);

    }

    private CardDetailModel buildCardDetailModel() {

        CardDetailModel cardDetailModel = new CardDetailModel();
        cardDetailModel.setCuttoffDate(mCuttoffDate.getText().toString());
        cardDetailModel.setPayDayLimit(mPayDayLimit.getText().toString());
        cardDetailModel.setCurrentBalance(new Double(mCurrentBalance.getText().toString()));
        cardDetailModel.setCreditLimit(new Double(mCreditLimit.getText().toString()));

        return cardDetailModel;
    }

    private BankCardModel buildBankCardModel() {

        BankCardModel bankCardModel = new BankCardModel();
        bankCardModel.setName(mCardName.getText().toString());
        bankCardModel.setNumber(mCardNumber.getText().toString());
        bankCardModel.setBrand(getRadioButtonTextFromId(mCardBrands.getCheckedRadioButtonId()));
        bankCardModel.setBankId((Long) mSpinnerWithInternalImage.getSelectedItem());
        bankCardModel.setAvailableCredit(calculateAvailableCredit(mCurrentBalance.getText().toString(),
                mCreditLimit.getText().toString()));
        bankCardModel.setCardType(CardTypeEnum.CREDIT_CARD);

        return bankCardModel;
    }

    private double calculateAvailableCredit(String currentBalance, String creditLimit) {
        return new Double(creditLimit) - new Double(currentBalance);
    }

    private void showErrors(CreditCardValidator creditCardValidator) {
        if (!creditCardValidator.isValidCardName()) {
            showError(mCardName, creditCardValidator.getCardNameError());
        } else if (!creditCardValidator.isValidCardNumber()) {
            showError(mCardNumber, creditCardValidator.getCardNumberError());
        } else if (!creditCardValidator.isValidCardBrand()) {
            mCardBrands.requestFocus();
            Toast.makeText(this, R.string.error_wrong_brand_card, Toast.LENGTH_LONG).show();
        } else if (!creditCardValidator.isValidCardBank()) {
            mSpinnerWithInternalImage.requestFocus();
            Toast.makeText(this, R.string.error_wrong_bank, Toast.LENGTH_LONG).show();
        } else if (!creditCardValidator.isValidCreditLimit()) {
            showError(mCreditLimit, creditCardValidator.getCreditLimitError());
        } else if (!creditCardValidator.isValidCurrentBalance()) {
            showError(mCurrentBalance, creditCardValidator.getCurrentBalanceError());
        } else if (!creditCardValidator.isValidCuttoffDate()) {
            showError(mCuttoffDate, creditCardValidator.getCuttoffDateError());
        } else if (!creditCardValidator.isValidPayDayLimit()) {
            showError(mPayDayLimit, creditCardValidator.getPayDayLimitError());
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
