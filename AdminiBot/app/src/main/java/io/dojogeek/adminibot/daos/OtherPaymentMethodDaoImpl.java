package io.dojogeek.adminibot.daos;

import android.content.Context;

import javax.inject.Inject;

public class OtherPaymentMethodDaoImpl extends SQLiteGlobalDao implements OtherPaymentMethodDao {

    @Inject
    public OtherPaymentMethodDaoImpl(Context context) {
        super(context);
    }
}
