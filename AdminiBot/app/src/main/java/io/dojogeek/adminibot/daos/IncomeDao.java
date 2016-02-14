package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.IncomeModel;

public interface IncomeDao extends ConectionDao {

    long createIncome(IncomeModel incomeModel);

}
