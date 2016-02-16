package io.dojogeek.adminibot.daos;

import android.content.Context;

import javax.inject.Inject;

public class CardDetailDaoImpl extends SQLiteGlobalDao implements CardDetailDao  {

    @Inject
    public CardDetailDaoImpl(Context context) {
        super(context);
    }
}
