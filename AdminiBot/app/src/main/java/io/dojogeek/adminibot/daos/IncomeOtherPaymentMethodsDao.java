package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeOtherPaymentMethodModel;

public interface IncomeOtherPaymentMethodsDao {

    long createIncomeOtherPaymentMethod(IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel);

    IncomeOtherPaymentMethodModel getIncomeOtherPaymentMethodById(long incomeOtherPaymentMethodModelId) throws DataException;

    List<IncomeOtherPaymentMethodModel> getIncomesOthersPaymentMethods();

    List<IncomeOtherPaymentMethodModel> getIncomeOtherPaymentMethodByIncomeId(long incomeId);

    List<IncomeOtherPaymentMethodModel> getIncomeOtherPaymentMethodByPaymentMethodId(long paymentMethodId);

    long deleteIncomeOtherPaymentMethod(long incomeOtherPaymentMethodModelId);
}
