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
import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;
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
        ((ExpenseBankCardDaoImpl)mExpenseBankCardDao).closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateMovementExpenseBankCard_successInsertion() {

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMovementExpenseBankCard_withNullModel_isException() {

        mExpenseBankCardDao.createMovementExpenseBankCard(null);

    }

    @Test
    public void testCreateMovementExpenseBankCard_withNullRequiredField_noInsertion() {

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();
        movementExpenseBankCardModel.description = null;

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetMovementExpenseBankCardById_successObtaining() throws DataException {

        MovementExpenseBankCardModel expectedMovementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(expectedMovementExpenseBankCardModel);

        MovementExpenseBankCardModel actualMovementExpenseBankCardModel = mExpenseBankCardDao.
                getMovementExpenseBankCardById(insertedRecordId);

        assertThat(actualMovementExpenseBankCardModel, notNullValue());
        assertThat(actualMovementExpenseBankCardModel.amount, is(expectedMovementExpenseBankCardModel.amount));
        assertThat(actualMovementExpenseBankCardModel.bankCardId, is(expectedMovementExpenseBankCardModel.bankCardId));
        assertThat(actualMovementExpenseBankCardModel.date, is(expectedMovementExpenseBankCardModel.date));
        assertThat(actualMovementExpenseBankCardModel.description, is(expectedMovementExpenseBankCardModel.description));
        assertThat(actualMovementExpenseBankCardModel.expenseId, is(expectedMovementExpenseBankCardModel.expenseId));
    }

    @Test(expected = DataException.class)
    public void testGetMovementExpenseBankCardById_withNonExistentId_isException() throws DataException {

        int movementExpenseBankCardId = 2;
        mExpenseBankCardDao.getMovementExpenseBankCardById(movementExpenseBankCardId);

    }

    @Test
    public void getGetMovementsExpensesBankCards_successObtainingList() {

        int numberOfInsertions = 7;

        List<MovementExpenseBankCardModel> expectedMovementExpenseBankCardModels = createMovementExpenseBankCard(numberOfInsertions);

        List<MovementExpenseBankCardModel> actualMovementExpenseBankCardModels = mExpenseBankCardDao.getMovementsExpensesBankCards();

        compareMovementsExpensesBankCardsModelsList(expectedMovementExpenseBankCardModels, actualMovementExpenseBankCardModels);
    }

    @Test
    public void testGetMovementsExpensesBankCards_withNoRecords_isEmptyList() {

        List<MovementExpenseBankCardModel> movementExpenseBankCardModelList =
                mExpenseBankCardDao.getMovementsExpensesBankCards();

        assertThat(movementExpenseBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsExpensesBankCardsByExpenseId_successObtaining() {

        int numberOfInsertions = 7;
        int expenseId = 2;

        List<MovementExpenseBankCardModel> expectedMovementExpenseBankCardModels = createMovementExpenseBankCard(numberOfInsertions);

        List<MovementExpenseBankCardModel> actualMovementExpenseBankCardModels =
                mExpenseBankCardDao.getMovementsExpensesBankCardsByExpenseId(expenseId);

        compareMovementsExpensesBankCardsModelsList(expectedMovementExpenseBankCardModels, actualMovementExpenseBankCardModels);
    }

    @Test
    public void testGetMovementsExpensesBankCardsByExpenseId_withNonExistentExpenseId_isEmptyList() {

        long nonExistentExpenseId = 4;

        List<MovementExpenseBankCardModel> movementExpenseBankCardModelList =
                mExpenseBankCardDao.getMovementsExpensesBankCardsByExpenseId(nonExistentExpenseId);

        assertThat(movementExpenseBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementExpenseBankCardByBankCardId_successObtaining() {

        int numberOfInsertions = 4;
        long bankCardId = 2;

        List<MovementExpenseBankCardModel> expectedMovementExpenseBankCardModelList =
                createMovementExpenseBankCard(numberOfInsertions);

        List<MovementExpenseBankCardModel> actualMovementExpenseBankCardModelList =
                mExpenseBankCardDao.getMovementExpenseBankCardByBankCardId(bankCardId);

        compareMovementsExpensesBankCardsModelsList(expectedMovementExpenseBankCardModelList,
                actualMovementExpenseBankCardModelList);
    }

    @Test
    public void testGetMovementExpenseBankCardByBankCardId_withNonExistentBankCardId_isEmptyList() {

        long bankCardId = 4;

        List<MovementExpenseBankCardModel> actualMovementExpenseBankCardModelList =
                mExpenseBankCardDao.getMovementExpenseBankCardByBankCardId(bankCardId);

        assertThat(actualMovementExpenseBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testUpdateMovementExpenseBankCard_successUpdating() throws DataException {

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);

        String where = ExpensesBankCardsContract.ExpensesBankCard._ID + "= " + insertedRecordId;

        MovementExpenseBankCardModel updatedMovementExpenseBankCardModel =
                changeMovementExpenseBankCardModelFields(movementExpenseBankCardModel);

        long updatedRows = mExpenseBankCardDao.updateMovementExpenseBankCard(updatedMovementExpenseBankCardModel, where);

        assertThat(updatedRows, is(not(OPERATIONAL_ERROR)));

        MovementExpenseBankCardModel actualMovementExpenseBankCardModel = mExpenseBankCardDao.
                getMovementExpenseBankCardById(updatedRows);

        compareMovementExpenseBankCardModels(updatedMovementExpenseBankCardModel, actualMovementExpenseBankCardModel);
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateMovementExpenseBankCard_withNullRequiredField_isException() {

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);

        String where = ExpensesBankCardsContract.ExpensesBankCard._ID + "= " + insertedRecordId;

        movementExpenseBankCardModel.description = null;

        mExpenseBankCardDao.updateMovementExpenseBankCard(movementExpenseBankCardModel, where);

    }

    @Test
    public void testUpdateMovementExpenseBankCard_withNonExistentId_noUpdating() {

        int inexistentId = 4;

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        String where = ExpensesBankCardsContract.ExpensesBankCard._ID + "= " + inexistentId;

        long updatedRows = mExpenseBankCardDao.updateMovementExpenseBankCard(movementExpenseBankCardModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDeleteMovementExpenseBankCard_successDeletion() {

        MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.createMovementExpenseBankCardModel();

        long insertedRecordId = mExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);

        long deletedRows = mExpenseBankCardDao.deleteMovementExpenseBankCard(insertedRecordId);

        assertThat(deletedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testDeleteMovementExpenseBankCard_withNonexistentId_noDeletion() {

        long nonExistentId = 2;

        long deletedRows = mExpenseBankCardDao.deleteMovementExpenseBankCard(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));

    }

    private List<MovementExpenseBankCardModel> createMovementExpenseBankCard(int numberOfInsertions) {

        List<MovementExpenseBankCardModel> movementExpenseBankCardModels = new ArrayList<>();

        for (int index = 1; index <= numberOfInsertions; index++) {

            MovementExpenseBankCardModel movementExpenseBankCardModel = CreatorModels.
                    createMovementExpenseBankCardModel(2340 + index,
                            2, DateUtils.getCurrentData(), "test description" + index, 2);

            mExpenseBankCardDao.createMovementExpenseBankCard(movementExpenseBankCardModel);
            movementExpenseBankCardModels.add(movementExpenseBankCardModel);
        }

        return movementExpenseBankCardModels;
    }

    private void compareMovementsExpensesBankCardsModelsList(List<MovementExpenseBankCardModel> expectedMovementExpenseBankCardModels,
                                                             List<MovementExpenseBankCardModel> actualMovementExpenseBankCardModels) {

        assertNotNull(actualMovementExpenseBankCardModels);
        assertThat(actualMovementExpenseBankCardModels, is(not(nullValue())));
        assertThat(actualMovementExpenseBankCardModels.size(), is(expectedMovementExpenseBankCardModels.size()));

        for (int index = 0; index < actualMovementExpenseBankCardModels.size(); index++) {

            MovementExpenseBankCardModel actualMovementExpenseBankCardModel =
                    actualMovementExpenseBankCardModels.get(index);

            MovementExpenseBankCardModel expectedMovementExpenseBankCardModel =
                    expectedMovementExpenseBankCardModels.get(index);

            compareMovementExpenseBankCardModels(expectedMovementExpenseBankCardModel, actualMovementExpenseBankCardModel);
        }

    }

    private void compareMovementExpenseBankCardModels(MovementExpenseBankCardModel expectedMovementExpenseBankCardModel,
                                                      MovementExpenseBankCardModel actualMovementExpenseBankCardModel) {

        assertNotNull(actualMovementExpenseBankCardModel);
        assertThat(expectedMovementExpenseBankCardModel.description, is(actualMovementExpenseBankCardModel.description));
        assertThat(expectedMovementExpenseBankCardModel.expenseId, is(actualMovementExpenseBankCardModel.expenseId));
        assertThat(expectedMovementExpenseBankCardModel.date, is(actualMovementExpenseBankCardModel.date));
        assertThat(expectedMovementExpenseBankCardModel.bankCardId, is(actualMovementExpenseBankCardModel.bankCardId));
        assertThat(expectedMovementExpenseBankCardModel.amount, is(actualMovementExpenseBankCardModel.amount));

    }

    private MovementExpenseBankCardModel changeMovementExpenseBankCardModelFields(MovementExpenseBankCardModel
                                                                                          movementExpenseBankCardModel) {

        movementExpenseBankCardModel.bankCardId = 3;
        movementExpenseBankCardModel.date = DateUtils.getCurrentData();
        movementExpenseBankCardModel.description = "updated description";
        movementExpenseBankCardModel.expenseId = 2;
        movementExpenseBankCardModel.amount = 98743.90;

        return movementExpenseBankCardModel;
    }
}
