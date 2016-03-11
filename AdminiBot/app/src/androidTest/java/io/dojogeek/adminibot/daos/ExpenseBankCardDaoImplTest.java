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
import io.dojogeek.adminibot.models.ExpenseBankCardModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.ExpensesBankCardsContract;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class ExpenseBankCardDaoImplTest {

    private static final long SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;

    private Context mContext;
    private ExpenseBankCardDao mExpenseBankCardDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mExpenseBankCardDao = new ExpenseBankCardDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((ExpenseBankCardDaoImpl)mExpenseBankCardDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateMovementExpenseBankCard_successInsertion() {

        ExpenseBankCardModel expenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(expenseBankCardModel);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMovementExpenseBankCard_withNullModel_isException() {

        mExpenseBankCardDao.createMovementExpenseBankCard(null);

    }

    @Test
    public void testCreateMovementExpenseBankCard_withNullRequiredField_noInsertion() {

        ExpenseBankCardModel expenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();
        expenseBankCardModel.setDescription(null);

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(expenseBankCardModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetMovementExpenseBankCardById_successObtaining() throws DataException {

        ExpenseBankCardModel expectedExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(expectedExpenseBankCardModel);

        ExpenseBankCardModel actualExpenseBankCardModel = mExpenseBankCardDao.
                getMovementExpenseBankCardById(insertedRecordId);

        assertThat(actualExpenseBankCardModel, notNullValue());
        assertThat(actualExpenseBankCardModel.getAmount(), is(expectedExpenseBankCardModel.getAmount()));
        assertThat(actualExpenseBankCardModel.getBankCardId(), is(expectedExpenseBankCardModel.getBankCardId()));
        assertThat(actualExpenseBankCardModel.getDate(), is(expectedExpenseBankCardModel.getDate()));
        assertThat(actualExpenseBankCardModel.getDescription(), is(expectedExpenseBankCardModel.getDescription()));
        assertThat(actualExpenseBankCardModel.getExpenseId(), is(expectedExpenseBankCardModel.getExpenseId()));
    }

    @Test(expected = DataException.class)
    public void testGetMovementExpenseBankCardById_withNonExistentId_isException() throws DataException {

        int movementExpenseBankCardId = 2;
        mExpenseBankCardDao.getMovementExpenseBankCardById(movementExpenseBankCardId);

    }

    @Test
    public void getGetMovementsExpensesBankCards_successObtainingList() {

        int numberOfInsertions = 7;

        List<ExpenseBankCardModel> expectedExpenseBankCardModels = createMovementExpenseBankCard(numberOfInsertions);

        List<ExpenseBankCardModel> actualExpenseBankCardModels = mExpenseBankCardDao.getMovementsExpensesBankCards();

        compareMovementsExpensesBankCardsModelsList(expectedExpenseBankCardModels, actualExpenseBankCardModels);
    }

    @Test
    public void testGetMovementsExpensesBankCards_withNoRecords_isEmptyList() {

        List<ExpenseBankCardModel> expenseBankCardModelList =
                mExpenseBankCardDao.getMovementsExpensesBankCards();

        assertThat(expenseBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsExpensesBankCardsByExpenseId_successObtaining() {

        int numberOfInsertions = 7;
        int expenseId = 2;

        List<ExpenseBankCardModel> expectedExpenseBankCardModels = createMovementExpenseBankCard(numberOfInsertions);

        List<ExpenseBankCardModel> actualExpenseBankCardModels =
                mExpenseBankCardDao.getMovementsExpensesBankCardsByExpenseId(expenseId);

        compareMovementsExpensesBankCardsModelsList(expectedExpenseBankCardModels, actualExpenseBankCardModels);
    }

    @Test
    public void testGetMovementsExpensesBankCardsByExpenseId_withNonExistentExpenseId_isEmptyList() {

        long nonExistentExpenseId = 4;

        List<ExpenseBankCardModel> expenseBankCardModelList =
                mExpenseBankCardDao.getMovementsExpensesBankCardsByExpenseId(nonExistentExpenseId);

        assertThat(expenseBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementExpenseBankCardByBankCardId_successObtaining() {

        int numberOfInsertions = 4;
        long bankCardId = 2;

        List<ExpenseBankCardModel> expectedExpenseBankCardModelList =
                createMovementExpenseBankCard(numberOfInsertions);

        List<ExpenseBankCardModel> actualExpenseBankCardModelList =
                mExpenseBankCardDao.getMovementExpenseBankCardByBankCardId(bankCardId);

        compareMovementsExpensesBankCardsModelsList(expectedExpenseBankCardModelList,
                actualExpenseBankCardModelList);
    }

    @Test
    public void testGetMovementExpenseBankCardByBankCardId_withNonExistentBankCardId_isEmptyList() {

        long bankCardId = 4;

        List<ExpenseBankCardModel> actualExpenseBankCardModelList =
                mExpenseBankCardDao.getMovementExpenseBankCardByBankCardId(bankCardId);

        assertThat(actualExpenseBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testUpdateMovementExpenseBankCard_successUpdating() throws DataException {

        ExpenseBankCardModel expenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(expenseBankCardModel);

        String where = ExpensesBankCardsContract.ExpensesBankCard._ID + "= " + insertedRecordId;

        ExpenseBankCardModel updatedExpenseBankCardModel =
                changeMovementExpenseBankCardModelFields(expenseBankCardModel);

        long updatedRows = mExpenseBankCardDao.updateMovementExpenseBankCard(updatedExpenseBankCardModel, where);

        assertThat(updatedRows, is(not(OPERATIONAL_ERROR)));

        ExpenseBankCardModel actualExpenseBankCardModel = mExpenseBankCardDao.
                getMovementExpenseBankCardById(updatedRows);

        compareMovementExpenseBankCardModels(updatedExpenseBankCardModel, actualExpenseBankCardModel);
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateMovementExpenseBankCard_withNullRequiredField_isException() {

        ExpenseBankCardModel expenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(expenseBankCardModel);

        String where = ExpensesBankCardsContract.ExpensesBankCard._ID + "= " + insertedRecordId;

        expenseBankCardModel.setDescription(null);

        mExpenseBankCardDao.updateMovementExpenseBankCard(expenseBankCardModel, where);

    }

    @Test
    public void testUpdateMovementExpenseBankCard_withNonExistentId_noUpdating() {

        int inexistentId = 4;

        ExpenseBankCardModel expenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        String where = ExpensesBankCardsContract.ExpensesBankCard._ID + "= " + inexistentId;

        long updatedRows = mExpenseBankCardDao.updateMovementExpenseBankCard(expenseBankCardModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDeleteMovementExpenseBankCard_successDeletion() {

        ExpenseBankCardModel expenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(expenseBankCardModel);

        long deletedRows = mExpenseBankCardDao.deleteMovementExpenseBankCard(insertedRecordId);

        assertThat(deletedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testDeleteMovementExpenseBankCard_withNonexistentId_noDeletion() {

        long nonExistentId = 2;

        long deletedRows = mExpenseBankCardDao.deleteMovementExpenseBankCard(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));

    }

    private List<ExpenseBankCardModel> createMovementExpenseBankCard(int numberOfInsertions) {

        List<ExpenseBankCardModel> expenseBankCardModels = new ArrayList<>();

        for (int index = 1; index <= numberOfInsertions; index++) {

            ExpenseBankCardModel expenseBankCardModel = CreatorModels.
                    createMovementExpenseBankCardModel(2340 + index,
                            2, DateUtils.getCurrentData(), "test description" + index, 2);

            mExpenseBankCardDao.createMovementExpenseBankCard(expenseBankCardModel);
            expenseBankCardModels.add(expenseBankCardModel);
        }

        return expenseBankCardModels;
    }

    private void compareMovementsExpensesBankCardsModelsList(List<ExpenseBankCardModel> expectedExpenseBankCardModels,
                                                             List<ExpenseBankCardModel> actualExpenseBankCardModels) {

        assertNotNull(actualExpenseBankCardModels);
        assertThat(actualExpenseBankCardModels, is(not(nullValue())));
        assertThat(actualExpenseBankCardModels.size(), is(expectedExpenseBankCardModels.size()));

        for (int index = 0; index < actualExpenseBankCardModels.size(); index++) {

            ExpenseBankCardModel actualExpenseBankCardModel =
                    actualExpenseBankCardModels.get(index);

            ExpenseBankCardModel expectedExpenseBankCardModel =
                    expectedExpenseBankCardModels.get(index);

            compareMovementExpenseBankCardModels(expectedExpenseBankCardModel, actualExpenseBankCardModel);
        }

    }

    private void compareMovementExpenseBankCardModels(ExpenseBankCardModel expectedExpenseBankCardModel,
                                                      ExpenseBankCardModel actualExpenseBankCardModel) {

        assertNotNull(actualExpenseBankCardModel);
        assertThat(expectedExpenseBankCardModel.getDescription(), is(actualExpenseBankCardModel.getDescription()));
        assertThat(expectedExpenseBankCardModel.getExpenseId(), is(actualExpenseBankCardModel.getExpenseId()));
        assertThat(expectedExpenseBankCardModel.getDate(), is(actualExpenseBankCardModel.getDate()));
        assertThat(expectedExpenseBankCardModel.getBankCardId(), is(actualExpenseBankCardModel.getBankCardId()));
        assertThat(expectedExpenseBankCardModel.getAmount(), is(actualExpenseBankCardModel.getAmount()));

    }

    private ExpenseBankCardModel changeMovementExpenseBankCardModelFields(ExpenseBankCardModel
                                                                                  expenseBankCardModel) {

        expenseBankCardModel.setBankCardId(3);
        expenseBankCardModel.setDate(DateUtils.getCurrentData());
        expenseBankCardModel.setDescription("updated description");
        expenseBankCardModel.setExpenseId(2);
        expenseBankCardModel.setAmount(98743.90);

        return expenseBankCardModel;
    }
}
