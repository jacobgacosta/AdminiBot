package io.dojogeek.adminibot.daos;

import android.content.Context;

import javax.inject.Inject;

public class IncomeDaoImpl extends SQLiteGlobalDao implements IncomeDao {

    @Inject
    public IncomeDaoImpl(Context context) {
        super(context);
    }

}
