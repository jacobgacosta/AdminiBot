package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;

public interface ExpenseDao extends ConectionDao {

    boolean createExpense(ExpenseModel expenseModel);

    List<ExpenseModel> getExpenses();

}
