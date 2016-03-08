package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.IncomesContract;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class IncomeDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;

    private Context mContext;
    private IncomeDao mIncomeDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomeDao = new IncomeDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((IncomeDaoImpl) mIncomeDao).closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateIncome_successInsertion() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

    }

    @Test(expected = NullPointerException.class)
    public void testCreateIncome_withNullModel_isException() {

        IncomeModel incomeModel = null;

        mIncomeDao.createIncome(incomeModel);

    }

    @Test
    public void testCreateIncome_withNullRequiredField_noInsertion() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();
        incomeModel.description = null;

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetIncomeById_successObtaining() throws DataException {

        IncomeModel expectedIncomeModel = CreatorModels.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(expectedIncomeModel);

        IncomeModel actualIncome = mIncomeDao.getIncomeById(insertedRecordId);

        assertNotNull(actualIncome);
        assertEquals(expectedIncomeModel.description, actualIncome.description);
        assertEquals(expectedIncomeModel.amount, actualIncome.amount, 0);
        assertEquals(expectedIncomeModel.date, actualIncome.date);
        assertEquals(expectedIncomeModel.nextDate, actualIncome.nextDate);
        assertEquals(expectedIncomeModel.userId, actualIncome.userId);
    }

    @Test(expected = DataException.class)
    public void testGetIncomeById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 5;

        mIncomeDao.getIncomeById(nonExistentId);

    }

    @Test
    public void testGetIncomes_successObtainingList() {

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
    public void testUpdateIncome_successUpdate() throws DataException {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        IncomeModel expectedNewIncomeModel = changeIncomeModelValues(incomeModel);

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
    public void testUpdateIncome_withNonExistentId_noUpdating() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long nonExistentId = 2;

        String where = IncomesContract.Incomes._ID + "= " +  nonExistentId;

        long updatedRows = mIncomeDao.updateIncome(incomeModel, where);

        assertThat(updatedRows, is(NO_OPERATION));

    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateIncome_withNullRequiredField_isException() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        incomeModel.description = null;

        String where = IncomesContract.Incomes._ID + "= " +  insertedRecordId;

        mIncomeDao.updateIncome(incomeModel, where);

    }

    @Test
    public void testDeleteIncome_successDeletion() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        long insertedRecordId = mIncomeDao.createIncome(incomeModel);

        long deletedRows = mIncomeDao.deleteIncome(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDeleteIncome_withNonExistentId_noDeletion() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        mIncomeDao.createIncome(incomeModel);

        long nonExistentId = 5;

        long deletedRows = mIncomeDao.deleteIncome(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));

    }

    private List<IncomeModel> createIncomes(int numberOfIncomesToCreate) {

        List<IncomeModel> incomeModelList = new ArrayList<>();

        for (int index = 1; index <= numberOfIncomesToCreate; index++) {
            IncomeModel incomeModel = CreatorModels.createIncomeModel("Test description " + index,
                    24506.90 + index, DateUtils.getCurrentData(), DateUtils.getCurrentData(), 1 + index);

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
        assertEquals(expectedIncomeModel.description, actualIncomeModel.description);
        assertEquals(expectedIncomeModel.amount, actualIncomeModel.amount, 0);
        assertEquals(expectedIncomeModel.date, actualIncomeModel.date);
        assertEquals(expectedIncomeModel.nextDate, actualIncomeModel.nextDate);
        assertEquals(expectedIncomeModel.userId, actualIncomeModel.userId);
    }

    private IncomeModel changeIncomeModelValues(IncomeModel incomeModel) {
        incomeModel.description = "updated description";
        incomeModel.amount = 120.60;
        incomeModel.date = DateUtils.getCurrentData();
        incomeModel.nextDate = null;
        incomeModel.userId = 1;

        return incomeModel;
    }
}
