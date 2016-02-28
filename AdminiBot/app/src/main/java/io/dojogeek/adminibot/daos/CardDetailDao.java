package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.CardDetailModel;

public interface CardDetailDao extends ConnectionDao {

    long createCardDetail(CardDetailModel cardDetailModel);

    CardDetailModel getCardDetailByBankCardId(long bankCardId) throws DataException;

    long updateCardDetail(CardDetailModel cardDetailModel, String where);

    long deleteCardDetail(long cardDetailId);
}
