package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class MovementExpenseBankCardDaoImplTest {

    private static final int SUCCESS_OPERATION = 1;
    private static final int OPERATIONAL_ERROR = -1;
    private static final int NO_OPERATION = 0;

    private Context mContext;
    private MovementExpenseBankCardDao mMovementExpenseBankCardDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mMovementExpenseBankCardDao = new MovementExpenseBankCardDaoImpl(mContext);
        mMovementExpenseBankCardDao.openConection();
    }

    @After
    public void finishTest() {
        mMovementExpenseBankCardDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

}
