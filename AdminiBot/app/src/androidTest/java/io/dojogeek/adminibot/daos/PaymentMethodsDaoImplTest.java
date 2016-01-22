package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.PaymentMethodsContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodsDaoImplTest {

    private static final int NO_VALUE = 0;
    private static final int OPERATIONAL_ERROR = -1;
    private PaymentMethodsDao mPaymentMethodsDao;

    @Before
    public void setup() {
        Context context = getTargetContext();
        context.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
        mPaymentMethodsDao = new PaymentMethodsDaoImpl(context);
        mPaymentMethodsDao.openConection();
    }

    @After
    public void finishTest() {
        mPaymentMethodsDao.closeConection();
    }

    @Test
    public void paymentMethodsDao_createPaymentMethod_isTrue() {

        PaymentMethodModel paymentMethodModel = CreatorModels.createPaymentMethodModel();

        long insertedRecordId = mPaymentMethodsDao.createPaymentMethod(paymentMethodModel);

        assertTrue(insertedRecordId != OPERATIONAL_ERROR);
        assertTrue(insertedRecordId > NO_VALUE);
    }

    @Test
    public void paymentMethodsDao_numberInsertedPaymentMethods_isTrue() {

        int defaultInitialInsertedValues = 4;

        List<PaymentMethodModel> paymentMethods = mPaymentMethodsDao.getPaymentMethods();

        assertNotNull(paymentMethods);
        assertTrue(!paymentMethods.isEmpty());
        assertEquals(defaultInitialInsertedValues, paymentMethods.size());

    }


}
