package io.dojogeek.adminibot.presenters;

import java.sql.SQLException;

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.ExpenseModel;

public interface ExpenseCreationPresenter {

    void createExpense(ExpenseModel expenseModel);

}
