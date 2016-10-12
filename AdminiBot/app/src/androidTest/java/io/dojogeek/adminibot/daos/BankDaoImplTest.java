package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.enums.BankEnum;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BankDaoImplTest {

    private BankDao mBankDao;
    private Context mContext;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mBankDao = new BankDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((BankDaoImpl) mBankDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testGetBanks_successObtainingList() {

        int initialInsertedBanks = BankEnum.values().length;

        List<BankModel> actualBanks = mBankDao.getBanks();

        assertNotNull(actualBanks);
        assertEquals(initialInsertedBanks, actualBanks.size());
    }
}
