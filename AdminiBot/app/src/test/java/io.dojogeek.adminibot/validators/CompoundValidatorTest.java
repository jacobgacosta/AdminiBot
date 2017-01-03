package io.dojogeek.adminibot.validators;

import org.junit.Test;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.utils.ValidatorsValues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompoundValidatorTest {

    @Test
    public void compoundValidator_correctValue_isTrue() {

        final String email = "jgacosta@dojogeek.io";

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.EMAIL_REGEXP));
        compoundValidator.addValidator(new RequiredValueValidator());
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.EMAIL_MAX_LENGTH));

        assertTrue(compoundValidator.isValid(email));
    }

    @Test
    public void compoundValidator_wrongValue_isFalse() {

        final String name = "Jac0b";

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(new RequiredValueValidator());
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ONLY_LETTERS_AND_SPACES).
                withErroMessage(R.string.error_wrong_format_name));

        assertFalse(compoundValidator.isValid(name));
        assertEquals(compoundValidator.getErrorMsg(), R.string.error_wrong_format_name);
    }
}
