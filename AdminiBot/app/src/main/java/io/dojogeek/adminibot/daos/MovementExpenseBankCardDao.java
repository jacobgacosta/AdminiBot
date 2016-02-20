package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;

public interface MovementExpenseBankCardDao extends ConectionDao {

    long createMovementExpenseBankCard(MovementExpenseBankCardModel movementExpenseBankCardModel);

    MovementExpenseBankCardModel getMovementExpenseBankCardById(long movementExpenseBankCardModelId) throws DataException;
}
