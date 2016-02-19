package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;

public interface MovementExpenseBankCardDao extends ConectionDao {

    long createMovementExpenseBankCard(MovementExpenseBankCardModel movementExpenseBankCardModel);
}
