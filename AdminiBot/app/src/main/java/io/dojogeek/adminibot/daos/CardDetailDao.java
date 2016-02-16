package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.CardDetailModel;

public interface CardDetailDao extends ConectionDao {

    long createCardDetail(CardDetailModel cardDetailModel);

}
