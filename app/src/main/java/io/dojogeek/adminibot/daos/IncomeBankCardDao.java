package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeBankCardModel;

public interface IncomeBankCardDao {

    List<IncomeBankCardModel> get();

    long delete(long movementIncomeBankCardId);

    List<IncomeBankCardModel> getByIncomeId(long incomeId);

    long create(IncomeBankCardModel movementIncomeBankCard);

    IncomeBankCardModel getById(long id) throws DataException;

    List<IncomeBankCardModel> getByBankCardId(long bankCardId);

    long update(IncomeBankCardModel incomeBankCardModel, String where);
}
