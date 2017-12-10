package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankCardModel;

public interface BankCardDao {

    long delete(long id);

    List<BankCardModel> getAll();

    long create(BankCardModel bankCardModel);

    List<BankCardModel> getByCartType(String type);

    List<TypePaymentMethodEnum> getRegisteredTypes();

    long update(BankCardModel bankCardModel, long id);

    BankCardModel getById(long id) throws DataException;

}
