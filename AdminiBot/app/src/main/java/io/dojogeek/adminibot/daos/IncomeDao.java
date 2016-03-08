package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeModel;

public interface IncomeDao {

    long createIncome(IncomeModel incomeModel);

    IncomeModel getIncomeById(long incomeId) throws DataException;

    List<IncomeModel> getIncomes();

    long updateIncome(IncomeModel incomeModel, String where);

    long deleteIncome(long incomeId);
}
