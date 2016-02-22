package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.MovementIncomeBankCardModel;

public interface MovementIncomeBankCardDao extends ConnectionDao {

    long createMovementIncomeBankCard(MovementIncomeBankCardModel movementIncomeBankCard);
}
