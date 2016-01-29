package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.TypesPaymentMethodsModel;

public interface TypesPaymentMethodsDao extends ConectionDao {

    List<TypesPaymentMethodsModel> getPaymentMethods();

    public long createPaymentMethod(TypesPaymentMethodsModel paymentMethod);

}
