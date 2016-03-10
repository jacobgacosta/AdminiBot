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
public class IncomeBankCardDaoImplTest {

    private static final long SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;

    private Context mContext;
    private IncomeBankCardDao mIncomeBankCardDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomeBankCardDao = new IncomeBankCardDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((IncomeBankCardDaoImpl)mIncomeBankCardDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateMovementIncomeBankCard_successInsertion() {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        assertThat(insertedRecordId, is(not(NO_OPERATION)));
        assertThat(insertedRecordId, is(not(OPERATIONAL_ERROR)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMovementIncomeBankCard_withNullModel_isException() {

        mIncomeBankCardDao.createMovementIncomeBankCard(null);

    }

    @Test
    public void testCreateMovementIncomeBankCard_withNullRequiredField_noInsertion() {

        MovementIncomeBankCardModel movementIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();
        movementIncomeBankCardModel.setDescription(null);

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCardModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetMovementIncomeBankCardById_successObtaining() throws DataException {

        MovementIncomeBankCardModel expectedMovementIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(expectedMovementIncomeBankCardModel);

        MovementIncomeBankCardModel actualMovementIncomeBankCardModel =
                mIncomeBankCardDao.getMovementIncomeBankCardById(insertedRecordId);

        assertThat(actualMovementIncomeBankCardModel, is(notNullValue()));
        assertThat(actualMovementIncomeBankCardModel.getAmount(), is(expectedMovementIncomeBankCardModel.getAmount()));
        assertThat(actualMovementIncomeBankCardModel.getBankCardId(), is(expectedMovementIncomeBankCardModel.getBankCardId()));
        assertThat(actualMovementIncomeBankCardModel.getDate(), is(expectedMovementIncomeBankCardModel.getDate()));
        assertThat(actualMovementIncomeBankCardModel.getDescription(), is(expectedMovementIncomeBankCardModel.getDescription()));
        assertThat(actualMovementIncomeBankCardModel.getIncomeId(), is(expectedMovementIncomeBankCardModel.getIncomeId()));
    }

    @Test(expected = DataException.class)
    public void testGetMovementIncomeBankCardById_withNonExistentId_isException() throws DataException {

        long nonExistentMovementIncomeBankCardId = 4;

        mIncomeBankCardDao.getMovementIncomeBankCardById(nonExistentMovementIncomeBankCardId);

    }

    @Test
    public void testGetMovementsIncomesBankCards_successObtainingList() {

        int numberOfInsertions = 5;

        List<MovementIncomeBankCardModel> expectedMovementIncomeBankCardModels = createMovementsIncomesBankCards(numberOfInsertions);

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModels = mIncomeBankCardDao.getMovementsIncomesBankCards();

        compareMovementsIncomesBankCardsModelsList(expectedMovementIncomeBankCardModels, actualMovementIncomeBankCardModels);

    }

    @Test
    public void testGetMovementsIncomesBankCards_withNoRecords_isEmptyList() {

        List<MovementIncomeBankCardModel> movementIncomeBankCardModelList = mIncomeBankCardDao.getMovementsIncomesBankCards();

        assertThat(movementIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsIncomesBankCardsByIncomeId_successObtainingList() {

        int numberOfInsertions = 5;

        int incomeId = 2;

        List<MovementIncomeBankCardModel> expectedMovementIncomeBankCardModelList =  createMovementsIncomesBankCards(numberOfInsertions);

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByIncomeId(incomeId);

        compareMovementsIncomesBankCardsModelsList(expectedMovementIncomeBankCardModelList, actualMovementIncomeBankCardModelList);
    }

    @Test
    public void testGetMovementsIncomesBankCardsByIncomeId_withNonExistentId_isEmptyList() {

        long incomeId = 0;

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByIncomeId(incomeId);

        assertThat(actualMovementIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsIncomesBankCardsByBankCardId_successObtainingList() {

        int numberOfInsertions = 4;
        long bankCardId = 2;

        List<MovementIncomeBankCardModel> expectedMovementIncomeBankCardModelList =
                createMovementsIncomesBankCards(numberOfInsertions);

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByBankCardId(bankCardId);

        compareMovementsIncomesBankCardsModelsList(expectedMovementIncomeBankCardModelList, actualMovementIncomeBankCardModelList);

    }

    @Test
    public void testGetMovementsIncomesBankCardsByBankCardId_withNonExistentBankCardId_isEmptyList() {

        int nonExistentId = 0;

        List<MovementIncomeBankCardModel> actualMovementIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByBankCardId(nonExistentId);

        assertThat(actualMovementIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testUpdateMovementIncomeBankCard_successUpdating() throws DataException {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        MovementIncomeBankCardModel updatedMovementIncomeBankCardModel = changeMovementIncomeBankCardFields(movementIncomeBankCard);

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + insertedRecordId;

        long updatedRows = mIncomeBankCardDao.updateMovementIncomeBankCard(updatedMovementIncomeBankCardModel, where);

        assertThat(updatedRows, is(SUCCESS_OPERATION));

        MovementIncomeBankCardModel actualMovementIncomeBankCardModel =
                mIncomeBankCardDao.getMovementIncomeBankCardById(insertedRecordId);

        compareMovementsIncomesBankCardsModels(updatedMovementIncomeBankCardModel, actualMovementIncomeBankCardModel);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateMovementIncomeBankCard_withNullModel_isException() {

        MovementIncomeBankCardModel updatedMovementIncomeBankCardModel = null;

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + 2;

        mIncomeBankCardDao.updateMovementIncomeBankCard(updatedMovementIncomeBankCardModel, where);

    }

    @Test
    public void testUpdateMovementIncomeBankCard_withNullWhereSentence_noUpdating() {

        MovementIncomeBankCardModel updatedMovementIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();

        String where = null;

        long updatedRows = mIncomeBankCardDao.updateMovementIncomeBankCard(updatedMovementIncomeBankCardModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateMovementIncomeBankCard_withNullRequiredField_isException() {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        movementIncomeBankCard.setDescription(null);

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + insertedRecordId;

        long updatedRows = mIncomeBankCardDao.updateMovementIncomeBankCard(movementIncomeBankCard, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDeleteMovementIncomeBankCard_successDeletion() {

        MovementIncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        long deletedRows = mIncomeBankCardDao.deleteMovementIncomeBankCard(insertedRecordId);

        assertThat(deletedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testDeleteMovementIncomeBankCard_withNonExistentId_noDeletion() {

        long nonExistentId = 2;

        long deletedRows = mIncomeBankCardDao.deleteMovementIncomeBankCard(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    private List<MovementIncomeBankCardModel> createMovementsIncomesBankCards(int numberOfInsertions) {

        List<MovementIncomeBankCardModel> movementIncomeBankCardModels = new ArrayList<>();

        for (int index = 0; index < numberOfInsertions; index++) {

            MovementIncomeBankCardModel movementIncomeBankCardModel =
                    CreatorModels.createMovementIncomeBankCardModel(3409.80 + index, 2,
                            DateUtils.getCurrentData(), "Test description " + index, 2);

            mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCardModel);
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
        assertThat(actualMovementIncomeBankCardModel.getIncomeId(), is(expectedMovementIncomeBankCardModel.getIncomeId()));
        assertThat(actualMovementIncomeBankCardModel.getDescription(), is(expectedMovementIncomeBankCardModel.getDescription()));
        assertThat(actualMovementIncomeBankCardModel.getDate(), is(expectedMovementIncomeBankCardModel.getDate()));
        assertThat(actualMovementIncomeBankCardModel.getBankCardId(), is(expectedMovementIncomeBankCardModel.getBankCardId()));
        assertThat(actualMovementIncomeBankCardModel.getAmount(), is(expectedMovementIncomeBankCardModel.getAmount()));

    }

    private MovementIncomeBankCardModel changeMovementIncomeBankCardFields(MovementIncomeBankCardModel movementIncomeBankCardModel) {

        movementIncomeBankCardModel.setAmount(309.00);
        movementIncomeBankCardModel.setBankCardId(2);
        movementIncomeBankCardModel.setDate(DateUtils.getCurrentData());
        movementIncomeBankCardModel.setDescription("updated description");
        movementIncomeBankCardModel.setIncomeId(3);

        return movementIncomeBankCardModel;
    }
}
