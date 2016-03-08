package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementIncomeBankCardModel;

public interface IncomeBankCardDao {

    long createMovementIncomeBankCard(MovementIncomeBankCardModel movementIncomeBankCard);

    MovementIncomeBankCardModel getMovementIncomeBankCardById(long movementIncomeBankCardId) throws DataException;

    List<MovementIncomeBankCardModel> getMovementsIncomesBankCards();

    List<MovementIncomeBankCardModel> getMovementsIncomesBankCardsByIncomeId(long incomeId);

    List<MovementIncomeBankCardModel> getMovementsIncomesBankCardsByBankCardId(long bankCardId);

    long updateMovementIncomeBankCard(MovementIncomeBankCardModel movementIncomeBankCardModel, String where);

    long deleteMovementIncomeBankCard(long movementIncomeBankCardId);
}
