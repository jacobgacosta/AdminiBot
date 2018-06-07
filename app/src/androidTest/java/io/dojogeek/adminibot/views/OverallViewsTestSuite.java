package io.dojogeek.adminibot.views;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DebitCardActivityTest.class,
        DebitCardsActivityTest.class,
        MovementsActivityTest.class,
        PaymentMethodsActivityTest.class,
        WelcomeActivityTest.class
})
public class OverallViewsTestSuite {
}
