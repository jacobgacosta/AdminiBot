package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseBankCardModel;

public interface ExpenseBankCardDao {

    long createMovementExpenseBankCard(ExpenseBankCardModel expenseBankCardModel);

    ExpenseBankCardModel getMovementExpenseBankCardById(long movementExpenseBankCardModelId) throws DataException;

    List<ExpenseBankCardModel> getMovementsExpensesBankCards();

    long updateMovementExpenseBankCard(ExpenseBankCardModel expenseBankCardModel,
                                       String where);

    long deleteMovementExpenseBankCard(long movementExpenseBankCardModelId);

    List<ExpenseBankCardModel> getMovementsExpensesBankCardsByExpenseId(long expenseId);

    List<ExpenseBankCardModel> getMovementExpenseBankCardByBankCardId(long bankCardId);
}
