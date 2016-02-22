package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.CardDetailModel;

public interface CardDetailDao extends ConnectionDao {

    long createCardDetail(CardDetailModel cardDetailModel);

    CardDetailModel getCardDetailById(long cardDetailId);

    CardDetailModel getCardDetailByBankCardId(long bankCardId);

    long updateCardDetail(CardDetailModel cardDetailModel, String where);

    long deleteCardDetail(long cardDetailId);
}
