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
import io.dojogeek.adminibot.presenters.CashPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.CashValidator;

public class CashActivity extends BaseActivity implements Cash, View.OnClickListener {

    public static final int SUCCESS_INSERTION_CASH = R.string.success_insertion_cash;
    public static final int ERROR_INSERTION_CASH = R.string.error_insertion_cash;
    private static final String REFERENCE_NUMBER = "N/A";

    private EditText mCashAlias;
    private EditText mCashAmount;
    private Button mAddCash;

    @Inject
    public CashPresenter mCashPresenter;

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
        mCashAlias = (EditText) findViewById(R.id.cash_alias);
        mCashAmount = (EditText) findViewById(R.id.cash_amount);
        mAddCash = (Button) findViewById(R.id.add_cash);
    }

    @Override
    protected void addListenersToViews() {
        mAddCash.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {
        setTitle(R.string.title_activity_add_cash);
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_cash;
    }

    @Override
    protected void closeConnections() {
        mCashPresenter.unnusedView();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.add_cash:
                processInformation();
                break;
            default:
                Log.v(this.getClass().getName(), "No operations for this event");
                break;
        }
    }

    private void processInformation() {

        CashValidator cashValidator = applyFieldValidators();

        boolean isValidCash = cashValidator.validate();

        if (isValidCash) {
            processValidCash();
        } else {
            showErrors(cashValidator);
        }
    }

    private CashValidator applyFieldValidators() {

        String alias = mCashAlias.getText().toString();
        String amount = mCashAmount.getText().toString();

        CashValidator cashValidator = new CashValidator();
        cashValidator.setConcept(alias);
        cashValidator.setAmount(amount);

        return cashValidator;

    }

    private void showErrors(CashValidator cashValidator) {

        if (!cashValidator.isValidConcept()) {
            showError(mCashAlias, cashValidator.getErrorMessageConcept());
        } else if (!cashValidator.isValidAmount()) {
            showError(mCashAmount, cashValidator.getErrorMessageAmount());
        }

    }

    private void showError(EditText editText, int resourceErrorMessage) {
        editText.setError(getString(resourceErrorMessage));
        editText.requestFocus();
    }

    private void processValidCash() {

        OtherPaymentMethodModel otherPaymentMethodModel = new OtherPaymentMethodModel();

        BigDecimal amount = new BigDecimal(mCashAmount.getText().toString());

        otherPaymentMethodModel.setAvailableCredit(amount);
        otherPaymentMethodModel.setName(mCashAlias.getText().toString());
        otherPaymentMethodModel.setTypePaymentMethod(TypePaymentMethodEnum.CASH);
        otherPaymentMethodModel.setReferenceNumber(REFERENCE_NUMBER);

        submitCash(otherPaymentMethodModel);

    }

    private void submitCash(OtherPaymentMethodModel otherPaymentMethodModel) {

        mCashPresenter.createCash(otherPaymentMethodModel);

    }

    @Override
    public void notifySuccessfulInsertion() {
        showMessage(CashActivity.SUCCESS_INSERTION_CASH);
    }

    @Override
    public void notifyErrorInsertion() {
        showMessage(CashActivity.ERROR_INSERTION_CASH);
    }

    @Override
    public void returnToMyPaymentsMethods() {

        LaunchIntents.launchIntentClearTop(this, MainActivity.class);

    }

    private void showMessage(int resourceMessage) {
        Toast.makeText(this, resourceMessage, Toast.LENGTH_LONG).show();
    }
}
