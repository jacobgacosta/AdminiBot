package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseTypeModel;

public interface ExpenseTypeDao extends ConectionDao {

    List<ExpenseTypeModel> getExpensesTypes();

    ExpenseTypeModel getExpenseTypeById(long id);
}
