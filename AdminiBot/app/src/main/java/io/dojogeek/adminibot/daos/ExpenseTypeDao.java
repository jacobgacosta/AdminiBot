package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseTypeModel;

public interface ExpenseTypeDao extends ConectionDao {

    long createExpenseType(ExpenseTypeModel expenseType);

    List<ExpenseTypeModel> getExpensesTypes();

    ExpenseTypeModel getExpenseTypeById(long id);
}
