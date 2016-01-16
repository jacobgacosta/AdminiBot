package io.dojogeek.adminibot.utils.validators;

import org.junit.Test;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.utils.ValidatorsValues;
import io.dojogeek.adminibot.validators.RegexValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexValidatorTest {

    @Test
    public void regexValidator_matchedString_isTrue() {

        String correctEmail = "jgacosta@dojogeek.io";

        RegexValidator regexValidator = new RegexValidator(ValidatorsValues.EMAIL_REGEXP);
        boolean isValid = regexValidator.isValid(correctEmail);

        assertTrue(isValid);

    }


}
