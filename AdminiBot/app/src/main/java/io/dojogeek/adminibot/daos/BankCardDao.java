package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.BankCardModel;

public interface BankCardDao extends ConectionDao {

    long createBankCard(BankCardModel bankCardModel);

    BankCardModel getBankCardById(long cardBankId);

    List<BankCardModel> getBankCards();
}
