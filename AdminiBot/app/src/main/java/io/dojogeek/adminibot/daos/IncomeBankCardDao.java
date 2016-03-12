package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeBankCardModel;

public interface IncomeBankCardDao {

    long createMovementIncomeBankCard(IncomeBankCardModel movementIncomeBankCard);

    IncomeBankCardModel getMovementIncomeBankCardById(long movementIncomeBankCardId) throws DataException;

    List<IncomeBankCardModel> getMovementsIncomesBankCards();

    List<IncomeBankCardModel> getMovementsIncomesBankCardsByIncomeId(long incomeId);

    List<IncomeBankCardModel> getMovementsIncomesBankCardsByBankCardId(long bankCardId);

    long updateMovementIncomeBankCard(IncomeBankCardModel incomeBankCardModel, String where);

    long deleteMovementIncomeBankCard(long movementIncomeBankCardId);
}
