package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeModel;

public interface IncomeDao {

    List<IncomeModel> getIncomes();

    long deleteIncome(long incomeId);

    long createIncome(IncomeModel incomeModel);

    long updateIncome(IncomeModel incomeModel, String where);

    IncomeModel getIncomeById(long incomeId) throws DataException;
}
