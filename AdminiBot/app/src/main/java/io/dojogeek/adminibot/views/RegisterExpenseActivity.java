package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.PaymentMethod;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.presenters.RegisterExpensePresenter;
import io.dojogeek.adminibot.utils.DateUtils;

public class RegisterExpenseActivity extends BaseActivity implements RegisterExpense, View.OnClickListener {

    private static String TAG = "ExpenseRegistrationActivity";
    private static int DEFAULT_VALUE_SPINNER = R.string.expenses_types_default_value;
    private EditText mName;
    private PaymentMethod mPaymentMethod;
    private Spinner mExpensesTypes;
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
        expensesTypes.add(getString(DEFAULT_VALUE_SPINNER));

        for (ExpenseTypeModel expenseTypeModel : expenseTypeModelList) {
            expensesTypes.add(getString(expenseTypeModel.name));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, expensesTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpensesTypes.setAdapter(dataAdapter);
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

        setContentViewResource(R.layout.register_expense).prepareViewComponentsAndListeners();

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
        mExpensesTypes = (Spinner) findViewById(R.id.expenses_types);
        mName = (EditText) findViewById(R.id.expense_name);
        mOk = (Button) findViewById(R.id.expense_ok);
        mPaymentMethod = (PaymentMethod) findViewById(R.id.payment_method);
    }

    @Override
    protected void addListenersToViews() {
        mOk.setOnClickListener(this);
    }

    private void loadInitData() {
        registerExpensePresenter.getExpensesTypes();
    }


    private void processInputData() {

        ExpenseModel expenseModel = fillExpense();

        registerExpensePresenter.addExpense(expenseModel);

    }

    private ExpenseModel fillExpense() {

        ExpenseModel expense = new ExpenseModel();
        expense.dataExpediture = DateUtils.getCurrentData();
        expense.name = mName.getText().toString();
        expense.expenseTypeId = mExpenseTypeModelList.get(mExpensesTypes.getSelectedItemPosition()).id;

        return expense;
    }
}
