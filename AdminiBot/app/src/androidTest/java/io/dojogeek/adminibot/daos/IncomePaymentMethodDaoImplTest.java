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
import io.dojogeek.adminibot.models.IncomeTypePaymentMethodModel;
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
    private IncomeTypePaymentMethodDao mIncomeTypePaymentMethodDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomeTypePaymentMethodDao = new IncomeTypeTypePaymentMethodDaoImpl(mContext);
    }

    @After
    public void tearDown() {
        ((IncomeTypeTypePaymentMethodDaoImpl) mIncomeTypePaymentMethodDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreation_isCreated() {

        IncomeTypePaymentMethodModel incomePaymentMethod = ModelsFactory.createIncomePaymentMethod();

        long insertedRecordId = mIncomeTypePaymentMethodDao.create(incomePaymentMethod);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreation_withNullModel_isNotCreated() {

        IncomeTypePaymentMethodModel incomePaymentMethod = null;

        mIncomeTypePaymentMethodDao.create(incomePaymentMethod);
    }

    @Test
    public void testGetById_successfulObtaining() throws DataException {

        IncomeTypePaymentMethodModel expected = ModelsFactory.createIncomePaymentMethod();

        long insertedRecordId = mIncomeTypePaymentMethodDao.create(expected);

        IncomeTypePaymentMethodModel actual = mIncomeTypePaymentMethodDao.getById(insertedRecordId);

        assertThat(actual, is(notNullValue()));

        assertThat(actual.getAmount(), is(expected.getAmount()));
        assertThat(actual.getIncomeId(), is(expected.getIncomeId()));
        assertThat(actual.getPaymentMethodId(), is(expected.getPaymentMethodId()));
    }

    @Test(expected = DataException.class)
    public void testGetById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 4;

        mIncomeTypePaymentMethodDao.getById(nonExistentId);
    }

    @Test
    public void testGetAll_obtainingASuccessfulList() {

        int numberOfInsertions = 5;

        List<IncomeTypePaymentMethodModel> expectedList = createIncomePaymentMethod(numberOfInsertions);

        List<IncomeTypePaymentMethodModel> actualList = mIncomeTypePaymentMethodDao.getAll();

        compareIncomePaymentMethodsList(expectedList, actualList);
    }

    @Test
    public void testGet_withNoRecords_isEmptyList() {

        List<IncomeTypePaymentMethodModel> actualList = mIncomeTypePaymentMethodDao.getAll();

        assertThat(actualList.isEmpty(), is(true));
    }

    @Test
    public void testGetByIncomeId_obtainingASuccessfulList() {

        int numberOfInsertions = 2;

        List<IncomeTypePaymentMethodModel> expectedList = createIncomePaymentMethod(numberOfInsertions);

        List<IncomeTypePaymentMethodModel> actualList = mIncomeTypePaymentMethodDao.getByIncomeId(1);

        assertThat(actualList.isEmpty(), is(false));
        assertThat(actualList.size(), is(2));
        assertThat(actualList.get(0), is(notNullValue()));
        compareIncomePaymentMethodsList(expectedList, actualList);
    }

    @Test
    public void testGetByIncomeId_withNonExistentId_isEmptyList() {

        long nonExistentId = 3;

        List<IncomeTypePaymentMethodModel> actualList = mIncomeTypePaymentMethodDao.getByIncomeId(nonExistentId);

        assertThat(actualList.isEmpty(), is(true));
    }

    @Test
    public void testGetByPaymentMethodId_obtainingASuccessfulList() {

        IncomeTypePaymentMethodModel expected = ModelsFactory.createIncomePaymentMethod();

        mIncomeTypePaymentMethodDao.create(expected);

        List<IncomeTypePaymentMethodModel> actualList = mIncomeTypePaymentMethodDao.
                getByTypePaymentMethodId(expected.getPaymentMethodId());

        assertThat(actualList.isEmpty(), is(false));
        assertThat(actualList.size(), is(1));
        assertThat(actualList.get(0), is(notNullValue()));
        compareIncomePaymentMethods(actualList.get(0), expected);
    }

    @Test
    public void testGetByPaymentMethodId_withNonExistentId_isEmptyList() {

        long nonExistentId = 3;

        List<IncomeTypePaymentMethodModel> actual =
                mIncomeTypePaymentMethodDao.getByTypePaymentMethodId(nonExistentId);

        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    public void testDelete_successfulDeletion() {

        IncomeTypePaymentMethodModel incomePaymentMethod = ModelsFactory.createIncomePaymentMethod();

        long insertedRecordId = mIncomeTypePaymentMethodDao.create(incomePaymentMethod);

        long deletedRows = mIncomeTypePaymentMethodDao.delete(insertedRecordId);

        assertThat(deletedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testDelete_withNonExistentId_noDeletion() {

        long nonExistentId = 5;

        long deletedRows = mIncomeTypePaymentMethodDao.delete(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    private List<IncomeTypePaymentMethodModel> createIncomePaymentMethod(int numberOfInsertions) {

        List<IncomeTypePaymentMethodModel> incomePaymentMethods = new ArrayList<>();

        for (int index = 0; index < numberOfInsertions; index++) {

            IncomeTypePaymentMethodModel incomePaymentMethod = ModelsFactory.createIncomePaymentMethod();

            mIncomeTypePaymentMethodDao.create(incomePaymentMethod);
            incomePaymentMethods.add(incomePaymentMethod);
        }

        return incomePaymentMethods;
    }

    private void compareIncomePaymentMethodsList(List<IncomeTypePaymentMethodModel> expectedList,
                                                 List<IncomeTypePaymentMethodModel> actualList) {

        assertThat(actualList, is(notNullValue()));
        assertThat(actualList.isEmpty(), is(false));
        assertThat(actualList.size(), is(expectedList.size()));

        for (int index = 0; index < actualList.size(); index++) {

            IncomeTypePaymentMethodModel actual = actualList.get(index);

            IncomeTypePaymentMethodModel expected = expectedList.get(index);

            compareIncomePaymentMethods(expected, actual);
        }
    }

    private void compareIncomePaymentMethods(IncomeTypePaymentMethodModel expected,
                                                 IncomeTypePaymentMethodModel actual) {

        assertThat(actual, is(notNullValue()));
        assertThat(actual.getAmount(), is(expected.getAmount()));
        assertThat(actual.getIncomeId(), is(expected.getIncomeId()));
        assertThat(actual.getPaymentMethodId(), is(expected.getPaymentMethodId()));
    }

}
