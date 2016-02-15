package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.BankCardModel;

public interface BankCardDao extends ConectionDao {

    long createBankCard(BankCardModel bankCardModel);

    BankCardModel getBankCardById(long cardBankId);
}
