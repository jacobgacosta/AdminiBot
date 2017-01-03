package io.dojogeek.adminibot.validators;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequiredValueValidatorTest {

    private static final String EMTPY_VALUE = "";
    private static final String SPACE_VALUE = "  ";
    private RequiredValueValidator mRequiredValueValidator;

    @Before
    public void setup() {
        mRequiredValueValidator = new RequiredValueValidator();
    }

    @Test
    public void requiredValueValidator_correctValue_isTrue() {

        String value = "Jacob";

        boolean isValid = mRequiredValueValidator.isValid(value);

        assertTrue(isValid);

    }

    @Test
    public void requiredValueValidator_nullValue_isFalse() {

        String value = null;

        boolean isValid = mRequiredValueValidator.isValid(value);

        assertFalse(isValid);
    }

    @Test
    public void requiredValueValidator_emtptyValue_isFalse() {

        String [] emptyValues = {EMTPY_VALUE, SPACE_VALUE};

        for (String value : emptyValues) {
            boolean isValid = mRequiredValueValidator.isValid(value);
            assertFalse(isValid);
        }
    }

}
