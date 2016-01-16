package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.validators.LenghtValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LenghtValidatorTest {

    private static final String EMTPY_VALUE = "";
    private static final String SPACE_VALUE = "  ";

    @Test
    public void lengthValidator_correctValue_isTrue() {

        int maxLength = 8;
        String value = "Dojogeek";

        LenghtValidator lenghtValidator = new LenghtValidator(maxLength);
        boolean isValid = lenghtValidator.isValid(value);

        assertTrue(isValid);
    }

    @Test
    public void lengthValidator_nullValue_isFalse() {

        int maxLength = 4;
        String value = null;

        LenghtValidator lenghtValidator = new LenghtValidator(maxLength);
        boolean isValid = lenghtValidator.isValid(value);

        assertFalse(isValid);

    }

    @Test
    public void lengthValidator_exceededLength_isFalse() {

        int maxLength = 15;
        String value = "Dojogeek is knoledge";

        LenghtValidator lenghtValidator = new LenghtValidator(maxLength);
        boolean isValid = lenghtValidator.isValid(value);

        assertFalse(isValid);

    }

}
