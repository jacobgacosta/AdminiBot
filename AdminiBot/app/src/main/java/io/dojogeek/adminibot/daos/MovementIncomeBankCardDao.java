package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementIncomeBankCardModel;

public interface MovementIncomeBankCardDao extends ConnectionDao {

    long createMovementIncomeBankCard(MovementIncomeBankCardModel movementIncomeBankCard);

    MovementIncomeBankCardModel getMovementIncomeBankCardById(long movementIncomeBankCardId) throws DataException;
}
