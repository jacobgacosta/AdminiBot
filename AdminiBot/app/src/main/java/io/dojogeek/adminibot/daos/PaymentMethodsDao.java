package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.PaymentMethodModel;

public interface PaymentMethodsDao extends ConectionDao {

    List<PaymentMethodModel> getPaymentMethods();

}
