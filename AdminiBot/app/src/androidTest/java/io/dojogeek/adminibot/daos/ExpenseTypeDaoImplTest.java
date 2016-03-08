package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.enums.ExpenseTypeEnum;
import io.dojogeek.adminibot.exceptions.DataException;
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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class ExpenseTypeDaoImplTest {

    private static final long NO_OPERATION = 0;
    private static final int OPERATIONAL_ERROR = -1;
    private ExpenseTypeDao mExpenseTypeDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();

        mExpenseTypeDao = new ExpenseTypeDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((ExpenseTypeDaoImpl)mExpenseTypeDao).closeConnection();
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
    public void testGetExpenseTypeById_successObtaining() throws DataException {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expenseType = mExpenseTypeDao.getExpenseTypeById(insertedExpenseTypeId);

        assertNotNull(expenseType);
        assertEquals(expenseTypeModel.name, expenseType.name);
        assertEquals(expenseTypeModel.description, expenseType.description);

    }

    @Test(expected = DataException.class)
    public void testGetExpenseTypeById_withNonExistentId_isException() throws DataException {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        mExpenseTypeDao.createExpenseType(expenseTypeModel);

        long nonExistentId = 7;

        mExpenseTypeDao.getExpenseTypeById(nonExistentId);

    }

    @Test
    public void testGetExpensesTypes_obtainingInitialInsertedRecords_successObtainingList() {

        int initiallyInsertedExpensesTypes = 5;

        List<ExpenseTypeModel> actualExpenseTypeModelList = mExpenseTypeDao.getExpensesTypes();

        assertNotNull(actualExpenseTypeModelList);
        assertTrue(!actualExpenseTypeModelList.isEmpty());
        assertEquals(initiallyInsertedExpensesTypes, actualExpenseTypeModelList.size());

        compareExpensesModelsInitiallyInserted(actualExpenseTypeModelList);
    }

    @Test
    public void testUpdateExpensetype_successUpdating() throws DataException {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expectedUpdatedExpenseTypeModel = changeExpenseTypeValues(expenseTypeModel);

        String where = ExpensesTypesContract.ExpenseType._ID + "= " + insertedExpenseTypeId;

        long rowsUpdated = mExpenseTypeDao.updateExpensetype(expectedUpdatedExpenseTypeModel, where);

        assertNotEquals(NO_OPERATION, rowsUpdated);

        ExpenseTypeModel actualExpenseTypeModel = mExpenseTypeDao.getExpenseTypeById(insertedExpenseTypeId);

        compareExpensesModels(expectedUpdatedExpenseTypeModel, actualExpenseTypeModel);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateExpensetype_withNullModel_isException() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expectedNewExpenseTypeModel = null;

        String where = ExpensesTypesContract.ExpenseType._ID + "= " + insertedExpenseTypeId;

        mExpenseTypeDao.updateExpensetype(expectedNewExpenseTypeModel, where);

    }

    @Test
    public void testUpdateExpensetype_withNonExistentId_noUpdating() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expectedUpdatedExpenseTypeModel = changeExpenseTypeValues(expenseTypeModel);

        long nonExistentId = 9;

        String where = ExpensesTypesContract.ExpenseType._ID + "= " + nonExistentId;

        long updatedRows = mExpenseTypeDao.updateExpensetype(expectedUpdatedExpenseTypeModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateExpensetype_withNullRequiredField_isException() {

        ExpenseTypeModel expenseTypeModel = CreatorModels.createExpenseTypeModel();

        long insertedExpenseTypeId = mExpenseTypeDao.createExpenseType(expenseTypeModel);

        ExpenseTypeModel expectedUpdatedExpenseTypeModel = changeExpenseTypeValues(expenseTypeModel);
        expectedUpdatedExpenseTypeModel.name = null;

        String where = ExpensesTypesContract.ExpenseType._ID + "= " + insertedExpenseTypeId;

        mExpenseTypeDao.updateExpensetype(expectedUpdatedExpenseTypeModel, where);

    }

    @Test
    public void testDeleteExpenseType_successDeletion() {

        long initiallyInsertedExpenseTypeId = 3;

        long removedRows = mExpenseTypeDao.deleteExpenseTypeById(initiallyInsertedExpenseTypeId);

        assertEquals(1, removedRows);

    }

    @Test
    public void testDeleteExpenseType_withNonExistenId_noDeletion() {

        long nonExistentId = 9;

        long removedRows = mExpenseTypeDao.deleteExpenseTypeById(nonExistentId);

        assertThat(removedRows, is(NO_OPERATION));

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
