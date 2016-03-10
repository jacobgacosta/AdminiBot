package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.CardDetailContract;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class CardDetailDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final long OPERATIONAL_ERROR = -1;
    private static final long NO_OPERATION = 0;
    private CardDetailDao mCardDetailDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mCardDetailDao = new CardDetailDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((CardDetailDaoImpl)mCardDetailDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateCardDetail_successInsertion() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

    }

    @Test(expected = NullPointerException.class)
    public void testCreateCardDetail_withNullModel_isException() {

        CardDetailModel cardDetailModel = null;

        mCardDetailDao.createCardDetail(cardDetailModel);

    }

    @Test
    public void testCreateCardDetail_withNullRequiredField_noInsertion() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();
        cardDetailModel.setCuttoffDate(null);

        long insetedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        assertThat(insetedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetCardDetailByBankCardId_successObtaining() throws DataException {

        CardDetailModel expectedCardDetailModel = CreatorModels.createCardDetailModel();

        mCardDetailDao.createCardDetail(expectedCardDetailModel);

        CardDetailModel actualCardDetailModel = mCardDetailDao.
                getCardDetailByBankCardId(expectedCardDetailModel.getBankCardId());

        compareCardsDetails(expectedCardDetailModel, actualCardDetailModel);

    }

    @Test(expected = DataException.class)
    public void testGetCardDetailByBankCardId_withNonExistentBanCardId_isException() throws DataException {

        long nonExistentId = 5;

        mCardDetailDao.getCardDetailByBankCardId(nonExistentId);

    }

    @Test
    public void testUpdateCardDetail_successUpdating() throws DataException {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        CardDetailModel expectedUpdatedCardDetail = changeCardDetailValues(cardDetailModel);

        String where = CardDetailContract.CardDetail._ID + "= " + insertedRecordId;

        long updatedRows = mCardDetailDao.updateCardDetail(expectedUpdatedCardDetail, where);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        CardDetailModel actualCardDetailModel =
                mCardDetailDao.getCardDetailByBankCardId(cardDetailModel.getBankCardId());

        compareCardsDetails(expectedUpdatedCardDetail, actualCardDetailModel);

    }

    @Test(expected = NullPointerException.class)
    public void testUpdateCardDetail_withNullModel_isException() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        String where = CardDetailContract.CardDetail._ID + "= " + insertedRecordId;

        mCardDetailDao.updateCardDetail(null, where);

    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateCardDetail_withNullRequiredField_noUpdate() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        CardDetailModel expectedUpdatedCardDetail = changeCardDetailValues(cardDetailModel);
        expectedUpdatedCardDetail.setCuttoffDate(null);

        String where = CardDetailContract.CardDetail._ID + "= " + insertedRecordId;

        mCardDetailDao.updateCardDetail(expectedUpdatedCardDetail, where);

    }

    @Test
    public void testUpdateCardDetail_withNonExistentId_noUpdating() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long nonExistentId = 5;

        String where = CardDetailContract.CardDetail._ID + "= " + nonExistentId;

        mCardDetailDao.updateCardDetail(cardDetailModel, where);

    }

    @Test
    public void testDeleteCardDetail_successDeletion() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        long deletedRows = mCardDetailDao.deleteCardDetail(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    @Test
    public void testDeleteCardDetail_withNonExistentId_noDeletion() {

        long nonExistentId = 4;

        mCardDetailDao.deleteCardDetail(nonExistentId);

    }

    private void compareCardsDetails(CardDetailModel expectedCardDetailModel, CardDetailModel actualCardDetailModel) {

        assertNotNull(actualCardDetailModel);
        assertEquals(expectedCardDetailModel.getBankCardId(), actualCardDetailModel.getBankCardId());
        assertEquals(expectedCardDetailModel.getCreditLimit(), actualCardDetailModel.getCreditLimit(), 0);
        assertEquals(expectedCardDetailModel.getCurrentBalance(), actualCardDetailModel.getCurrentBalance(), 0);
        assertEquals(expectedCardDetailModel.getCuttoffDate(), actualCardDetailModel.getCuttoffDate());
        assertEquals(expectedCardDetailModel.getPayDayLimit(), actualCardDetailModel.getPayDayLimit());
    }

    private CardDetailModel changeCardDetailValues(CardDetailModel cardDetailModel) {

        cardDetailModel.setBankCardId(4);
        cardDetailModel.setCreditLimit(25000.00);
        cardDetailModel.setPayDayLimit(DateUtils.getCurrentData());
        cardDetailModel.setCurrentBalance(24980.00);
        cardDetailModel.setCuttoffDate(DateUtils.getCurrentData());

        return cardDetailModel;
    }
}
