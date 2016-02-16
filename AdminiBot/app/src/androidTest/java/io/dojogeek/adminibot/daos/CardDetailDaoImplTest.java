package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.CardDetailContract;
import io.dojogeek.adminibot.utils.DateUtils;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CardDetailDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final int OPERATIONAL_ERROR = -1;
    private static final int NO_OPERATION = 0;
    private CardDetailDao mCardDetailDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mCardDetailDao = new CardDetailDaoImpl(mContext);
        mCardDetailDao.openConection();
    }

    @After
    public void finishTest() {
        mCardDetailDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void cardDetailDao_creationCardDetail_isTrue() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);

    }

    @Test
    public void cardDetailDao_creationAndObtainingCardDetailById_isTrue() {

        CardDetailModel expectedCardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(expectedCardDetailModel);

        CardDetailModel actualCardDetailModel = mCardDetailDao.getCardDetailById(insertedRecordId);

        compareCardsDetails(expectedCardDetailModel, actualCardDetailModel);
    }

    @Test
    public void cardDetailDao_creationAndObtainingCardDetailByBankCardId_isTrue() {

        CardDetailModel expectedCardDetailModel = CreatorModels.createCardDetailModel();

        mCardDetailDao.createCardDetail(expectedCardDetailModel);

        CardDetailModel actualCardDetailModel = mCardDetailDao.getCardDetailByBankCardId(expectedCardDetailModel.bankCardId);

        compareCardsDetails(expectedCardDetailModel, actualCardDetailModel);

    }

    @Test
    public void cardDetailDao_creationUpdatingAndObtainingCardDetailById_isTrue() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        CardDetailModel expectedUpdatedCardDetail = changeCardDetailValues(cardDetailModel);

        String where = CardDetailContract.CardDetail._ID + "= " + insertedRecordId;

        long updatedRows = mCardDetailDao.updateCardDetail(expectedUpdatedCardDetail, where);

        assertEquals(SUCCESS_OPERATION, updatedRows);

        CardDetailModel actualCardDetailModel = mCardDetailDao.getCardDetailById(insertedRecordId);

        compareCardsDetails(expectedUpdatedCardDetail, actualCardDetailModel);

    }

    @Test
    public void cardDetailDao_creationAndDeletionCardDetail_isTrue() {

        CardDetailModel cardDetailModel = CreatorModels.createCardDetailModel();

        long insertedRecordId = mCardDetailDao.createCardDetail(cardDetailModel);

        long deletedRows = mCardDetailDao.deleteCardDetail(insertedRecordId);

        assertEquals(SUCCESS_OPERATION, deletedRows);
    }

    private void compareCardsDetails(CardDetailModel expectedCardDetailModel, CardDetailModel actualCardDetailModel) {

        assertNotNull(actualCardDetailModel);
        assertEquals(expectedCardDetailModel.bankCardId, actualCardDetailModel.bankCardId);
        assertEquals(expectedCardDetailModel.creditLimit, actualCardDetailModel.creditLimit, 0);
        assertEquals(expectedCardDetailModel.currentBalance, actualCardDetailModel.currentBalance, 0);
        assertEquals(expectedCardDetailModel.cuttoffDate, actualCardDetailModel.cuttoffDate);
        assertEquals(expectedCardDetailModel.payDayLimit, actualCardDetailModel.payDayLimit);
    }

    private CardDetailModel changeCardDetailValues(CardDetailModel cardDetailModel) {

        cardDetailModel.bankCardId = 4;
        cardDetailModel.creditLimit = 25000.00;
        cardDetailModel.payDayLimit = DateUtils.getCurrentData();
        cardDetailModel.currentBalance = 24980.00;
        cardDetailModel.cuttoffDate = DateUtils.getCurrentData();

        return cardDetailModel;
    }
}
