package io.dojogeek.adminibot.sqlite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AdminiBotSQLiteOpenHelperTest.class, ExpensesTypesSQLiteOpenHelperInsertionTest.class,
        TypesPaymentMethodsSQLiteOpenHelperInsertionTest.class/*, CardTypesSQLiteOpenHelperInsertionTest.class*/})
public class AdminiBotSQLiteOpenHelperSuite {
}
