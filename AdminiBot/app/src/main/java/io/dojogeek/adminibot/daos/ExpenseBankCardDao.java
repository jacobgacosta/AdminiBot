package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;

public interface ExpenseBankCardDao extends ConnectionDao {

    long createMovementExpenseBankCard(MovementExpenseBankCardModel movementExpenseBankCardModel);

    MovementExpenseBankCardModel getMovementExpenseBankCardById(long movementExpenseBankCardModelId) throws DataException;

    List<MovementExpenseBankCardModel> getMovementsExpensesBankCards();

    long updateMovementExpenseBankCard(MovementExpenseBankCardModel movementExpenseBankCardModel,
                                       String where);

    long deleteMovementExpenseBankCard(long movementExpenseBankCardModelId);

    List<MovementExpenseBankCardModel> getMovementsExpensesBankCardsByExpenseId(long expenseId);

    List<MovementExpenseBankCardModel> getMovementExpenseBankCardByBankCardId(long bankCardId);
}
