package io.dojogeek.adminibot.views;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;

public interface Inbox {

    void listExpenses(List<ExpenseModel> expenses);

}
