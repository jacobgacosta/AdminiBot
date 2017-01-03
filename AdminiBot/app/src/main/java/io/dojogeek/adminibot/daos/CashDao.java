package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface CashDao {

    long createCash(OtherPaymentMethodModel otherPaymentMethodModel);

}
