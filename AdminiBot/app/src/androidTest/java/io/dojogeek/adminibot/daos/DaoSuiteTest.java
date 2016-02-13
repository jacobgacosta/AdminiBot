package io.dojogeek.adminibot.daos;

import android.content.Context;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserDaoImplTest.class, ExpenseTypeDaoImplTest.class})
public class DaoSuiteTest {

    @AfterClass
    public void deleteDataBase() {

        Context context = getTargetContext();
        context.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);

    }

}
