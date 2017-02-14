package io.dojogeek.adminibot.presenters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CashPresenterTest.class,
        CheckPresenterTest.class,
        CreditCardDetailPresenterTest.class,
        CreditCardPresenterTest.class,
        ExpenseCreationPresenterTest.class,
        ExpensesInboxPresenterTest.class,
        FoodCouponPresenterTest.class,
        MyCashPresenterTest.class,
        MyCreditCardsPresenterTest.class,
        MyFoodCouponsPresenterTest.class,
        PaymentMethodsPresenterTest.class
})
public class PresentersSuiteTest {
}
