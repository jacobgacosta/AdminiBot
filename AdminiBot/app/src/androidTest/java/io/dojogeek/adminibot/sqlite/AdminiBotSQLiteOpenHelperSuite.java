package io.dojogeek.adminibot.sqlite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExpenseTypeSQLiteOpenHelperTest.class, TypesPaymentMethodsSQLiteOpenHelperTest.class,
        ExpenseSQLiteOpenHelperTest.class, UserSQLiteOpenHelperTest.class,
        PaymentMethodsSQLiteOpenHelperTest.class, ExpensesPaymentMethodsOpenHelperTest.class})
public class AdminiBotSQLiteOpenHelperSuite {
}
