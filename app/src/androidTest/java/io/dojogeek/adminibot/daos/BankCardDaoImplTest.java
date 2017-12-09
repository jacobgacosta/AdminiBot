package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.ModelsFactory;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
        ((BankCardDaoImpl)mBankCardDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreation_isCreated() {

        BankCardModel bankCard = ModelsFactory.createBankCardModel();

        long insertedRecordId = mBankCardDao.create(bankCard);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);
    }

    @Test(expected = NullPointerException.class)
    public void testCreation_withNullModel_isNotCreated() {

        mBankCardDao.create(null);
    }

    @Test
    public void testGetById_successfulObtaining() throws DataException {

        BankCardModel expected = ModelsFactory.createBankCardModel();

        long insertedRecordId = mBankCardDao.create(expected);

        BankCardModel actual = mBankCardDao.getById(insertedRecordId);

        assertNotNull(actual);
        assertNull(actual.getUpdatedAt());
        assertNotNull(actual.getCreatedAt());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getNumber(), actual.getNumber());
        assertEquals(expected.getBankId(), actual.getBankId());
        assertEquals(expected.getCardType(), actual.getCardType());
        assertEquals(expected.getAvailableCredit(), actual.getAvailableCredit());
    }

    @Test(expected = DataException.class)
    public void testGetById_withNonExistentId_isException() throws DataException {

        long nonExistentId = 2;

        mBankCardDao.getById(nonExistentId);
    }

    @Test
    public void testGetAll_obtainingASuccessfulList() {

        BankCardModel expected = ModelsFactory.createBankCardModel();
        mBankCardDao.create(expected);
        mBankCardDao.create(expected);

        List<BankCardModel> actualList = mBankCardDao.getAll();

        assertNotNull(actualList);
        assertEquals(2, actualList.size());
        assertNull(actualList.get(0).getUpdatedAt());
        assertNotNull(actualList.get(0).getCreatedAt());
        assertEquals(expected.getName(), actualList.get(0).getName());
        assertEquals(expected.getBrand(), actualList.get(0).getBrand());
        assertEquals(expected.getNumber(), actualList.get(0).getNumber());
        assertEquals(expected.getBankId(), actualList.get(0).getBankId());
        assertEquals(expected.getCardType(), actualList.get(0).getCardType());
        assertEquals(expected.getAvailableCredit(), actualList.get(0).getAvailableCredit());
    }

    @Test
    public void testGetBankCards_withNoRecords_isEmptyList() {

        List<BankCardModel> actualBankCardModels = mBankCardDao.getAll();

        assertTrue(actualBankCardModels.isEmpty());
    }

    @Test
    public void testUpdate_successfulUpdate() throws DataException {

        BankCardModel expected = ModelsFactory.createBankCardModel();

        long insertedRecordId = mBankCardDao.create(expected);

        expected.setBrand("AMEX");

        long updatedRows = mBankCardDao.update(expected, insertedRecordId);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        BankCardModel actual = mBankCardDao.getById(insertedRecordId);

        assertNotNull(actual);
        assertNotNull(actual.getUpdatedAt());
        assertNotNull(actual.getCreatedAt());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getNumber(), actual.getNumber());
        assertEquals(expected.getBankId(), actual.getBankId());
        assertEquals(expected.getCardType(), actual.getCardType());
        assertEquals(expected.getAvailableCredit(), actual.getAvailableCredit());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdate_withNullModel_isException() {

        int bankCardId = 2;

        mBankCardDao.update(null, bankCardId);

    }

    @Test
    public void testUpdate_withNonExistentId_noUpdate() {

        BankCardModel bankCardModel = ModelsFactory.createBankCardModel();

        long nonExistentId = 4;

        long updatedRows = mBankCardDao.update(bankCardModel, nonExistentId);

        assertThat(updatedRows, is(NO_OPERATION));

    }

    @Test
    public void testDelete_successfulDeletion() {

        BankCardModel bankCardModel = ModelsFactory.createBankCardModel();

        long insertedRecordId = mBankCardDao.create(bankCardModel);

        long deletedRows = mBankCardDao.delete(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDelete_withNonExistentId_noDeletion() {

        long nonExistentId = 5;

        long deletedRows = mBankCardDao.delete(nonExistentId);

        assertThat(deletedRows, is(NO_OPERATION));
    }

    @Test
    public void testGetByCartType_successfulObtaining() {

        BankCardModel bankCard = ModelsFactory.createBankCardModel();
        mBankCardDao.create(bankCard);

        List<BankCardModel> actualList = mBankCardDao.getByCartType(bankCard.getCardType());

        assertNotNull(actualList);
        assertEquals(1, actualList.size());
        assertNull(actualList.get(0).getUpdatedAt());
        assertNotNull(actualList.get(0).getCreatedAt());
        assertEquals(bankCard.getName(), actualList.get(0).getName());
        assertEquals(bankCard.getBrand(), actualList.get(0).getBrand());
        assertEquals(bankCard.getNumber(), actualList.get(0).getNumber());
        assertEquals(bankCard.getBankId(), actualList.get(0).getBankId());
        assertEquals(bankCard.getCardType(), actualList.get(0).getCardType());
        assertEquals(bankCard.getAvailableCredit(), actualList.get(0).getAvailableCredit());
    }

    @Test
    public void testRegisteredBankCards_obtainingASuccessfulList() {

        BankCardModel bankCardModel1 = ModelsFactory.createBankCardModel();

        BankCardModel bankCardModel2 = ModelsFactory.createBankCardModel();

        BankCardModel bankCardModel3 = ModelsFactory.createBankCardModel();
        bankCardModel2.setCardType("DEBIT_CARD");

        mBankCardDao.create(bankCardModel1);
        mBankCardDao.create(bankCardModel2);
        mBankCardDao.create(bankCardModel3);

        List<TypePaymentMethodEnum> actualCardTypes = mBankCardDao.getRegisteredTypes();

        assertNotNull(actualCardTypes);
        assertEquals(2, actualCardTypes.size());
    }
}
