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

    @Test
    public void regexValidator_notMatchingString_isFalse() {

        String simpleString = "Dojogeej is knowledge =)";

        RegexValidator regexValidator = new RegexValidator(ValidatorsValues.ONLY_LETTERS_AND_SPACES);
        regexValidator.withErroMessage(R.string.error_wrong_format_name);
        boolean isValid = regexValidator.isValid(simpleString);

        assertFalse(isValid);

        assertEquals(R.string.error_wrong_format_name, regexValidator.getErrorMsg());

    }

    @Test
    public void regexValidator_nullValue_isFalse() {

        String nullValue = null;

        RegexValidator regexValidator = new RegexValidator(ValidatorsValues.EMAIL_REGEXP);
        regexValidator.withErroMessage(R.string.error_wrong_format_email);
        boolean isValid = regexValidator.isValid(nullValue);

        assertFalse(isValid);

        assertEquals(R.string.error_wrong_format_email, regexValidator.getErrorMsg());

    }

}
