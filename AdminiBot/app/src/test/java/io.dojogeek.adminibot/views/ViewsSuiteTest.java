package io.dojogeek.adminibot.views;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddNewPaymentMethodActivityTest.class,
        AddPaymentMethodActivityTest.class,
        CardCreationActivityTest.class,
        CashActivityTest.class,
        CheckActivityTest.class,
        CreditCardActivityTest.class,
        FoodCouponsActivityTest.class,
        MyPaymentMethodsActivityTest.class,
        PaymentMethodsActivityTest.class
})
public class ViewsSuiteTest {
}
