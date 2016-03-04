package io.dojogeek.adminibot.validators;

import org.junit.Test;

import io.dojogeek.adminibot.exceptions.ValidatorNullValueException;

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

    @Test(expected= ValidatorNullValueException.class)
    public void lengthValidator_nullValue_isException() {

        int maxLength = 4;
        String value = null;

        LenghtValidator lenghtValidator = new LenghtValidator(maxLength);
        lenghtValidator.isValid(value);

    }

    @Test
    public void lengthValidator_exceededLength_isFalse() {

        int maxLength = 15;
        String value = "Dojogeek is knoledge";

        LenghtValidator lenghtValidator = new LenghtValidator(maxLength);
        boolean isValid = lenghtValidator.isValid(value);

        assertFalse(isValid);

    }

    @Test
    public void lengthValidator_withEmptyValue_isTrue() {

        int maxLength = 10;
        String [] emptyValues = {EMTPY_VALUE, SPACE_VALUE};

        for (String value : emptyValues) {
            LenghtValidator lenghtValidator = new LenghtValidator(maxLength);
            boolean isValid = lenghtValidator.isValid(value);
            assertTrue(isValid);
        }

    }
}
