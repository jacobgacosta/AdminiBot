package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import io.dojogeek.adminibot.validators.RequiredValueValidator;

public class RequiredValueValidatorTest {

    private RequiredValueValidator mRequiredValueValidator;

    @Before
    public void setup() {
        mRequiredValueValidator = new RequiredValueValidator();
    }

    @Test
    public void requiredValueValidator_nullValue_isFalse() {

        String value = null;

        boolean isValid = mRequiredValueValidator.isValid(value);

        assertFalse(isValid);
    }


}
