package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;

public interface RegisterExpensePresenter {

    void getExpensesTypes();

    void addExpense(ExpenseModel expenseModel);

    void unnusedView();

    void getPaymentMethods();
}
