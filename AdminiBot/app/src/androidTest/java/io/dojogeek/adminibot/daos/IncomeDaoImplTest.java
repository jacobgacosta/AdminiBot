package io.dojogeek.adminibot.daos;

import android.content.Context;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.joda.time.DateTimeComparator;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.sqlite.IncomesContract;
import io.dojogeek.adminibot.utiltest.ModelsFactory;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class IncomeDaoImplTest {

    private static final long EQUALITY = 0;
    private static final long NO_OPERATION = 0;
    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;

    private Context mContext;
    private IncomeDao mIncomeDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomeDao = new IncomeDaoImpl(mContext);
    }

    @After
    public void tearDown() {
        ((IncomeDaoImpl) mIncomeDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testIncomeCreation_isCreated() {

        IncomeModel incomeModel = ModelsFactory.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

    }

    @Test(expected = NullPointerException.class)
    public void testIncomeCreation_withNullModel_isNotCreated() {

        IncomeModel incomeModel = null;

        mIncomeDao.createIncome(incomeModel);

    }

    @Test
    public void testGetIncomeById_successfulObtaining() throws DataException {

        IncomeModel expectedIncomeModel = ModelsFactory.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(expectedIncomeModel);

        IncomeModel actualIncome = mIncomeDao.getIncomeById(insertedRecordId);

        compareIncomes(expectedIncomeModel, actualIncome);

    }

    @Test(expected = DataException.class)
    public void testGetIncomeById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 5;

        mIncomeDao.getIncomeById(nonExistentId);

    }

    @Test
    public void testGetIncomes_obtainingASuccessfulList() {

        int numberOfIncomesToCreate = 5;

        List<IncomeModel> expectedIncomeModels = createIncomes(numberOfIncomesToCreate);

        List<IncomeModel> actualIncomeModels = mIncomeDao.getIncomes();

        compareIncomesList(expectedIncomeModels, actualIncomeModels);
    }

    @Test
    public void testGetIncomes_withNoRecords_isEmptyList() {

        List<IncomeModel> actualIncomeModels = mIncomeDao.getIncomes();

        assertThat(actualIncomeModels.isEmpty(), is(true));
    }

    @Test
    public void testUpdateIncome_successfulUpdate() throws DataException {

        IncomeModel incomeModel = ModelsFactory.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        IncomeModel expectedNewIncomeModel = incomeModel;
        expectedNewIncomeModel.setTotalAmount(new BigDecimal(50000));

        String where = IncomesContract.Incomes._ID + "= " +  insertedRecordId;

        long updatedRows = mIncomeDao.updateIncome(expectedNewIncomeModel, where);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        IncomeModel actualUpdatedIncome = mIncomeDao.getIncomeById(insertedRecordId);

        compareIncomes(expectedNewIncomeModel, actualUpdatedIncome);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateIncome_withNullModel_isException() {

        String where = IncomesContract.Incomes._ID + "= " +  2;

        mIncomeDao.updateIncome(null, where);

    }

    @Test
    public void testUpdateIncome_withNonExistentId_noUpdate() {

        IncomeModel incomeModel = ModelsFactory.createIncomeModel();

        long nonExistentId = 2;

        String where = IncomesContract.Incomes._ID + "= " +  nonExistentId;

        long updatedRows = mIncomeDao.updateIncome(incomeModel, where);

        assertThat(updatedRows, is(NO_OPERATION));

    }

    @Test
    public void testDeleteIncome_successfulDeletion() {

        IncomeModel incomeModel = ModelsFactory.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        long deletedRows = mIncomeDao.deleteIncome(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDeleteIncome_withNonExistentId_noDeletion() {

        IncomeModel incomeModel = ModelsFactory.createIncomeModel();

        mIncomeDao.createIncome(incomeModel);

        long nonExistentId = 5;

        long deletedRows = mIncomeDao.deleteIncome(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));

    }

    private List<IncomeModel> createIncomes(int numberOfIncomesToCreate) {

        List<IncomeModel> incomeModelList = new ArrayList<>();

        for (int index = 1; index <= numberOfIncomesToCreate; index++) {
            IncomeModel incomeModel = ModelsFactory.createIncomeModel();

            mIncomeDao.createIncome(incomeModel);
            incomeModelList.add(incomeModel);
        }

        return incomeModelList;
    }

    private void compareIncomesList(List<IncomeModel> expectedIncomes, List<IncomeModel> actualIncomes) {

        assertNotNull(actualIncomes);
        assertTrue(!actualIncomes.isEmpty());
        assertEquals(expectedIncomes.size(), actualIncomes.size());

        for (int index = 0; index < actualIncomes.size(); index++) {

            IncomeModel actualIncomeModel = actualIncomes.get(index);
            IncomeModel expectedIncomeModel = expectedIncomes.get(index);

            compareIncomes(expectedIncomeModel, actualIncomeModel);
        }
    }

    private void compareIncomes(IncomeModel expectedIncomeModel, IncomeModel actualIncomeModel) {

        assertNotNull(actualIncomeModel);
        assertEquals(expectedIncomeModel.getName(), actualIncomeModel.getName());

        DateTimeComparator date = DateTimeComparator.getDateOnlyInstance();
        DateTimeComparator time = DateTimeComparator.getTimeOnlyInstance();

        assertEquals(EQUALITY, date.compare(expectedIncomeModel.getCreatedAt(),
                actualIncomeModel.getCreatedAt()));
        assertEquals(EQUALITY, time.compare(expectedIncomeModel.getNextEntry().withMillisOfSecond(0),
                actualIncomeModel.getNextEntry().withMillisOfSecond(0)));
        assertEquals(expectedIncomeModel.getTotalAmount(), actualIncomeModel.getTotalAmount());
    }

}
