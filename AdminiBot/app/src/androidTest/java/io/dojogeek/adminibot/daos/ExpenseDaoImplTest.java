package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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

        createExpenses(numberOfInsertions);

        List<ExpenseModel> expenses = mExpenseDao.getExpenses();

        assertNotNull(expenses);
        assertTrue(!expenses.isEmpty());
        assertEquals(numberOfInsertions, expenses.size());
    }


    @Test
    public void expenseDao_creationAndUpdatingExpense_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        ExpenseModel newExpenseModel = changeExpenseModelValues(expenseModel);

        String where = ExpensesContract.Expense._ID + "= " + insertedRecordId;

        long updatedRows = mExpenseDao.updateExpense(newExpenseModel, where);

        assertEquals(SUCCESS_OPERATION, updatedRows);
    }

    private ExpenseModel changeExpenseModelValues(ExpenseModel expenseModel) {

        expenseModel.amount = 7357.90;
        expenseModel.description = "Update description";
        expenseModel.expenseTypeId = 3;

        return expenseModel;
    }

    private void createExpenses(int numberOfDummyInsertions) {

        for (int index = 1; index <= numberOfDummyInsertions; index++) {
            ExpenseModel expenseModel = CreatorModels.createExpenseModel("Expense type test " + index,
                    567.90 + index, DateUtils.getCurrentData(), DateUtils.getCurrentData(), index, 20 + index);

            mExpenseDao.createExpense(expenseModel);
        }

    }

}
