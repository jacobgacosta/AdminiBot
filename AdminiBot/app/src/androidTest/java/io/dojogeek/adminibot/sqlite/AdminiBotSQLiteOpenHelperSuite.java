package io.dojogeek.adminibot.sqlite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExpenseTypeSQLiteOpenHelperTest.class, PaymentMethodSQLiteOpenHelperTest.class,
        ExpenseSQLiteOpenHelperTest.class})
public class AdminiBotSQLiteOpenHelperSuite {
}
