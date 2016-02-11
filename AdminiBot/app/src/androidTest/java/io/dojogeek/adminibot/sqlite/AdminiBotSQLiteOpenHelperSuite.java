package io.dojogeek.adminibot.sqlite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AdminiBotSQLiteOpenHelperTest.class, ExpenseTypesSQLiteOpenHelperInsertionTest.class,
        TypesPaymentMethodsSQLiteOpenHelperInsertionTest.class})
public class AdminiBotSQLiteOpenHelperSuite {
}
