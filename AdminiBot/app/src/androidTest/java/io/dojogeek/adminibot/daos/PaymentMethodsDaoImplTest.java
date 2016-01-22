package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodsDaoImplTest {

    private PaymentMethodsDao mPaymentMethodsDao;

    @Before
    public void setup() {
        Context context = getTargetContext();
        context.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
        mPaymentMethodsDao = new PaymentMethodsDaoImpl(context);
    }

    @After
    public void finishTest() {
        mPaymentMethodsDao.closeConection();
    }

}
