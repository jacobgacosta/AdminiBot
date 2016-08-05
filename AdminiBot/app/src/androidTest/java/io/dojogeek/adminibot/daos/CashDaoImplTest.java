package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class CashDaoImplTest {

    private static final long NO_OPERATION = 0;
    private static final long OPERATIONAL_ERROR = -1;

    private Context mContext;

    private CashDao mCashDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mCashDao = new CashDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((CashDaoImpl) mCashDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateCash_successfulCreation() {

        CashModel cashModel = CreatorModels.createCashModel();

        long insertedRecordId = mCashDao.createCash(cashModel);

        assertNotEquals(NO_OPERATION, insertedRecordId);
        assertNotEquals(OPERATIONAL_ERROR, insertedRecordId);
    }

}
