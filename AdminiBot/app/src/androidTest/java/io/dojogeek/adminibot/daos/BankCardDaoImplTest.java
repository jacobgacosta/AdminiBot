package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BankCardDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final int OPERATIONAL_ERROR = -1;
    private static final int NO_OPERATION = 0;
    private BankCardDao mBankCardDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mBankCardDao = new BankCardDaoImpl(mContext);
        mBankCardDao.openConection();
    }

    @After
    public void finishTest() {
        mBankCardDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void bankCardDao_creationBankCard_isTrue() {

        BankCardModel bankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(bankCardModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);
    }

    @Test
    public void bankCardDao_creationAndObtainingBankCardById_isTrue() {

        BankCardModel expectedBankCardModel = CreatorModels.createBankCardModel();

        long insertedRecordId = mBankCardDao.createBankCard(expectedBankCardModel);

        BankCardModel actualBankCardModel = mBankCardDao.getBankCardById(insertedRecordId);

        assertNotNull(actualBankCardModel);
        assertEquals(expectedBankCardModel.name, actualBankCardModel.name);
        assertEquals(expectedBankCardModel.number, actualBankCardModel.number);
        assertEquals(expectedBankCardModel.bankId, actualBankCardModel.bankId);
        assertEquals(expectedBankCardModel.trademarkId, actualBankCardModel.trademarkId);
        assertEquals(expectedBankCardModel.availableCredit, actualBankCardModel.availableCredit, 0);
        assertEquals(expectedBankCardModel.cardTypeId, actualBankCardModel.cardTypeId);
        assertEquals(expectedBankCardModel.userId, actualBankCardModel.userId);

    }

    @Test
    public void bankCardDao_creationAndObtainingAllBankCards_isTrue() {

        int numberOfBankCardToCreate = 5;

        List<BankCardModel> expectedBankCardModels = createBankCardModels(numberOfBankCardToCreate);

        List<BankCardModel> actualBankCardModels = mBankCardDao.getBankCards();

        compareBankCardsList(expectedBankCardModels, actualBankCardModels);
    }

    private List<BankCardModel> createBankCardModels(int numberOfBankCardToCreate) {

        List<BankCardModel> bankCardModels = new ArrayList<>();

        for (int index = 1; index <= numberOfBankCardToCreate; index++) {

            BankCardModel bankCardModel = CreatorModels.createBankCardModel("Bancomer " + index,
                    "12345678901234567" + index, 2 + index, 2 + index, 24000.00 + index, 2 + index,
                    1 + index);

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
        assertEquals(expectedBankCardModel.cardTypeId, actualBankCardModel.cardTypeId);
        assertEquals(expectedBankCardModel.userId, actualBankCardModel.userId);
    }
}
