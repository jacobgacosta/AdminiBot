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
import io.dojogeek.adminibot.models.MovementIncomeBankCardModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.IncomesBankCardsContract;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MovementIncomeBankCardDaoImplTest {

    private static final long SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;

    private Context mContext;
    private MovementIncomeBankCardDao mMovementIncomeBankCardDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mMovementIncomeBankCardDao = new MovementIncomeBankCardDaoImpl(mContext);
        mMovementIncomeBankCardDao.openConection();
    }

    @After
    public void finishTest() {
        mMovementIncomeBankCardDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateMovementIncomeBankCard_successInsertion() {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mMovementIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMovementIncomeBankCard_withNullModel_isException() {

        mMovementIncomeBankCardDao.createMovementIncomeBankCard(null);

    }

    @Test
    public void testCreateMovementIncomeBankCard_withNullRequiredField_noInsertion() {

        MovementIncomeBankCardModel movementIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();
        movementIncomeBankCardModel.description = null;

        long insertedRecordId = mMovementIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCardModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetMovementIncomeBankCardById_successObtaining() throws DataException {

        MovementIncomeBankCardModel expectedMovementIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mMovementIncomeBankCardDao.createMovementIncomeBankCard(expectedMovementIncomeBankCardModel);

        MovementIncomeBankCardModel actualMovementIncomeBankCardModel =
                mMovementIncomeBankCardDao.getMovementIncomeBankCardById(insertedRecordId);

        assertThat(actualMovementIncomeBankCardModel, is(notNullValue()));
        assertThat(actualMovementIncomeBankCardModel.amount, is(expectedMovementIncomeBankCardModel.amount));
        assertThat(actualMovementIncomeBankCardModel.bankCardId, is(expectedMovementIncomeBankCardModel.bankCardId));
        assertThat(actualMovementIncomeBankCardModel.date, is(expectedMovementIncomeBankCardModel.date));
        assertThat(actualMovementIncomeBankCardModel.description, is(expectedMovementIncomeBankCardModel.description));
        assertThat(actualMovementIncomeBankCardModel.incomeId, is(expectedMovementIncomeBankCardModel.incomeId));
    }

    @Test(expected = DataException.class)
    public void testGetMovementIncomeBankCardById_withNonExistentId_isException() throws DataException {

        long nonExistentMovementIncomeBankCardId = 4;

        mMovementIncomeBankCardDao.getMovementIncomeBankCardById(nonExistentMovementIncomeBankCardId);

    }

    @Test
    public void testGetMovementsIncomesBankCards_successObtainingList() {

        int numberOfInsertions = 5;

        List<MovementIncomeBankCardModel> expectedMovementIncomeBankCardModels = createMovementsIncomesBankCards(numberOfInsertions);

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModels = mMovementIncomeBankCardDao.getMovementsIncomesBankCards();

        compareMovementsIncomesBankCardsModelsList(expectedMovementIncomeBankCardModels, actualMovementIncomeBankCardModels);

    }

    @Test
    public void testGetMovementsIncomesBankCards_withNoRecords_isEmptyList() {

        List<MovementIncomeBankCardModel> movementIncomeBankCardModelList = mMovementIncomeBankCardDao.getMovementsIncomesBankCards();

        assertThat(movementIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsIncomesBankCardsByIncomeId_successObtainingList() {

        int numberOfInsertions = 5;

        int incomeId = 2;

        List<MovementIncomeBankCardModel> expectedMovementIncomeBankCardModelList =  createMovementsIncomesBankCards(numberOfInsertions);

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mMovementIncomeBankCardDao.getMovementsIncomesBankCardsByIncomeId(incomeId);

        compareMovementsIncomesBankCardsModelsList(expectedMovementIncomeBankCardModelList, actualMovementIncomeBankCardModelList);
    }

    @Test
    public void testGetMovementsIncomesBankCardsByIncomeId_withNonExistentId_isEmptyList() {

        long incomeId = 0;

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mMovementIncomeBankCardDao.getMovementsIncomesBankCardsByIncomeId(incomeId);

        assertThat(actualMovementIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsIncomesBankCardsByBankCardId_successObtainingList() {

        int numberOfInsertions = 4;
        long bankCardId = 2;

        List<MovementIncomeBankCardModel> expectedMovementIncomeBankCardModelList =
                createMovementsIncomesBankCards(numberOfInsertions);

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mMovementIncomeBankCardDao.getMovementsIncomesBankCardsByBankCardId(bankCardId);

        compareMovementsIncomesBankCardsModelsList(expectedMovementIncomeBankCardModelList, actualMovementIncomeBankCardModelList);

    }

    @Test
    public void testGetMovementsIncomesBankCardsByBankCardId_withNonExistentBankCardId_isEmptyList() {

        int nonExistentId = 0;

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mMovementIncomeBankCardDao.getMovementsIncomesBankCardsByBankCardId(nonExistentId);

        assertThat(actualMovementIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testUpdateMovementIncomeBankCard_successUpdating() throws DataException {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mMovementIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        MovementIncomeBankCardModel updatedMovementIncomeBankCardModel = changeMovementIncomeBankCardFields(movementIncomeBankCard);

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + insertedRecordId;

        long updatedRows = mMovementIncomeBankCardDao.updateMovementIncomeBankCard(updatedMovementIncomeBankCardModel, where);

        assertThat(updatedRows, is(SUCCESS_OPERATION));

        MovementIncomeBankCardModel actualMovementIncomeBankCardModel =
                mMovementIncomeBankCardDao.getMovementIncomeBankCardById(insertedRecordId);

        compareMovementsIncomesBankCardsModels(updatedMovementIncomeBankCardModel, actualMovementIncomeBankCardModel);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateMovementIncomeBankCard_withNullModel_isException() {

        MovementIncomeBankCardModel updatedMovementIncomeBankCardModel = null;

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + 2;

        mMovementIncomeBankCardDao.updateMovementIncomeBankCard(updatedMovementIncomeBankCardModel, where);

    }

    @Test
    public void testUpdateMovementIncomeBankCard_withNullWhereSentence_noUpdating() {

        MovementIncomeBankCardModel updatedMovementIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();

        String where = null;

        long updatedRows = mMovementIncomeBankCardDao.updateMovementIncomeBankCard(updatedMovementIncomeBankCardModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateMovementIncomeBankCard_withNullRequiredField_isException() {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mMovementIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        movementIncomeBankCard.description = null;

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + insertedRecordId;

        long updatedRows = mMovementIncomeBankCardDao.updateMovementIncomeBankCard(movementIncomeBankCard, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDeleteMovementIncomeBankCard_successDeletion() {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mMovementIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        long deletedRows = mMovementIncomeBankCardDao.deleteMovementIncomeBankCard(insertedRecordId);

        assertThat(deletedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testDeleteMovementIncomeBankCard_withNonExistentId_noDeletion() {

        long nonExistentId = 2;

        long deletedRows = mMovementIncomeBankCardDao.deleteMovementIncomeBankCard(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    private List<MovementIncomeBankCardModel> createMovementsIncomesBankCards(int numberOfInsertions) {

        List<MovementIncomeBankCardModel> movementIncomeBankCardModels = new ArrayList<>();

        for (int index = 0; index < numberOfInsertions; index++) {

            MovementIncomeBankCardModel movementIncomeBankCardModel =
                    CreatorModels.createMovementIncomeBankCardModel(3409.80 + index, 2,
                            DateUtils.getCurrentData(), "Test description " + index, 2);

            mMovementIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCardModel);
            movementIncomeBankCardModels.add(movementIncomeBankCardModel);
        }

        return movementIncomeBankCardModels;
    }

    private void compareMovementsIncomesBankCardsModelsList(List<MovementIncomeBankCardModel> expectedMovementIncomeBankCardModels,
                                                            List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModels) {

        assertThat(actualMovementIncomeBankCardModels, is(notNullValue()));
        assertThat(actualMovementIncomeBankCardModels.isEmpty(), is(false));
        assertThat(actualMovementIncomeBankCardModels.size(), is(expectedMovementIncomeBankCardModels.size()));

        for (int index = 0; index < actualMovementIncomeBankCardModels.size(); index++) {

            MovementIncomeBankCardModel actualMovementIncomeBankCardModel =
                    actualMovementIncomeBankCardModels.get(index);

            MovementIncomeBankCardModel expectedMovementIncomeBankCardModel =
                    expectedMovementIncomeBankCardModels.get(index);

            compareMovementsIncomesBankCardsModels(expectedMovementIncomeBankCardModel, actualMovementIncomeBankCardModel);
        }

    }

    private void compareMovementsIncomesBankCardsModels(MovementIncomeBankCardModel expectedMovementIncomeBankCardModel,
                                                        MovementIncomeBankCardModel actualMovementIncomeBankCardModel) {

        assertThat(actualMovementIncomeBankCardModel, is(notNullValue()));
        assertThat(actualMovementIncomeBankCardModel.incomeId, is(expectedMovementIncomeBankCardModel.incomeId));
        assertThat(actualMovementIncomeBankCardModel.description, is(expectedMovementIncomeBankCardModel.description));
        assertThat(actualMovementIncomeBankCardModel.date, is(expectedMovementIncomeBankCardModel.date));
        assertThat(actualMovementIncomeBankCardModel.bankCardId, is(expectedMovementIncomeBankCardModel.bankCardId));
        assertThat(actualMovementIncomeBankCardModel.amount, is(expectedMovementIncomeBankCardModel.amount));

    }

    private MovementIncomeBankCardModel changeMovementIncomeBankCardFields(MovementIncomeBankCardModel movementIncomeBankCardModel) {

        movementIncomeBankCardModel.amount = 309.00;
        movementIncomeBankCardModel.bankCardId = 2;
        movementIncomeBankCardModel.date = DateUtils.getCurrentData();
        movementIncomeBankCardModel.description = "updated description";
        movementIncomeBankCardModel.incomeId = 3;

        return movementIncomeBankCardModel;
    }
}
