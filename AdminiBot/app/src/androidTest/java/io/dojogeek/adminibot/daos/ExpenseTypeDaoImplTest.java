package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.ExpensesTypesContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpenseTypeDaoImplTest {

    private static final int NO_VALUE = 0;
    private static final int OPERATIONAL_ERROR = -1;
    private static final int SUCCESS_CODE = 1;
    private ExpenseTypeDao mExpenseTypeDao;

    @Before
    public void setup() {
        Context context = getTargetContext();

        mExpenseTypeDao = new ExpenseTypeDaoImpl(context);
        mExpenseTypeDao.openConection();
    }

    @After
    public void finishTest() {
        mExpenseTypeDao.removeAllExpensesTypes();
        mExpenseTypeDao.closeConection();
    }

    @Test
    public void expenseTypeDao_creationExpenseType_isTrue() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        assertTrue(insertedExpenseTypeId != OPERATIONAL_ERROR);
        assertTrue(insertedExpenseTypeId > NO_VALUE);
    }

    @Test
    public void expenseTypeDao_creationAndUpdatingExpenseType_isTrue() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        expenseTypeModel = changeExpenseTypeValues(expenseTypeModel);

        String where = ExpensesTypesContract.ExpenseType._ID + "= " + insertedExpenseTypeId;

        long rowsUpdated = mExpenseTypeDao.updateExpensetype(expenseTypeModel, where);

        assertNotEquals(OPERATIONAL_ERROR, rowsUpdated);
        assertEquals(SUCCESS_CODE, rowsUpdated);
    }

    @Test
    public void expenseTypeDao_creationAndObtainingExpenseType_isTrue() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expenseType = mExpenseTypeDao.getExpenseTypeById(insertedExpenseTypeId);

        assertNotNull(expenseType);
        assertEquals(expenseTypeModel.name, expenseType.name);
        assertEquals(expenseTypeModel.description, expenseType.description);

    }

    private ExpenseTypeModel changeExpenseTypeValues(ExpenseTypeModel expenseTypeModel) {

        expenseTypeModel.name = R.string.expenses_types_luxuries;
        expenseTypeModel.description = R.string.expenses_types_clothes;

        return expenseTypeModel;
    }
}
