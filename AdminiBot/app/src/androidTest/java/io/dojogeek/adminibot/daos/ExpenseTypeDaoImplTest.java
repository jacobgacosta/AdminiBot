package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class ExpenseTypeDaoImplTest {

    private ExpenseTypeDao mExpenseTypeDao;

    @Before
    public void setup() {
        Context context = getTargetContext();
        context.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
        mExpenseTypeDao = new ExpenseTypeDaoImpl(context);
        mExpenseTypeDao.openConection();
    }

    @After
    public void finishTest() {
        mExpenseTypeDao.closeConection();
    }

}
