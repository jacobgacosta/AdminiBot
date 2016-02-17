package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface OtherPaymentMethodDao extends ConectionDao {

    long createOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel);

}
