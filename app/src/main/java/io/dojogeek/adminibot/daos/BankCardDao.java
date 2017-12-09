package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.exceptions.DataException;

public interface BankCardDao {

    long delete(long id);

    List<BankCardModel> getAll();

    long create(BankCardModel bankCardModel);

    List<BankCardModel> getByCartType(String type);

    List<TypePaymentMethodEnum> getRegisteredTypes();

    long update(BankCardModel bankCardModel, long id);

    BankCardModel getById(long id) throws DataException;

}
