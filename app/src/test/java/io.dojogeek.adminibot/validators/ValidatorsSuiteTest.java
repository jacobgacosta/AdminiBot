package io.dojogeek.adminibot.validators;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ValidatorTest.class, UserValidatorTest.class, RequiredValueValidatorTest.class,
        RegexValidatorTest.class, LoginValidatorTest.class, LenghtValidatorTest.class, CompoundValidatorTest.class,
        CompoundValidatorsFactoryTest.class})
public class ValidatorsSuiteTest {
}
