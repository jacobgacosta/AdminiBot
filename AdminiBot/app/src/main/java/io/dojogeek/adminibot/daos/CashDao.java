package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.PaymentMethodModel;

public interface CashDao {

    long createCash(PaymentMethodModel paymentMethod);

}
