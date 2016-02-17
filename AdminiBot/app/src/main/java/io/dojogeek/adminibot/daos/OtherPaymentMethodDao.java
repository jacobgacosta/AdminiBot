package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface OtherPaymentMethodDao extends ConectionDao {

    long createOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel);

    OtherPaymentMethodModel getOtherPaymentMethodById(long otherPaymentMethodId);

    List<OtherPaymentMethodModel> getOtherPaymentMethods();

    long updateOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel, String where);
}
