package io.dojogeek.adminibot.views;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;

public interface ExpensesInbox {

    void listExpenses(List<ExpenseModel> expenseModelList);

    void showNotification(int stringResourceId);
}
