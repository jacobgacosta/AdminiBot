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
import io.dojogeek.adminibot.models.IncomeBankCardModel;
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

        IncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

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

        IncomeBankCardModel incomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();
        incomeBankCardModel.setDescription(null);

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(incomeBankCardModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetMovementIncomeBankCardById_successObtaining() throws DataException {

        IncomeBankCardModel expectedIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(expectedIncomeBankCardModel);

        IncomeBankCardModel actualIncomeBankCardModel =
                mIncomeBankCardDao.getMovementIncomeBankCardById(insertedRecordId);

        assertThat(actualIncomeBankCardModel, is(notNullValue()));
        assertThat(actualIncomeBankCardModel.getAmount(), is(expectedIncomeBankCardModel.getAmount()));
        assertThat(actualIncomeBankCardModel.getBankCardId(), is(expectedIncomeBankCardModel.getBankCardId()));
        assertThat(actualIncomeBankCardModel.getDate(), is(expectedIncomeBankCardModel.getDate()));
        assertThat(actualIncomeBankCardModel.getDescription(), is(expectedIncomeBankCardModel.getDescription()));
        assertThat(actualIncomeBankCardModel.getIncomeId(), is(expectedIncomeBankCardModel.getIncomeId()));
    }

    @Test(expected = DataException.class)
    public void testGetMovementIncomeBankCardById_withNonExistentId_isException() throws DataException {

        long nonExistentMovementIncomeBankCardId = 4;

        mIncomeBankCardDao.getMovementIncomeBankCardById(nonExistentMovementIncomeBankCardId);

    }

    @Test
    public void testGetMovementsIncomesBankCards_successObtainingList() {

        int numberOfInsertions = 5;

        List<IncomeBankCardModel> expectedIncomeBankCardModels = createMovementsIncomesBankCards(numberOfInsertions);

        List<IncomeBankCardModel> actualIncomeBankCardModels = mIncomeBankCardDao.getMovementsIncomesBankCards();

        compareMovementsIncomesBankCardsModelsList(expectedIncomeBankCardModels, actualIncomeBankCardModels);

    }

    @Test
    public void testGetMovementsIncomesBankCards_withNoRecords_isEmptyList() {

        List<IncomeBankCardModel> incomeBankCardModelList = mIncomeBankCardDao.getMovementsIncomesBankCards();

        assertThat(incomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsIncomesBankCardsByIncomeId_successObtainingList() {

        int numberOfInsertions = 5;

        int incomeId = 2;

        List<IncomeBankCardModel> expectedIncomeBankCardModelList =  createMovementsIncomesBankCards(numberOfInsertions);

        List<IncomeBankCardModel> actualIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByIncomeId(incomeId);

        compareMovementsIncomesBankCardsModelsList(expectedIncomeBankCardModelList, actualIncomeBankCardModelList);
    }

    @Test
    public void testGetMovementsIncomesBankCardsByIncomeId_withNonExistentId_isEmptyList() {

        long incomeId = 0;

        List<IncomeBankCardModel> actualIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByIncomeId(incomeId);

        assertThat(actualIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testGetMovementsIncomesBankCardsByBankCardId_successObtainingList() {

        int numberOfInsertions = 4;
        long bankCardId = 2;

        List<IncomeBankCardModel> expectedIncomeBankCardModelList =
                createMovementsIncomesBankCards(numberOfInsertions);

        List<IncomeBankCardModel> actualIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByBankCardId(bankCardId);

        compareMovementsIncomesBankCardsModelsList(expectedIncomeBankCardModelList, actualIncomeBankCardModelList);

    }

    @Test
    public void testGetMovementsIncomesBankCardsByBankCardId_withNonExistentBankCardId_isEmptyList() {

        int nonExistentId = 0;

        List<IncomeBankCardModel> actualIncomeBankCardModelList =
                mIncomeBankCardDao.getMovementsIncomesBankCardsByBankCardId(nonExistentId);

        assertThat(actualIncomeBankCardModelList.isEmpty(), is(true));
    }

    @Test
    public void testUpdateMovementIncomeBankCard_successUpdating() throws DataException {

        IncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        IncomeBankCardModel updatedIncomeBankCardModel = changeMovementIncomeBankCardFields(movementIncomeBankCard);

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + insertedRecordId;

        long updatedRows = mIncomeBankCardDao.updateMovementIncomeBankCard(updatedIncomeBankCardModel, where);

        assertThat(updatedRows, is(SUCCESS_OPERATION));

        IncomeBankCardModel actualIncomeBankCardModel =
                mIncomeBankCardDao.getMovementIncomeBankCardById(insertedRecordId);

        compareMovementsIncomesBankCardsModels(updatedIncomeBankCardModel, actualIncomeBankCardModel);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateMovementIncomeBankCard_withNullModel_isException() {

        IncomeBankCardModel updatedIncomeBankCardModel = null;

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + 2;

        mIncomeBankCardDao.updateMovementIncomeBankCard(updatedIncomeBankCardModel, where);

    }

    @Test
    public void testUpdateMovementIncomeBankCard_withNullWhereSentence_noUpdating() {

        IncomeBankCardModel updatedIncomeBankCardModel = CreatorModels.createMovementIncomeBankCardModel();

        String where = null;

        long updatedRows = mIncomeBankCardDao.updateMovementIncomeBankCard(updatedIncomeBankCardModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateMovementIncomeBankCard_withNullRequiredField_isException() {

        IncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

        long insertedRecordId = mIncomeBankCardDao.createMovementIncomeBankCard(movementIncomeBankCard);

        movementIncomeBankCard.setDescription(null);

        String where = IncomesBankCardsContract.IncomesBankCards._ID + "= " + insertedRecordId;

        long updatedRows = mIncomeBankCardDao.updateMovementIncomeBankCard(movementIncomeBankCard, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test
    public void testDeleteMovementIncomeBankCard_successDeletion() {

        IncomeBankCardModel movementIncomeBankCard = CreatorModels.createMovementIncomeBankCardModel();

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

    private List<IncomeBankCardModel> createMovementsIncomesBankCards(int numberOfInsertions) {

        List<IncomeBankCardModel> incomeBankCardModels = new ArrayList<>();

        for (int index = 0; index < numberOfInsertions; index++) {

            IncomeBankCardModel incomeBankCardModel =
                    CreatorModels.createMovementIncomeBankCardModel(3409.80 + index, 2,
                            DateUtils.getCurrentData(), "Test description " + index, 2);

            mIncomeBankCardDao.createMovementIncomeBankCard(incomeBankCardModel);
            incomeBankCardModels.add(incomeBankCardModel);
        }

        return incomeBankCardModels;
    }

    private void compareMovementsIncomesBankCardsModelsList(List<IncomeBankCardModel> expectedIncomeBankCardModels,
                                                            List<IncomeBankCardModel> actualIncomeBankCardModels) {

        assertThat(actualIncomeBankCardModels, is(notNullValue()));
        assertThat(actualIncomeBankCardModels.isEmpty(), is(false));
        assertThat(actualIncomeBankCardModels.size(), is(expectedIncomeBankCardModels.size()));

        for (int index = 0; index < actualIncomeBankCardModels.size(); index++) {

            IncomeBankCardModel actualIncomeBankCardModel =
                    actualIncomeBankCardModels.get(index);

            IncomeBankCardModel expectedIncomeBankCardModel =
                    expectedIncomeBankCardModels.get(index);

            compareMovementsIncomesBankCardsModels(expectedIncomeBankCardModel, actualIncomeBankCardModel);
        }

    }

    private void compareMovementsIncomesBankCardsModels(IncomeBankCardModel expectedIncomeBankCardModel,
                                                        IncomeBankCardModel actualIncomeBankCardModel) {

        assertThat(actualIncomeBankCardModel, is(notNullValue()));
        assertThat(actualIncomeBankCardModel.getIncomeId(), is(expectedIncomeBankCardModel.getIncomeId()));
        assertThat(actualIncomeBankCardModel.getDescription(), is(expectedIncomeBankCardModel.getDescription()));
        assertThat(actualIncomeBankCardModel.getDate(), is(expectedIncomeBankCardModel.getDate()));
        assertThat(actualIncomeBankCardModel.getBankCardId(), is(expectedIncomeBankCardModel.getBankCardId()));
        assertThat(actualIncomeBankCardModel.getAmount(), is(expectedIncomeBankCardModel.getAmount()));

    }

    private IncomeBankCardModel changeMovementIncomeBankCardFields(IncomeBankCardModel incomeBankCardModel) {

        incomeBankCardModel.setAmount(309.00);
        incomeBankCardModel.setBankCardId(2);
        incomeBankCardModel.setDate(DateUtils.getCurrentData());
        incomeBankCardModel.setDescription("updated description");
        incomeBankCardModel.setIncomeId(3);

        return incomeBankCardModel;
    }
}
