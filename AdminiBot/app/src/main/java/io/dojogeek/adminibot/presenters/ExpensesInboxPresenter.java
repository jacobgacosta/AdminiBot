package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;

public interface ExpensesInboxPresenter {

    void getExpenses();

    void unusedView();

    void deleteExpense(long expenseId);

    void getExpenseDetail(int idExpense);
}
