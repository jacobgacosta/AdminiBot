package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.BankEnum;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.presenters.CheckPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.CheckValidator;

public class CheckActivity extends BaseActivity implements Check, View.OnClickListener {

    public static final int SUCCESS_INSERTION_CHECK = R.string.success_insertion_check;
    public static final int ERROR_INSERTION_CHECK = R.string.error_insertion_check;

    private EditText mAlias;
    private EditText mCheckNumber;
    private EditText mAmount;
    private Button mAddCheck;

    @Inject
    public CheckPresenter mCheckPresenter;

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

        mAlias = (EditText) findViewById(R.id.check_alias);
        mCheckNumber = (EditText) findViewById(R.id.check_number);
        mAmount = (EditText) findViewById(R.id.amount);
        mAddCheck = (Button) findViewById(R.id.add_check);

    }

    @Override
    protected void addListenersToViews() {
        mAddCheck.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

        setTitle(R.string.title_activity_add_check);

    }

    @Override
    protected int getLayoutActivity() {

        return R.layout.activity_check;
    }

    @Override
    protected void closeConnections() {
        mCheckPresenter.unnusedView();
    }

    private Map<String, Integer> createItemsMapFromBankEnum(BankEnum[] banks) {

        Map<String, Integer> banksMap = new HashMap<>();

        banksMap.put("ic_amex", R.string.banks_amex);
        banksMap.put("ic_visa", R.string.banks_banamex);
        banksMap.put("ic_mastercard", R.string.banks_bancoopel);

        return banksMap;
    }

    @Override
    public void onClick(View view) {

        int idView = view.getId();

        switch (idView) {
            case R.id.add_check:
                processInformation();
                break;
            default:
                Log.v(this.getClass().getName(), "No operations for this event");
                break;
        }

    }

    private void processInformation() {

        CheckValidator checkValidator = applyFieldValidators();

        boolean isValidCheck = checkValidator.validate();

        if (isValidCheck) {
            processValidCheck();
        } else {
            showErrors(checkValidator);
        }

    }

    private CheckValidator applyFieldValidators() {

        String checkNumber = mCheckNumber.getText().toString();
        String amount = mAmount.getText().toString();
        String alias = mAlias.getText().toString();

        CheckValidator checkValidator = new CheckValidator();
        checkValidator.setAmount(amount);
        checkValidator.setCheckNumber(checkNumber);
        checkValidator.setAlias(alias);

        return checkValidator;
    }

    private void processValidCheck() {

        PaymentMethodModel paymentMethod = new PaymentMethodModel();
//        paymentMethod.setName(mAlias.getText().toString());
//        paymentMethod.setReferenceNumber(mCheckNumber.getText().toString());
//        paymentMethod.setAvailableCredit(new BigDecimal(mAmount.getText().toString()));

        mCheckPresenter.createCheck(paymentMethod);
    }

    private void showErrors(CheckValidator checkValidator) {

        if (!checkValidator.isValidAlias()) {
            showError(mAlias, checkValidator.getErrorMessageAlias());
        } else if (!checkValidator.isValidCheckNumber()) {
            showError(mCheckNumber, checkValidator.getErrorMessageCheckNumber());
        } else if (!checkValidator.isValidAmount()) {
            showError(mAmount, checkValidator.getErrorMessageAmount());
        }

    }

    private void showError(EditText editText, int resourceErrorMessage) {
        editText.setError(getString(resourceErrorMessage));
        editText.requestFocus();
    }

    @Override
    public void notifySuccessfulInsertion() {
        showMessage(CheckActivity.SUCCESS_INSERTION_CHECK);
    }

    @Override
    public void notifyErrorInsertion() {
        showMessage(CheckActivity.ERROR_INSERTION_CHECK);
    }

    @Override
    public void returnToMyPaymentsMethods() {

        LaunchIntents.launchIntentClearTop(this, MainActivity.class);

    }

    private void showMessage(int resourceMessage) {
        Toast.makeText(this, resourceMessage, Toast.LENGTH_LONG).show();
    }
}
