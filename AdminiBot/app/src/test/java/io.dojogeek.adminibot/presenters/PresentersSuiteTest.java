package io.dojogeek.adminibot.presenters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import io.dojogeek.adminibot.validators.CompoundValidatorTest;
import io.dojogeek.adminibot.validators.CompoundValidatorsFactoryTest;
import io.dojogeek.adminibot.validators.LenghtValidatorTest;
import io.dojogeek.adminibot.validators.LoginValidatorTest;
import io.dojogeek.adminibot.validators.RegexValidatorTest;
import io.dojogeek.adminibot.validators.RequiredValueValidatorTest;
import io.dojogeek.adminibot.validators.UserValidatorTest;
import io.dojogeek.adminibot.validators.ValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({PaymentMethodsPresenterTest.class, ExpensesInboxPresenterTest.class,
        ExpenseCreationPresenterTest.class, CashPresenterTest.class})
public class PresentersSuiteTest {
}
