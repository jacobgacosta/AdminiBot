package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;

public interface OtherPaymentMethodDao {

    long createOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel);

    OtherPaymentMethodModel getOtherPaymentMethodById(long otherPaymentMethodId) throws DataException;

    List<OtherPaymentMethodModel> getOtherPaymentMethods();

    long updateOtherPaymentMethod(OtherPaymentMethodModel otherPaymentMethodModel, long id);

    long deleteOtherPaymentMethod(long otherPaymentMethodId);

    List<OtherPaymentMethodModel> getOtherPaymentMethodByType(TypePaymentMethodEnum typePaymentMethodEnum);
}
