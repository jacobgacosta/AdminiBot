package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomePaymentMethodModel;

public interface IncomePaymentMethodDao {

    long delete(long id);

    List<IncomePaymentMethodModel> get();

    List<IncomePaymentMethodModel> getByIncomeId(long incomeId);

    IncomePaymentMethodModel getById(long id) throws DataException;

    long create(IncomePaymentMethodModel incomePaymentMethodModel);

    List<IncomePaymentMethodModel> getByPaymentMethodId(long paymentMethodId);
}
