package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class ExpenseDaoImplTest {

    private ExpenseDao mExpenseDao;

    @Before
    public void setup() {
        Context context = getTargetContext();
        mExpenseDao = new ExpenseDaoImpl(context);
        mExpenseDao.openConection();
    }

    @After
    public void finishTest() {
        mExpenseDao.closeConection();
    }

}
