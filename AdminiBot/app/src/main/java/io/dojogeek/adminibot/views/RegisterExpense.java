package io.dojogeek.adminibot.views;

import java.util.List;

import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.TypePaymentMethodModel;

public interface RegisterExpense {

    void showNotification(int message);

    void deployExpensesTypes(List<ExpenseTypeModel> expenseTypeModelList);

    void deployPaymentMethods(List<TypePaymentMethodModel> typesPaymentMethodsModelList);
}
