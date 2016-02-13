package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.sqlite.ExpensesContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExpenseDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final int OPERATIONAL_ERROR = -1;
    private ExpenseDao mExpenseDao;

    @Before
    public void setup() {
        Context context = getTargetContext();
        mExpenseDao = new ExpenseDaoImpl(context);
        mExpenseDao.openConection();
    }

    @After
    public void finishTest() {
        mExpenseDao.closeConection();
    }

    @Test
    public void expenseDao_creationExpense_isTrue() {

        ExpenseModel expenseModel = CreatorModels.createExpenseModel();

        long insertedRecordId = mExpenseDao.createExpense(expenseModel);

        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

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

}
