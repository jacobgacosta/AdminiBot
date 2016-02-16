package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.ExpensesContract;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExpenseDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final int OPERATIONAL_ERROR = -1;
    private ExpenseDao mExpenseDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mExpenseDao = new ExpenseDaoImpl(mContext);
        mExpenseDao.openConection();
    }

    @After
    public void finishTest() {
        mExpenseDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void expenseDao_creationExpense_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

    }

    @Test
    public void expenseDao_obtainingAllExpensesInitiallyInserted_isTrue() {

        int numberOfInsertions = 5;

        List<ExpenseModel> expectedExpenses = createExpenses(numberOfInsertions);

        List<ExpenseModel> actualExpenses = mExpenseDao.getExpenses();

        compareExpensesList(expectedExpenses, actualExpenses);
    }


    @Test
    public void expenseDao_creationAndObtainingExpenseById_isTrue() {

        ExpenseModel expectedExpenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expectedExpenseModel);

        ExpenseModel actualExpenseModel = mExpenseDao.getExpenseById(insertedRecordId);

        compareExpenses(expectedExpenseModel, actualExpenseModel);

    }

    @Test
    public void expenseDao_creationUpdatingAndObtainingExpenseById_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        ExpenseModel newExpenseModel = changeExpenseModelValues(expenseModel);

        String where = ExpensesContract.Expense._ID + "= " + insertedRecordId;

        long updatedRows = mExpenseDao.updateExpense(newExpenseModel, where);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        ExpenseModel updatedExpenseModel = mExpenseDao.getExpenseById(insertedRecordId);

        compareExpenses(newExpenseModel, updatedExpenseModel);

    }

    @Test
    public void expenseDao_creationAndDeleteExpense_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        long deletedRows = mExpenseDao.deleteExpense(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void expenseDao_createAndObtainingExpenseByExpenseTypeId_isTrue() {

        int numberOfInsertions = 5;
        int expenseTypeId = 2;

        List<ExpenseModel> expectedExpensesModels = createExpenses(numberOfInsertions);

        List<ExpenseModel> actualExpensesModels = mExpenseDao.getExpenseByExpenseTypeId(expenseTypeId);

        compareExpensesList(expectedExpensesModels, actualExpensesModels);
    }

    private ExpenseModel changeExpenseModelValues(ExpenseModel expenseModel) {

        expenseModel.amount = 7357.90;
        expenseModel.description = "Update description";
        expenseModel.expenseTypeId = 3;

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
        assertEquals(expectedExpense.expenseTypeId, actualExpense.expenseTypeId);
        assertEquals(expectedExpense.userId, actualExpense.userId);
        assertEquals(expectedExpense.nextExit, actualExpense.nextExit);
        assertEquals(expectedExpense.dateExpediture, actualExpense.dateExpediture);
        assertEquals(expectedExpense.amount, actualExpense.amount, 0);
        assertEquals(expectedExpense.description, actualExpense.description);
    }

    private void compareExpensesList(List<ExpenseModel> expectedExpenses, List<ExpenseModel> actualExpenses) {

        assertNotNull(actualExpenses);
        assertTrue(!actualExpenses.isEmpty());
        assertEquals(expectedExpenses.size(), actualExpenses.size());

        for (int index = 0; index < actualExpenses.size(); index++) {

            ExpenseModel actualExpenseModel = actualExpenses.get(index);
            ExpenseModel expectedExpenseModel = expectedExpenses.get(index);

            assertEquals(expectedExpenseModel.description, actualExpenseModel.description);
            assertEquals(expectedExpenseModel.amount, actualExpenseModel.amount, 0);
            assertEquals(expectedExpenseModel.nextExit, actualExpenseModel.nextExit);
            assertEquals(expectedExpenseModel.dateExpediture, actualExpenseModel.dateExpediture);
            assertEquals(expectedExpenseModel.expenseTypeId, actualExpenseModel.expenseTypeId);
            assertEquals(expectedExpenseModel.userId, actualExpenseModel.userId);

        }

    }

}
