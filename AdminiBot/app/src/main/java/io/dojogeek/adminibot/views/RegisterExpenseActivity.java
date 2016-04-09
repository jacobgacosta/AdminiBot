package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.CustomSpinner;
import io.dojogeek.adminibot.components.PaymentMethod;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.presenters.RegisterExpensePresenter;
import io.dojogeek.adminibot.utils.DateUtils;

public class RegisterExpenseActivity extends BaseActivity implements RegisterExpense, View.OnClickListener {

    private static String TAG = "ExpenseRegistrationActivity";
    private static final int DEFAULT_VALUE_SPINNER_EXPENSES_TYPES = R.string.expenses_types_spinner_default_value;
    private static final int DEFAULT_VALUE_SPINNER_PAYMENT__METHODS = R.string.payment_methods_spinner_default_value;

    private EditText mName;
    private PaymentMethod mPaymentMethod;
    private CustomSpinner mExpensesTypes;
    private Button mOk;
    private List<ExpenseTypeModel> mExpenseTypeModelList;

    @Inject
    public RegisterExpensePresenter registerExpensePresenter;

    @Override
    public void showNotification(int message) {

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    @Override
    public void deployExpensesTypes(List<ExpenseTypeModel> expenseTypeModelList) {

        mExpenseTypeModelList = expenseTypeModelList;

        List<String> expensesTypes = new ArrayList<>();

        for (ExpenseTypeModel expenseTypeModel : expenseTypeModelList) {
            expensesTypes.add(expenseTypeModel.getName().getName());
        }

        loadSpinnerData(expensesTypes);
    }

    @Override
    public void deployPaymentMethods(List<TypePaymentMethodModel> typesPaymentMethodsModelList) {

        List<String> paymentMethods = new ArrayList<>();

        for (TypePaymentMethodModel typePaymentMethodModel : typesPaymentMethodsModelList) {
            //paymentMethods.add(typePaymentMethodModel.name.getName());
        }

        mPaymentMethod.createSpinner(paymentMethods, DEFAULT_VALUE_SPINNER_PAYMENT__METHODS);

    }

    @Override
    public void onClick(View view) {

        int idView = view.getId();

        switch (idView) {
            case R.id.expense_ok:
                processInputData();
                onBackPressed();
                break;
            default:
                Log.v(TAG, "no events!");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loadInitData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        registerExpensePresenter.unnusedView();
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        appComponent.plus(new AdminiBotModule(this)).inject(this);
    }

    @Override
    protected void loadViews() {
        mExpensesTypes = (CustomSpinner) findViewById(R.id.expenses_types);
        mName = (EditText) findViewById(R.id.expense_name);
        mOk = (Button) findViewById(R.id.expense_ok);
        mPaymentMethod = (PaymentMethod) findViewById(R.id.payment_method_component);
    }

    @Override
    protected void addListenersToViews() {
        mOk.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

    }

    @Override
    protected int getLayoutActivity() {

        return R.layout.register_expense;

    }

    @Override
    protected void closeConnections() {

    }

    private void loadSpinnerData(List<String> expensesTypes) {
        mExpensesTypes.setItems(expensesTypes);
        mExpensesTypes.setHint(DEFAULT_VALUE_SPINNER_EXPENSES_TYPES);
        mExpensesTypes.createSpinner();
    }

    private void processInputData() {

        ExpenseModel expenseModel = fillExpense();

        registerExpensePresenter.addExpense(expenseModel);

    }

    private ExpenseModel fillExpense() {

        ExpenseModel expense = new ExpenseModel();
        /*expense.dataExpediture = DateUtils.getCurrentData();
        expense.name = mName.getText().toString();
        expense.expenseTypeId = mExpenseTypeModelList.get(mExpensesTypes.getSelectedItemPosition()).id;
        expense.totalAmount = getTotalAmount();*/

        return expense;
    }

    private String getTotalAmount() {

        double totalAmount = 0.0;

        EditText mandatoryAmount = (EditText) mPaymentMethod.findViewById(R.id.mandatory_amount);
        totalAmount += Double.parseDouble(mandatoryAmount.getText().toString());

        Map<Integer, RelativeLayout> paymentMethods = mPaymentMethod.getAddedItems();

        for (Map.Entry<Integer, RelativeLayout> entry : paymentMethods.entrySet()) {
            RelativeLayout relativeLayout = entry.getValue();
            EditText editText = (EditText) relativeLayout.findViewById(R.id.added_amount);
            totalAmount += Double.parseDouble(editText.getText().toString());
        }

        return String.valueOf(totalAmount);
    }

    private void loadInitData() {
        //registerExpensePresenter.getExpensesTypes();
        //registerExpensePresenter.getPaymentMethods();
    }

}
