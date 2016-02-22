package io.dojogeek.adminibot.daos;

import android.content.Context;

import javax.inject.Inject;

public class MovementIncomeBankCardDaoImpl extends SQLiteGlobalDao implements MovementIncomeBankCardDao {

    @Inject
    public MovementIncomeBankCardDaoImpl(Context context) {
        super(context);
    }

}
