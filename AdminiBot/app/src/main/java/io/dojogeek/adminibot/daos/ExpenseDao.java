package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseModel;

public interface ExpenseDao extends ConnectionDao {

    long createExpense(ExpenseModel expenseModel);

    long updateExpense(ExpenseModel expenseModel, String where);

    List<ExpenseModel> getExpenses();

    ExpenseModel getExpenseById(long expenseId) throws DataException;

    long deleteExpense(long expenseId);

    List<ExpenseModel> getExpenseByExpenseTypeId(long expenseTypeId);
}
