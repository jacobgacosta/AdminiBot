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

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.BankCardsContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class BankCardDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;
    private BankCardDao mBankCardDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mBankCardDao = new BankCardDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((BankCardDaoImpl)mBankCardDao).closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateBankCard_successInsertion() {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(bankCardModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateBankCard_withNullModel_isException() {

        BankCardModel bankCardModel = null;

        mBankCardDao.createBankCard(bankCardModel);
    }

    @Test
    public void testCreateBankCard_withNullRequiredField_noInsertion() {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();
        bankCardModel.name = null;

        long insertedRecordId = mBankCardDao.createBankCard(bankCardModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetBankCardById_successObtaining() throws DataException {

        BankCardModel expectedBankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(expectedBankCardModel);

        BankCardModel actualBankCardModel = mBankCardDao.getBankCardById(insertedRecordId);

        assertNotNull(actualBankCardModel);
        assertEquals(expectedBankCardModel.name, actualBankCardModel.name);
        assertEquals(expectedBankCardModel.number, actualBankCardModel.number);
        assertEquals(expectedBankCardModel.bankId, actualBankCardModel.bankId);
        assertEquals(expectedBankCardModel.trademarkId, actualBankCardModel.trademarkId);
        assertEquals(expectedBankCardModel.availableCredit, actualBankCardModel.availableCredit, 0);
        assertEquals(expectedBankCardModel.cardType, actualBankCardModel.cardType);
        assertEquals(expectedBankCardModel.userId, actualBankCardModel.userId);

    }

    @Test(expected = DataException.class)
    public void testGetBankCardById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 2;

        mBankCardDao.getBankCardById(nonExistentId);

    }

    @Test
    public void testGetBankCards_successObtainingList() {

        int numberOfBankCardToCreate = 5;

        List<BankCardModel> expectedBankCardModels = createBankCardModels(numberOfBankCardToCreate);

        List<BankCardModel> actualBankCardModels = mBankCardDao.getBankCards();

        compareBankCardsList(expectedBankCardModels, actualBankCardModels);
    }

    @Test
    public void testGetBankCards_withNoRecords_isEmptyList() {

        List<BankCardModel> actualBankCardModels = mBankCardDao.getBankCards();

        assertThat(actualBankCardModels.isEmpty(), is(true));
    }

    @Test
    public void testUpdateBankCard_successUpdating() throws DataException {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(bankCardModel);

        String where = BankCardsContract.BankCard._ID + "= " +  insertedRecordId;

        BankCardModel expectedUpdatedBankCard = changeBankCardValues(bankCardModel);

        long updatedRows = mBankCardDao.updateBankCard(expectedUpdatedBankCard, where);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        BankCardModel actualUpdatedBankCardModel = mBankCardDao.getBankCardById(insertedRecordId);

        compareBankCards(expectedUpdatedBankCard, actualUpdatedBankCardModel);

    }

    @Test(expected = NullPointerException.class)
    public void testUpdateBankCard_withNullModel_isException() {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(bankCardModel);

        String where = BankCardsContract.BankCard._ID + "= " +  insertedRecordId;

        BankCardModel expectedUpdatedBankCard = null;

        mBankCardDao.updateBankCard(expectedUpdatedBankCard, where);

    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateBankCard_withNullRequiredField_isException() {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(bankCardModel);

        String where = BankCardsContract.BankCard._ID + "= " +  insertedRecordId;

        BankCardModel expectedUpdatedBankCard = changeBankCardValues(bankCardModel);
        expectedUpdatedBankCard.name = null;

        mBankCardDao.updateBankCard(expectedUpdatedBankCard, where);

    }

    @Test
    public void testUpdateBankCard_withNonExistentId_noUpdating() {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();

        long nonExistentId = 4;

        String where = BankCardsContract.BankCard._ID + "= " +  nonExistentId;

        long updatedRows = mBankCardDao.updateBankCard(bankCardModel, where);

        assertThat(updatedRows, is(NO_OPERATION));

    }

    @Test
    public void testDeleteBankCard_successDeletion() {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(bankCardModel);

        long deletedRows = mBankCardDao.deleteBankCard(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDeleteBankCard_withNonExistentId_noDeletion() {

        long nonExistentId = 5;

        long deletedRows = mBankCardDao.deleteBankCard(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    @Test
    public void testGetBankCardByCartType_successObtaining() {

        int numberOfBankCardToCreate = 4;

        List<BankCardModel> expectedBankCardsModels = createBankCardModels(numberOfBankCardToCreate);

        List<BankCardModel> actualBankCardsModels = mBankCardDao.getBankCardByCartType(CardTypeEnum.DEBIT_CARD);

        compareBankCardsList(expectedBankCardsModels, actualBankCardsModels);

    }

    @Test(expected = NullPointerException.class)
    public void testGetBankCardByCartType_withNullCardType_isException() {

        mBankCardDao.getBankCardByCartType(null);

    }

    private List<BankCardModel> createBankCardModels(int numberOfBankCardToCreate) {

        List<BankCardModel> bankCardModels = new ArrayList<>();

        for (int index = 1; index <= numberOfBankCardToCreate; index++) {

            BankCardModel bankCardModel = CreatorModels.createBankCardModel("Bancomer " + index,
                    "12345678901234567" + index, 2 + index, 2 + index, 24000.00 + index, CardTypeEnum.DEBIT_CARD, 1 + index);

            mBankCardDao.createBankCard(bankCardModel);
            bankCardModels.add(bankCardModel);

        }

        return bankCardModels;
    }

    private void compareBankCardsList(List<BankCardModel> expectedBankCards, List<BankCardModel> actualBankCards) {

        assertNotNull(actualBankCards);
        assertTrue(!actualBankCards.isEmpty());
        assertEquals(expectedBankCards.size(), actualBankCards.size());

        for (int index = 0; index < actualBankCards.size(); index++) {

            BankCardModel actualBankCardModel = actualBankCards.get(index);
            BankCardModel expectedBankCardModel = actualBankCards.get(index);

            compareBankCards(expectedBankCardModel, actualBankCardModel);
        }
    }

    private void compareBankCards(BankCardModel expectedBankCardModel, BankCardModel actualBankCardModel) {
        assertNotNull(actualBankCardModel);
        assertEquals(expectedBankCardModel.name, actualBankCardModel.name);
        assertEquals(expectedBankCardModel.number, actualBankCardModel.number);
        assertEquals(expectedBankCardModel.bankId, actualBankCardModel.bankId);
        assertEquals(expectedBankCardModel.trademarkId, actualBankCardModel.trademarkId);
        assertEquals(expectedBankCardModel.availableCredit, actualBankCardModel.availableCredit, 0);
        assertEquals(expectedBankCardModel.cardType, actualBankCardModel.cardType);
        assertEquals(expectedBankCardModel.userId, actualBankCardModel.userId);
    }

    private BankCardModel changeBankCardValues(BankCardModel bankCardModel) {
        bankCardModel.name = "Updated name";
        bankCardModel.number = "123456789012345658";
        bankCardModel.bankId = 1;
        bankCardModel.trademarkId = 1;
        bankCardModel.availableCredit = 8743.90;
        bankCardModel.cardType = CardTypeEnum.CREDIT_CARD;
        bankCardModel.userId = 2;

        return bankCardModel;
    }
}
