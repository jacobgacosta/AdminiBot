package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.ExpenseTypeEnum;
import io.dojogeek.adminibot.models.ExpenseModel;
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
    private ExpenseTypeDao mExpenseTypeDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();

        mExpenseTypeDao = new ExpenseTypeDaoImpl(mContext);
        mExpenseTypeDao.openConection();
    }

    @After
    public void finishTest() {
        mExpenseTypeDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateExpenseType_successInsertion() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        assertNotEquals(OPERATIONAL_ERROR, insertedExpenseTypeId);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateExpenseType_withNullModel_isException() {

        ExpenseTypeModel expenseTypeModel = null;

        mExpenseTypeDao.createExpenseType(expenseTypeModel);

    }

    @Test(expected = NullPointerException.class)
    public void testCreateExpenseType_withNullRequiredField_isException() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();
        expenseTypeModel.name = null;

        mExpenseTypeDao.createExpenseType(expenseTypeModel);

    }

    @Test
    public void testGetExpenseTypeById_successObtaining() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expenseType = mExpenseTypeDao.getExpenseTypeById(insertedExpenseTypeId);

        assertNotNull(expenseType);
        assertEquals(expenseTypeModel.name, expenseType.name);
        assertEquals(expenseTypeModel.description, expenseType.description);

    }

    @Test
    public void testGetExpensesTypes_successObtainingList() {

        int initiallyInsertedExpensesTypes = 5;

        List<ExpenseTypeModel> actualExpenseTypeModelList = mExpenseTypeDao.getExpensesTypes();

        assertNotNull(actualExpenseTypeModelList);
        assertTrue(!actualExpenseTypeModelList.isEmpty());
        assertEquals(initiallyInsertedExpensesTypes, actualExpenseTypeModelList.size());

        compareExpensesModelsInitiallyInserted(actualExpenseTypeModelList);
    }

    @Test
    public void testUpdateExpensetype_successUpdating() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expectedNewExpenseTypeModel = changeExpenseTypeValues(expenseTypeModel);

        String where = ExpensesTypesContract.ExpenseType._ID + "= " + insertedExpenseTypeId;

        long rowsUpdated = mExpenseTypeDao.updateExpensetype(expectedNewExpenseTypeModel, where);

        assertNotEquals(NO_VALUE, rowsUpdated);

        ExpenseTypeModel actualExpenseTypeModel = mExpenseTypeDao.getExpenseTypeById(insertedExpenseTypeId);

        compareExpensesModels(expectedNewExpenseTypeModel, actualExpenseTypeModel);
    }

    @Test
    public void testDeleteExpenseTypeById_successDeletion() {

        long initiallyInsertedExpenseTypeId = 3;

        long removedRows = mExpenseTypeDao.deleteExpenseTypeById(initiallyInsertedExpenseTypeId);

        assertEquals(1, removedRows);

    }


    private ExpenseTypeModel changeExpenseTypeValues(ExpenseTypeModel expenseTypeModel) {

        expenseTypeModel.name = ExpenseTypeEnum.CLOTHES;
        expenseTypeModel.description = ExpenseTypeEnum.CLOTHES.getDescription();

        return expenseTypeModel;
    }

    private void compareExpensesModels(ExpenseTypeModel expectedExpenseTypeModel,
                                       ExpenseTypeModel actualExpenseTypeModel) {

        assertEquals(expectedExpenseTypeModel.name, actualExpenseTypeModel.name);
        assertEquals(expectedExpenseTypeModel.description, actualExpenseTypeModel.description);

    }

    private void compareExpensesModelsInitiallyInserted(List<ExpenseTypeModel> actualExpenseTypeModelList) {

        for (int index = 0; index < actualExpenseTypeModelList.size(); index++) {
            ExpenseTypeModel actualExpenseTypeModel = actualExpenseTypeModelList.get(index);

            ExpenseTypeEnum expectedName = ExpensesTypesContract.EXPENSES_TYPES[index];
            String expectedDescription = ExpensesTypesContract.EXPENSES_TYPES[index].getDescription();

            assertEquals(expectedName, actualExpenseTypeModel.name);
            assertEquals(expectedDescription, actualExpenseTypeModel.description);

        }

    }

}
