package io.dojogeek.adminibot.daos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserDaoImplTest.class, PaymentMethodsDaoImplTest.class,
        ExpenseTypeDaoImplTest.class, ExpenseDaoImplTest.class})
public class DaoSuiteTest {
}
