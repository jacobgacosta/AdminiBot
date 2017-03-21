package io.dojogeek.adminibot.daos;

import android.content.Context;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.ArrayList;

import io.dojogeek.adminibot.utiltest.ModelsFactory;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomePaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertThat;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class IncomePaymentMethodDaoImplTest {

    private static final long NO_OPERATION = 0;
    private static final long SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;

    private Context mContext;
    private IncomePaymentMethodDao mIncomePaymentMethodDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomePaymentMethodDao = new IncomePaymentMethodDaoImpl(mContext);
    }

    @After
    public void tearDown() {
        ((IncomePaymentMethodDaoImpl) mIncomePaymentMethodDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreation_isCreated() {

        IncomePaymentMethodModel incomePaymentMethod = ModelsFactory.createIncomePaymentMethod();

        long insertedRecordId = mIncomePaymentMethodDao.create(incomePaymentMethod);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreation_withNullModel_isNotCreated() {

        IncomePaymentMethodModel incomePaymentMethod = null;

        mIncomePaymentMethodDao.create(incomePaymentMethod);
    }

    @Test
    public void testGetById_successfulObtaining() throws DataException {

        IncomePaymentMethodModel expected = ModelsFactory.createIncomePaymentMethod();

        long insertedRecordId = mIncomePaymentMethodDao.create(expected);

        IncomePaymentMethodModel actual = mIncomePaymentMethodDao.getById(insertedRecordId);

        assertThat(actual, is(notNullValue()));

        assertThat(actual.getAmount(), is(expected.getAmount()));
        assertThat(actual.getIncomeId(), is(expected.getIncomeId()));
        assertThat(actual.getPaymentMethodId(), is(expected.getPaymentMethodId()));
    }

    @Test(expected = DataException.class)
    public void testGetById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 4;

        mIncomePaymentMethodDao.getById(nonExistentId);
    }

    @Test
    public void testGet_obtainingASuccessfulList() {

        int numberOfInsertions = 5;

        List<IncomePaymentMethodModel> expectedList = createIncomePaymentMethod(numberOfInsertions);

        List<IncomePaymentMethodModel> actualList = mIncomePaymentMethodDao.get();

        compareIncomePaymentMethodsList(expectedList, actualList);
    }

    @Test
    public void testGet_withNoRecords_isEmptyList() {

        List<IncomePaymentMethodModel> actualList = mIncomePaymentMethodDao.get();

        assertThat(actualList.isEmpty(), is(true));
    }

    @Test
    public void testGetByIncomeId_obtainingASuccessfulList() {

        int numberOfInsertions = 2;

        List<IncomePaymentMethodModel> expectedList = createIncomePaymentMethod(numberOfInsertions);

        List<IncomePaymentMethodModel> actualList = mIncomePaymentMethodDao.getByIncomeId(1);

        assertThat(actualList.isEmpty(), is(false));
        assertThat(actualList.size(), is(2));
        assertThat(actualList.get(0), is(notNullValue()));
        compareIncomePaymentMethodsList(expectedList, actualList);
    }

    @Test
    public void testGetByIncomeId_withNonExistentId_isEmptyList() {

        long nonExistentId = 3;

        List<IncomePaymentMethodModel> actualList = mIncomePaymentMethodDao.getByIncomeId(nonExistentId);

        assertThat(actualList.isEmpty(), is(true));
    }

    @Test
    public void testGetByPaymentMethodId_obtainingASuccessfulList() {

        IncomePaymentMethodModel expected = ModelsFactory.createIncomePaymentMethod();

        mIncomePaymentMethodDao.create(expected);

        List<IncomePaymentMethodModel> actualList = mIncomePaymentMethodDao.
                getByPaymentMethodId(expected.getPaymentMethodId());

        assertThat(actualList.isEmpty(), is(false));
        assertThat(actualList.size(), is(1));
        assertThat(actualList.get(0), is(notNullValue()));
        compareIncomePaymentMethods(actualList.get(0), expected);
    }

    @Test
    public void testGetByPaymentMethodId_withNonExistentId_isEmptyList() {

        long nonExistentId = 3;

        List<IncomePaymentMethodModel> actual =
                mIncomePaymentMethodDao.getByPaymentMethodId(nonExistentId);

        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    public void testDelete_successfulDeletion() {

        IncomePaymentMethodModel incomePaymentMethod = ModelsFactory.createIncomePaymentMethod();

        long insertedRecordId = mIncomePaymentMethodDao.create(incomePaymentMethod);

        long deletedRows = mIncomePaymentMethodDao.delete(insertedRecordId);

        assertThat(deletedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testDelete_withNonExistentId_noDeletion() {

        long nonExistentId = 5;

        long deletedRows = mIncomePaymentMethodDao.delete(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    private List<IncomePaymentMethodModel> createIncomePaymentMethod(int numberOfInsertions) {

        List<IncomePaymentMethodModel> incomePaymentMethods = new ArrayList<>();

        for (int index = 0; index < numberOfInsertions; index++) {

            IncomePaymentMethodModel incomePaymentMethod = ModelsFactory.createIncomePaymentMethod();

            mIncomePaymentMethodDao.create(incomePaymentMethod);
            incomePaymentMethods.add(incomePaymentMethod);
        }

        return incomePaymentMethods;
    }

    private void compareIncomePaymentMethodsList(List<IncomePaymentMethodModel> expectedList,
                                                 List<IncomePaymentMethodModel> actualList) {

        assertThat(actualList, is(notNullValue()));
        assertThat(actualList.isEmpty(), is(false));
        assertThat(actualList.size(), is(expectedList.size()));

        for (int index = 0; index < actualList.size(); index++) {

            IncomePaymentMethodModel actual = actualList.get(index);

            IncomePaymentMethodModel expected = expectedList.get(index);

            compareIncomePaymentMethods(expected, actual);
        }
    }

    private void compareIncomePaymentMethods(IncomePaymentMethodModel expected,
                                                 IncomePaymentMethodModel actual) {

        assertThat(actual, is(notNullValue()));
        assertThat(actual.getAmount(), is(expected.getAmount()));
        assertThat(actual.getIncomeId(), is(expected.getIncomeId()));
        assertThat(actual.getPaymentMethodId(), is(expected.getPaymentMethodId()));
    }

}
