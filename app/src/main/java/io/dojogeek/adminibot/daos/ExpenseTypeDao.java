package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseTypeModel;

public interface ExpenseTypeDao {

    long createExpenseType(ExpenseTypeModel expenseType);

    List<ExpenseTypeModel> getExpensesTypes();

    ExpenseTypeModel getExpenseTypeById(long id) throws DataException;

    long updateExpensetype(ExpenseTypeModel expenseTypeModel, String where);

    long deleteExpenseTypeById(long expenseTypeId);

}
