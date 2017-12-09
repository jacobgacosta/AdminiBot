package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeTypePaymentMethodModel;

public interface IncomeTypePaymentMethodDao {

    long delete(long id);

    List<IncomeTypePaymentMethodModel> getAll();

    List<IncomeTypePaymentMethodModel> getByIncomeId(long incomeId);

    long create(IncomeTypePaymentMethodModel incomeTypePaymentMethodModel);

    IncomeTypePaymentMethodModel getById(long id) throws DataException;

    List<IncomeTypePaymentMethodModel> getByTypePaymentMethodId(long typePaymentMethodId);
}
