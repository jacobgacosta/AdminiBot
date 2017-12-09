package io.dojogeek.adminibot.presenters;

public interface ExpensesInboxPresenter {

    void getExpenses();

    void unusedView();

    void deleteExpense(long expenseId);

    void getExpenseDetail(int idExpense);
}
