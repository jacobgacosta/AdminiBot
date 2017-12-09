package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.PaymentMethodModel;

public interface PaymentMethodDao {

    long delete(long id);

    List<PaymentMethodModel> getAll();

    long create(PaymentMethodModel paymentMethod);

    List<TypePaymentMethodEnum> getRegisteredTypes();

    long update(PaymentMethodModel paymentMethod, long id);

    PaymentMethodModel getById(long id) throws DataException;
}
