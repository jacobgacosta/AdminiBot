package io.dojogeek.adminibot.daos;

import android.content.ContentValues;

import java.util.List;

import io.dojogeek.adminibot.models.PaymentMethodModel;

public interface PaymentMethodsDao extends ConectionDao {

    List<PaymentMethodModel> getPaymentMethods();

    public long createPaymentMethod(PaymentMethodModel paymentMethod);

}
