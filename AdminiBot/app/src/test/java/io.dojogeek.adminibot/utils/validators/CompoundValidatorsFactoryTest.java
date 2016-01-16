package io.dojogeek.adminibot.utils.validators;

import org.junit.Test;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.validators.CompoundValidator;
import io.dojogeek.adminibot.validators.CompoundValidatorsFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CompoundValidatorsFactoryTest {

    private static final int NO_ERRORS = 0;

    @Test
    public void compoundValidators_correctEmail_isTrue() {

        final String email = "jgacosta@dojogeek.io";

        CompoundValidator compoundValidator = CompoundValidatorsFactory.emailValidator();
        compoundValidator.isValid(email);

        assertEquals(compoundValidator.getErrorMsg(), NO_ERRORS);
    }

    @Test
    public void compoundValidators_longLength_isFalse() {

        final String email = "jgacostadg880224n62guaj272016hck@dojogeek.io";

        CompoundValidator compoundValidator = CompoundValidatorsFactory.emailValidator();
        compoundValidator.isValid(email);

        assertEquals(compoundValidator.getErrorMsg(), R.string.error_wrong_length_email);
    }

}
