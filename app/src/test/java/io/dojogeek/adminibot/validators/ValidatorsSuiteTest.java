package io.dojogeek.adminibot.validators;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ValidatorTest.class, RequiredValueValidatorTest.class,
        RegexValidatorTest.class, LenghtValidatorTest.class})
public class ValidatorsSuiteTest {
}
