package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.validators.LoginValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginValidatorTest {

    private LoginValidator mLoginValidator;

    @Before
    public void setup() {

        mLoginValidator = new LoginValidator();

    }

    @Test
    public void loginValidator_correctData_isTrue() {

        mLoginValidator.setEmail("jgacosta@dojogeek.io");
        mLoginValidator.setPassword("dojogeek_1234_#990DG");

        boolean isValid = mLoginValidator.validate();

        assertTrue(isValid);
    }

    @Test
    public void loginValidator_formatEmail_isFalse() {

        String [] invalidEmails = {"jgacosta@dojogeek", "jgacosta@dojo@.com", "jgacosta@.com",
                "jgacosta@..", "jgacosta@@@.com", "jgacosta@dojogeek@dojogeek.io", "dojogeek.io"};

        for (String emails : invalidEmails) {

            mLoginValidator.setEmail(emails);
            mLoginValidator.setPassword("dojogeek1234@_DG#1");
            boolean isValid = mLoginValidator.validate();
            assertFalse(isValid);
            assertEquals(R.string.error_wrong_format_email, mLoginValidator.getErrorMessageEmail());

        }

    }
}
