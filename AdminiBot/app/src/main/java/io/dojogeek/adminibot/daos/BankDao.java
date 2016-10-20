package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankModel;

public interface BankDao {

    List<BankModel> getBanks();

    BankModel getBankById(long bankId);
}
