package io.dojogeek.adminibot.daos;

import android.content.Context;

import javax.inject.Inject;

public class BankCardDaoImpl extends SQLiteGlobalDao implements BankCardDao {

    public BankCardDaoImpl(Context context) {
        super(context);
    }

}
