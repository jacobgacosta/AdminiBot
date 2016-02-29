package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseOtherPaymentMethodModel;

public interface ExpenseOtherPaymentMethodDao extends ConnectionDao {

    long createExpenseOtherPaymentMethod(ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel);

    ExpenseOtherPaymentMethodModel getExpenseOtherPaymentMethodById(long expenseOtherPaymentMethod) throws DataException;

    List<ExpenseOtherPaymentMethodModel> getExpensesOthersPaymentMethods();

    List<ExpenseOtherPaymentMethodModel> getExpenseOtherPaymentMethodByExpenseId(long expenseId);

    List<ExpenseOtherPaymentMethodModel> getExpenseOtherPaymentMethodByPaymentMethod(long paymentMethod);

    long updateExpenseOtherPaymentMethod(ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel, String where);

    long deleteExpenseOtherPaymentMethod(long expenseOtherPaymentMethod);
}
