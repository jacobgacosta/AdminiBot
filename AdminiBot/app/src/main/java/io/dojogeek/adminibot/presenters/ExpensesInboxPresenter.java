package io.dojogeek.adminibot.presenters;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;

public interface ExpensesInboxPresenter {

    void getExpenses();

    void unnusedView();

    void deleteExpense(long expenseId);
}
