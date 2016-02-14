package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.IncomeModel;

public interface IncomeDao extends ConectionDao {

    long createIncome(IncomeModel incomeModel);

    IncomeModel getIncomeById(long incomeId);

    List<IncomeModel> getIncomes();
}
