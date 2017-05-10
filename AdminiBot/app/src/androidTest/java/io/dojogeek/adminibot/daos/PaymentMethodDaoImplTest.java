package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.ModelsFactory;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PaymentMethodDaoImplTest {

    private static final long NO_OPERATION = 0;
    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;

    private Context mContext;
    private PaymentMethodDao mPaymentMethodDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mPaymentMethodDao = new PaymentMethodDaoImpl(mContext);
    }

    @After
    public void tearDown() {
        ((PaymentMethodDaoImpl) mPaymentMethodDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreation_isCreated() {

        PaymentMethodModel paymentMethod = ModelsFactory.createPaymentMethodModel();

        long insertedRecordId = mPaymentMethodDao.create(paymentMethod);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreation_withNullModel_isNotCreated() {

        mPaymentMethodDao.create(null);
    }

    @Test
    public void testGetById_successfulObtaining() throws DataException {

        PaymentMethodModel expected = ModelsFactory.createPaymentMethodModel();

        long insertedRecordId = mPaymentMethodDao.create(expected);

        PaymentMethodModel actual = mPaymentMethodDao.getById(insertedRecordId);

        assertNull(actual.getUpdatedAt());
        assertNotNull(actual.getCreatedAt());
        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getType(), is(expected.getType()));
    }

    @Test(expected = DataException.class)
    public void testGetById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 4;

        mPaymentMethodDao.getById(nonExistentId);
    }

    @Test
    public void testGetAll_obtainingASuccessfulList() {

        PaymentMethodModel expected = ModelsFactory.createPaymentMethodModel();
        mPaymentMethodDao.create(expected);
        mPaymentMethodDao.create(expected);

        List<PaymentMethodModel> actualList = mPaymentMethodDao.getAll();

        assertNotNull(actualList);
        assertEquals(2, actualList.size());
        assertNull(actualList.get(0).getUpdatedAt());
        assertNotNull(actualList.get(0).getCreatedAt());
        assertEquals(expected.getName(), actualList.get(0).getName());
        assertEquals(expected.getType(), actualList.get(0).getType());
    }

    @Test
    public void testGetBankCards_withNoRecords_isEmptyList() {

        List<PaymentMethodModel> banks = mPaymentMethodDao.getAll();

        assertTrue(banks.isEmpty());
    }

    @Test
    public void testUpdate_successfulUpdate() throws DataException {

        PaymentMethodModel expected = ModelsFactory.createPaymentMethodModel();

        long insertedRecordId = mPaymentMethodDao.create(expected);

        expected.setType(TypePaymentMethodEnum.CREDIT_CARD);

        long updatedRows = mPaymentMethodDao.update(expected, insertedRecordId);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        PaymentMethodModel actual = mPaymentMethodDao.getById(insertedRecordId);

        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getType(), actual.getType());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdate_withNullModel_isException() {

        int paymentMethodId = 2;

        mPaymentMethodDao.update(null, paymentMethodId);
    }

    @Test
    public void testUpdate_withNonExistentId_noUpdate() {

        PaymentMethodModel paymentMethod = ModelsFactory.createPaymentMethodModel();

        long nonExistentId = 5;

        long updatedRows = mPaymentMethodDao.update(paymentMethod, nonExistentId);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDelete_successfulDeletion() {

        PaymentMethodModel paymentMethod = ModelsFactory.createPaymentMethodModel();

        long insertedRecordId = mPaymentMethodDao.create(paymentMethod);

        long deletedRows = mPaymentMethodDao.delete(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDelete_withNonExistentId_noDeletion() {

        long nonExistentId = 5;

        long deletedRows = mPaymentMethodDao.delete(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    @Test
    public void testGetAllRegisteredTypes_successfulObtaining() {

        PaymentMethodModel paymentMethod1 = new PaymentMethodModel();
        paymentMethod1.setName("First payment method test");
        paymentMethod1.setType(TypePaymentMethodEnum.CASH);

        PaymentMethodModel paymentMethod2 = new PaymentMethodModel();
        paymentMethod2.setName("Second payment method test");
        paymentMethod2.setType(TypePaymentMethodEnum.CASH);

        PaymentMethodModel paymentMethod3 = new PaymentMethodModel();
        paymentMethod3.setName("Third payment method test");
        paymentMethod3.setType(TypePaymentMethodEnum.FOOD_COUPONS);

        mPaymentMethodDao.create(paymentMethod1);
        mPaymentMethodDao.create(paymentMethod2);
        mPaymentMethodDao.create(paymentMethod3);

        List<TypePaymentMethodEnum> actual = mPaymentMethodDao.getRegisteredTypes();

        assertEquals(2, actual.size());
    }
}
