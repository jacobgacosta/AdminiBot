package io.dojogeek.adminibot.daos;

import android.content.Context;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserDaoImplTest.class, ExpenseTypeDaoImplTest.class, ExpenseDaoImplTest.class,
        IncomeDaoImplTest.class, BankCardDaoImplTest.class, CardDetailDaoImplTest.class,
        OtherPaymentMethodsDaoImplTest.class})
public class DaoSuiteTest {

}
