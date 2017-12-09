package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.ExpenseModel;

public interface RegisterExpensePresenter {

    void getExpensesTypes();

    void addExpense(ExpenseModel expenseModel);

    void unnusedView();

    void getPaymentMethods();
}
