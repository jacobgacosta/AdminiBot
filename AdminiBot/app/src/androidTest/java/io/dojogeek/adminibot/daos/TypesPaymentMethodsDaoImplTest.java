package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.TypesPaymentMethodsContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Deprecated
@RunWith(AndroidJUnit4.class)
public class TypesPaymentMethodsDaoImplTest {

    private static final int NO_VALUE = 0;
    private static final int OPERATIONAL_ERROR = -1;
    private TypesPaymentMethodsDao mTypesPaymentMethodsDao;

    @Before
    public void setup() {
        Context context = getTargetContext();
        context.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
        mTypesPaymentMethodsDao = new TypesPaymentMethodsDaoImpl(context);
    }

    @After
    public void finishTest() {
        ((TypesPaymentMethodsDaoImpl) mTypesPaymentMethodsDao).closeConnection();
    }

    @Test
    public void paymentMethodsDao_createPaymentMethod_isTrue() {

        TypePaymentMethodModel typesPaymentMethodModel = CreatorModels.createTypePaymentMethodModel();

        long insertedRecordId = mTypesPaymentMethodsDao.createPaymentMethod(typesPaymentMethodModel);

        assertTrue(insertedRecordId != OPERATIONAL_ERROR);
        assertTrue(insertedRecordId > NO_VALUE);
    }

    @Test
    public void paymentMethodsDao_numberInsertedPaymentMethods_isTrue() {

        int defaultInitialInsertedValues = 4;

        List<TypePaymentMethodModel> paymentMethods = mTypesPaymentMethodsDao.getPaymentMethods();

        assertNotNull(paymentMethods);
        assertTrue(!paymentMethods.isEmpty());
        assertEquals(defaultInitialInsertedValues, paymentMethods.size());

    }

    @Test
    public void paymentMethodsDao_matchingPaymentMethodsInitialInsertion_isTrue() {


        List<TypePaymentMethodModel> paymentMethodsList = mTypesPaymentMethodsDao.getPaymentMethods();
        assertNotNull(paymentMethodsList);
        assertTrue(!paymentMethodsList.isEmpty());
        comparePaymentMethodsResult(paymentMethodsList);

    }

    private void comparePaymentMethodsResult(List<TypePaymentMethodModel> actualPaymentMethodsList) {

        for (int index = 0; index < actualPaymentMethodsList.size(); index++) {
            assertEquals(TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS[index].getName(), actualPaymentMethodsList.get(index).name);
            assertEquals(TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS[index].getDescription(), actualPaymentMethodsList.get(index).description);
        }

    }


}
