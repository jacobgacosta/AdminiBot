package io.dojogeek.adminibot.sqlite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExpenseTypesSQLiteOpenHelperTest.class, TypesPaymentMethodsSQLiteOpenHelperTest.class,
        ExpensesSQLiteOpenHelperTest.class, UsersSQLiteOpenHelperTest.class,
        PaymentMethodsSQLiteOpenHelperTest.class, ExpensesPaymentMethodsOpenHelperTest.class})
public class AdminiBotSQLiteOpenHelperSuite {
}
