package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class BankCardDaoImplTest {

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

}
