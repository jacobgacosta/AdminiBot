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
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.ExpensesContract;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class ExpenseDaoImplTest {

    private static final long NO_OPERATION = 0;
    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private ExpenseDao mExpenseDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mExpenseDao = new ExpenseDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((ExpenseDaoImpl)mExpenseDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateExpense_successInsertion() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

    }

    @Test(expected = NullPointerException.class)
    public void testCreateExpense_withNullModel_isException() {

        ExpenseModel expenseModel = null;

        mExpenseDao.createExpense(expenseModel);

    }

    @Test
    public void testCreateExpense_withNullRequieredField_noInsertion() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();
        expenseModel.setDescription(null);

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));

    }

    @Test
    public void testGetExpenses_successObtainingList() {

        int numberOfInsertions = 5;

        List<ExpenseModel> expectedExpenses = createExpenses(numberOfInsertions);

        List<ExpenseModel> actualExpenses = mExpenseDao.getExpenses();

        compareExpensesList(expectedExpenses, actualExpenses);
    }

    @Test
    public void testGetExpenses_withNoRecords_isEmptyList() {

        List<ExpenseModel> actualExpenses = mExpenseDao.getExpenses();

        assertThat(actualExpenses.isEmpty(), is(true));
    }


    @Test
    public void testGetExpenseById_successObtaining() throws DataException {

        ExpenseModel expectedExpenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expectedExpenseModel);

        ExpenseModel actualExpenseModel = mExpenseDao.getExpenseById(insertedRecordId);

        compareExpenses(expectedExpenseModel, actualExpenseModel);

    }

    @Test(expected = DataException.class)
    public void testGetExpenseById_withNonExistentId_isException() throws DataException {

        ExpenseModel expectedExpenseModel = CreatorModels.createExpenseModel();

        mExpenseDao.createExpense(expectedExpenseModel);

        long nonExistentId = 4;

        mExpenseDao.getExpenseById(nonExistentId);

    }

    @Test
    public void testUpdateExpense_successUpdating() throws DataException {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        ExpenseModel newExpenseModel = changeExpenseModelValues(expenseModel);

        String where = ExpensesContract.Expense._ID + "= " + insertedRecordId;

        long updatedRows = mExpenseDao.updateExpense(newExpenseModel, where);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        ExpenseModel updatedExpenseModel = mExpenseDao.getExpenseById(insertedRecordId);

        compareExpenses(newExpenseModel, updatedExpenseModel);

    }

    @Test(expected = NullPointerException.class)
    public void testUpdateExpense_withNullModel_isException() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        String where = ExpensesContract.Expense._ID + "= " + insertedRecordId;

        mExpenseDao.updateExpense(null, where);

    }

    @Test
    public void testUpdateExpense_withNonExistentId_noUpdating() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        mExpenseDao.createExpense(expenseModel);

        ExpenseModel newExpenseModel = changeExpenseModelValues(expenseModel);

        long nonExistentId = 5;

        String where = ExpensesContract.Expense._ID + "= " + nonExistentId;

        long updatedRows = mExpenseDao.updateExpense(newExpenseModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateExpense_withNullRequiredField_isException() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        ExpenseModel newExpenseModel = changeExpenseModelValues(expenseModel);
        newExpenseModel.setDescription(null);

        String where = ExpensesContract.Expense._ID + "= " + insertedRecordId;

        mExpenseDao.updateExpense(newExpenseModel, where);

    }

    @Test
    public void testDeleteExpense_successDeletion() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        long deletedRows = mExpenseDao.deleteExpense(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDeleteExpense_withNonExistentId_noDeletion() {

        long nonExistentId = 5;

        long deletedRows = mExpenseDao.deleteExpense(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    @Test
    public void testGetExpenseByExpenseTypeId_successObtaining() {

        int numberOfInsertions = 5;
        int expenseTypeId = 2;

        List<ExpenseModel> expectedExpensesModels = createExpenses(numberOfInsertions);

        List<ExpenseModel> actualExpensesModels = mExpenseDao.getExpenseByExpenseTypeId(expenseTypeId);

        compareExpensesList(expectedExpensesModels, actualExpensesModels);
    }

    @Test
    public void testGetExpenseByExpenseTypeId_withNonExistentExpenseTypeId_isEmptyList() {

        long nonExistentId = 4;

        List<ExpenseModel> actualExpensesModels = mExpenseDao.getExpenseByExpenseTypeId(nonExistentId);

        assertThat(actualExpensesModels.isEmpty(), is(true));

    }

    private ExpenseModel changeExpenseModelValues(ExpenseModel expenseModel) {

        expenseModel.setAmount(7357.90);
        expenseModel.setDescription("Update description");
        expenseModel.setExpenseTypeId(3);

        return expenseModel;
    }

    private List<ExpenseModel> createExpenses(int numberOfDummyInsertions) {

        List<ExpenseModel> expenseModelList = new ArrayList<>();

        for (int index = 1; index <= numberOfDummyInsertions; index++) {
            ExpenseModel expenseModel = CreatorModels.createExpenseModel("Expense type test " + index,
                    567.90 + index, DateUtils.getCurrentData(), DateUtils.getCurrentData(), 2, 20 + index);

            mExpenseDao.createExpense(expenseModel);
            expenseModelList.add(expenseModel);
        }

        return expenseModelList;
    }

    private void compareExpenses(ExpenseModel expectedExpense, ExpenseModel actualExpense) {
        assertNotNull(actualExpense);
        assertEquals(expectedExpense.getExpenseTypeId(), actualExpense.getExpenseTypeId());
        assertEquals(expectedExpense.getUserId(), actualExpense.getUserId());
        assertEquals(expectedExpense.getNextExit(), actualExpense.getNextExit());
        assertEquals(expectedExpense.getDateExpediture(), actualExpense.getDateExpediture());
        assertEquals(expectedExpense.getAmount(), actualExpense.getAmount(), 0);
        assertEquals(expectedExpense.getDescription(), actualExpense.getDescription());
    }

    private void compareExpensesList(List<ExpenseModel> expectedExpenses, List<ExpenseModel> actualExpenses) {

        assertNotNull(actualExpenses);
        assertTrue(!actualExpenses.isEmpty());
        assertEquals(expectedExpenses.size(), actualExpenses.size());

        for (int index = 0; index < actualExpenses.size(); index++) {

            ExpenseModel actualExpenseModel = actualExpenses.get(index);
            ExpenseModel expectedExpenseModel = expectedExpenses.get(index);

            assertEquals(expectedExpenseModel.getDescription(), actualExpenseModel.getDescription());
            assertEquals(expectedExpenseModel.getAmount(), actualExpenseModel.getAmount(), 0);
            assertEquals(expectedExpenseModel.getNextExit(), actualExpenseModel.getNextExit());
            assertEquals(expectedExpenseModel.getDateExpediture(), actualExpenseModel.getDateExpediture());
            assertEquals(expectedExpenseModel.getExpenseTypeId(), actualExpenseModel.getExpenseTypeId());
            assertEquals(expectedExpenseModel.getUserId(), actualExpenseModel.getUserId());

        }

    }

}
