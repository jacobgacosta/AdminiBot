package io.dojogeek.adminibot.presenters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CashPresenterTest.class,
        CheckPresenterTest.class,
        CreditCardPresenterTest.class,
        ExpenseCreationPresenterTest.class,
        ExpensesInboxPresenterTest.class,
        FoodCouponPresenterTest.class,
        PaymentMethodsPresenterTest.class
})
public class PresentersSuiteTest {
}
