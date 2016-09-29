package io.dojogeek.adminibot.daos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserDaoImplTest.class, ExpenseTypeDaoImplTest.class, ExpenseDaoImplTest.class,
        IncomeDaoImplTest.class, BankCardDaoImplTest.class, CardDetailDaoImplTest.class,
        OtherPaymentMethodDaoImplTest.class, ExpenseBankCardDaoImplTest.class,
        IncomeBankCardDaoImplTest.class, ExpenseOtherPaymentMethodDaoTest.class,
        IncomeOtherPaymentMethodsDaoTest.class, BankDaoImplTest.class,
        CashDaoImplTest.class, TrademarkDaoImplTest.class})
public class DaoSuiteTest {

}
