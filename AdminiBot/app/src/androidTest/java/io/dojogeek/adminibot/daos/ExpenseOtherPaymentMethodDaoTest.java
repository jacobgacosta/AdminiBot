package io.dojogeek.adminibot.daos;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseOtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.ExpensesOthersPaymentMethodsContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ExpenseOtherPaymentMethodDaoTest {

    private static final long SUCCESS_OPERATION = 1;
    private static final long NO_OPERATION = 0;
    private static final long OPERATIONAL_ERROR = -1;
    private ExpenseOtherPaymentMethodDao mExpenseOtherPaymentMethodDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();

        mExpenseOtherPaymentMethodDao = new ExpenseOtherPaymentMethodDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((ExpenseOtherPaymentMethodDaoImpl)mExpenseOtherPaymentMethodDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateExpenseOtherPaymentMethod_successInsertion() {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                CreatorModels.createExpenseOtherPaymentMethodModel();

        long insertedRecordId =
                mExpenseOtherPaymentMethodDao.createExpenseOtherPaymentMethod(expenseOtherPaymentMethodModel);

        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
        assertThat(insertedRecordId, is(not(NO_OPERATION)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateExpenseOtherPaymentMethod_withNullModel_isException() {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel = null;

        mExpenseOtherPaymentMethodDao.createExpenseOtherPaymentMethod(expenseOtherPaymentMethodModel);

    }

    @Test
    public void testGetExpenseOtherPaymentMethodById_successObtaining() throws DataException {

        ExpenseOtherPaymentMethodModel expectedExpenseOtherPaymentMethodModel =
                CreatorModels.createExpenseOtherPaymentMethodModel();

        long insertedRecordId =
                mExpenseOtherPaymentMethodDao.createExpenseOtherPaymentMethod(expectedExpenseOtherPaymentMethodModel);

        ExpenseOtherPaymentMethodModel actualExpenseOtherPaymentMethod =
                mExpenseOtherPaymentMethodDao.getExpenseOtherPaymentMethodById(insertedRecordId);

        compareExpensesOtherPaymentsMethodsModels(expectedExpenseOtherPaymentMethodModel, actualExpenseOtherPaymentMethod);

    }

    @Test(expected = DataException.class)
    public void testGetExpenseOtherPaymentMethodById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 4;

        mExpenseOtherPaymentMethodDao.getExpenseOtherPaymentMethodById(nonExistentId);

    }

    @Test
    public void testGetExpensesOthersPaymentMethods_successObtainingList() {

        int numberOfDummyInsertions = 5;

        List<ExpenseOtherPaymentMethodModel> expectedOtherPaymentMethodModelList =
                createExpenseOtherPaymentMethod(numberOfDummyInsertions);

        List<ExpenseOtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mExpenseOtherPaymentMethodDao.getExpensesOthersPaymentMethods();

        compareExpenseOtherPaymentMethodModelList(expectedOtherPaymentMethodModelList,
                actualOtherPaymentMethodModelList);
    }

    @Test
    public void testGetExpensesOthersPaymentMethods_withNoRecords_isEmptyList() {

        List<ExpenseOtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mExpenseOtherPaymentMethodDao.getExpensesOthersPaymentMethods();

        assertThat(actualOtherPaymentMethodModelList, is(notNullValue()));
        assertThat(actualOtherPaymentMethodModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetExpenseOtherPaymentMethodByExpenseId_successObtainingList() {

        long expenseId = 2;
        int numberOfDummyInsertions = 4;

        List<ExpenseOtherPaymentMethodModel> expectedOtherPaymentMethodModelList =
                createExpenseOtherPaymentMethod(numberOfDummyInsertions);

        List<ExpenseOtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mExpenseOtherPaymentMethodDao.getExpenseOtherPaymentMethodByExpenseId(expenseId);

        compareExpenseOtherPaymentMethodModelList(expectedOtherPaymentMethodModelList,
                actualOtherPaymentMethodModelList);

    }

    @Test
    public void testGetExpenseOtherPaymentMethodByExpenseId_withNonExistentExpenseId_isEmptyList() {

        long nonExistentExpenseId = 3;

        List<ExpenseOtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mExpenseOtherPaymentMethodDao.getExpenseOtherPaymentMethodByExpenseId(nonExistentExpenseId);

        assertThat(actualOtherPaymentMethodModelList, is(notNullValue()));
        assertThat(actualOtherPaymentMethodModelList.isEmpty(), is(true));

    }

    @Test
    public void testGetExpenseOtherPaymentMethodByPaymentMethod_successObtainingList() {

        long paymentMethod = 2;
        int numberOfDummyInsertions = 4;

        List<ExpenseOtherPaymentMethodModel> expectedOtherPaymentMethodModelList =
                createExpenseOtherPaymentMethod(numberOfDummyInsertions);

        List<ExpenseOtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mExpenseOtherPaymentMethodDao.getExpenseOtherPaymentMethodByPaymentMethod(paymentMethod);

        compareExpenseOtherPaymentMethodModelList(expectedOtherPaymentMethodModelList,
                actualOtherPaymentMethodModelList);

    }

    @Test
    public void testGetExpenseOtherPaymentMethodByPaymentMethod_withNonExistentExpenseId_isEmptyList() {

        long nonExistentPaymentMethod = 1;

        List<ExpenseOtherPaymentMethodModel> actualOtherPaymentMethodModelList =
                mExpenseOtherPaymentMethodDao.getExpenseOtherPaymentMethodByPaymentMethod(nonExistentPaymentMethod);

        assertThat(actualOtherPaymentMethodModelList, is(notNullValue()));
        assertThat(actualOtherPaymentMethodModelList.isEmpty(), is(true));

    }

    @Test
    public void testUpdateExpenseOtherPaymentMethod_successUpdating() throws DataException {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                CreatorModels.createExpenseOtherPaymentMethodModel();

        long insertedRecordId =
                mExpenseOtherPaymentMethodDao.createExpenseOtherPaymentMethod(expenseOtherPaymentMethodModel);

        ExpenseOtherPaymentMethodModel updatedExpenseOtherPaymentMethodModel =
                changeExpenseOtherPaymentMethodModelFields(expenseOtherPaymentMethodModel);

        String where = ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod._ID + "= "+ insertedRecordId;

        long updatedRows = mExpenseOtherPaymentMethodDao.
                updateExpenseOtherPaymentMethod(updatedExpenseOtherPaymentMethodModel, where);

        assertThat(updatedRows, is(SUCCESS_OPERATION));

        ExpenseOtherPaymentMethodModel actualExpenseOtherPaymentMethodModel =
                mExpenseOtherPaymentMethodDao.getExpenseOtherPaymentMethodById(insertedRecordId);

        compareExpensesOtherPaymentsMethodsModels(updatedExpenseOtherPaymentMethodModel, actualExpenseOtherPaymentMethodModel);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateExpenseOtherPaymentMethod_withNullModel_isException() {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                CreatorModels.createExpenseOtherPaymentMethodModel();

        long insertedRecordId =
                mExpenseOtherPaymentMethodDao.createExpenseOtherPaymentMethod(expenseOtherPaymentMethodModel);

        String where = ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod._ID + "= "+ insertedRecordId;

        mExpenseOtherPaymentMethodDao.updateExpenseOtherPaymentMethod(null, where);

    }

    @Test
    public void testUpdateExpenseOtherPaymentMethod_withNonExistentId__isException() {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                CreatorModels.createExpenseOtherPaymentMethodModel();

        mExpenseOtherPaymentMethodDao.createExpenseOtherPaymentMethod(expenseOtherPaymentMethodModel);

        ExpenseOtherPaymentMethodModel updatedExpenseOtherPaymentMethodModel =
                changeExpenseOtherPaymentMethodModelFields(expenseOtherPaymentMethodModel);

        long nonExistentId = 7;

        String where = ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod._ID + "= "+ nonExistentId;

        long updatedRows = mExpenseOtherPaymentMethodDao.
                updateExpenseOtherPaymentMethod(updatedExpenseOtherPaymentMethodModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDeleteExpenseOtherPaymentMethod_successDeletion() {


        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                CreatorModels.createExpenseOtherPaymentMethodModel();

        long insertedRecordId = mExpenseOtherPaymentMethodDao.
                createExpenseOtherPaymentMethod(expenseOtherPaymentMethodModel);

        long deletedRows = mExpenseOtherPaymentMethodDao.deleteExpenseOtherPaymentMethod(insertedRecordId);

        assertThat(deletedRows,is(SUCCESS_OPERATION));
    }

    @Test
    public void testDeleteExpenseOtherPaymentMethod_withNonExistentId_noDeletion() {

        long nonExistentId = 6;

        long deletedRows = mExpenseOtherPaymentMethodDao.deleteExpenseOtherPaymentMethod(nonExistentId);

        assertThat(deletedRows,is(NO_OPERATION));

    }

    private List<ExpenseOtherPaymentMethodModel> createExpenseOtherPaymentMethod(int numberOfDummyInsertions) {

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModelList = new ArrayList<>();

        for (int index = 1; index <= numberOfDummyInsertions; index++) {
            ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                    CreatorModels.createExpenseOtherPaymentMethodModel();

            mExpenseOtherPaymentMethodDao.createExpenseOtherPaymentMethod(expenseOtherPaymentMethodModel);
            expenseOtherPaymentMethodModelList.add(expenseOtherPaymentMethodModel);
        }

        return expenseOtherPaymentMethodModelList;
    }

    private void compareExpenseOtherPaymentMethodModelList(List<ExpenseOtherPaymentMethodModel> expectedExpenseOtherPaymentMethodModelList,
                                                           List<ExpenseOtherPaymentMethodModel> actualExpenseOtherPaymentMethodModelList) {

        assertNotNull(actualExpenseOtherPaymentMethodModelList);
        assertTrue(!actualExpenseOtherPaymentMethodModelList.isEmpty());
        assertEquals(expectedExpenseOtherPaymentMethodModelList.size(), actualExpenseOtherPaymentMethodModelList.size());

        for (int index = 0; index < actualExpenseOtherPaymentMethodModelList.size(); index++) {

            ExpenseOtherPaymentMethodModel actualExpenseOtherPaymentMethodModel = actualExpenseOtherPaymentMethodModelList.get(index);
            ExpenseOtherPaymentMethodModel expectedExpenseOtherPaymentMethodModel = expectedExpenseOtherPaymentMethodModelList.get(index);

            assertThat(actualExpenseOtherPaymentMethodModel.getAmount(),
                    is(expectedExpenseOtherPaymentMethodModel.getAmount()));
            assertThat(actualExpenseOtherPaymentMethodModel.getOtherPaymentMethodId(),
                    is(expectedExpenseOtherPaymentMethodModel.getOtherPaymentMethodId()));
            assertThat(actualExpenseOtherPaymentMethodModel.getExpenseId(),
                    is(expectedExpenseOtherPaymentMethodModel.getExpenseId()));

        }
    }

    private ExpenseOtherPaymentMethodModel changeExpenseOtherPaymentMethodModelFields(ExpenseOtherPaymentMethodModel
                                                                                              expenseOtherPaymentMethodModel) {

        expenseOtherPaymentMethodModel.setAmount(94359.80);
        expenseOtherPaymentMethodModel.setExpenseId(4);
        expenseOtherPaymentMethodModel.setOtherPaymentMethodId(4);

        return expenseOtherPaymentMethodModel;
    }

    private void compareExpensesOtherPaymentsMethodsModels(ExpenseOtherPaymentMethodModel expectedExpenseOtherPaymentMethodModel,
                                                           ExpenseOtherPaymentMethodModel actualExpenseOtherPaymentMethodModel) {

        assertThat(actualExpenseOtherPaymentMethodModel, is(notNullValue()));
        assertThat(actualExpenseOtherPaymentMethodModel.getAmount(),
                is(expectedExpenseOtherPaymentMethodModel.getAmount()));
        assertThat(actualExpenseOtherPaymentMethodModel.getExpenseId(),
                is(expectedExpenseOtherPaymentMethodModel.getExpenseId()));
        assertThat(actualExpenseOtherPaymentMethodModel.getOtherPaymentMethodId(),
                is(expectedExpenseOtherPaymentMethodModel.getOtherPaymentMethodId()));
    }
}
