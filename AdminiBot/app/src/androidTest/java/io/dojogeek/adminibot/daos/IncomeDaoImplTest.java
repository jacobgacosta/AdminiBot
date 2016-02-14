package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class IncomeDaoImplTest {

    private Context mContext;
    private IncomeDao mIncomeDao;

    @Before
    public void setup() {
        mContext = getTargetContext();
        mIncomeDao = new IncomeDaoImpl(mContext);
        mIncomeDao.openConection();
    }

    @After
    public void finishTest() {
        mIncomeDao.closeConection();
    }
}
