package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.validators.LoginValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginValidatorTest {

    private static final String EMTPY_VALUE = "";
    private static final String SPACE_VALUE = "  ";
    private static final String CORRECT_PASSWORD = "dojogeek1234@_DG#1";
    private static final String CORRECT_EMAIL = "jgacosta@dojogeek.io";
    private LoginValidator mLoginValidator;

    @Before
    public void setup() {

        mLoginValidator = new LoginValidator();

    }

    @Test
    public void loginValidator_correctData_isTrue() {

        mLoginValidator.setEmail(CORRECT_EMAIL);
        mLoginValidator.setPassword(CORRECT_PASSWORD);

        boolean isValid = mLoginValidator.validate();

        assertTrue(isValid);
    }

    @Test
    public void loginValidator_formatEmail_isFalse() {

        String [] invalidEmails = {"jgacosta@dojogeek", "jgacosta@dojo@.com", "jgacosta@.com",
                "jgacosta@..", "jgacosta@@@.com", "jgacosta@dojogeek@dojogeek.io", "dojogeek.io",
        "jgacosta#@dojogeek.io", "jgacosta@dojogeek.##"};

        for (String emails : invalidEmails) {

            mLoginValidator.setEmail(emails);
            mLoginValidator.setPassword(CORRECT_PASSWORD);
            boolean isValid = mLoginValidator.validate();
            assertFalse(isValid);
            assertEquals(R.string.error_wrong_format_email, mLoginValidator.getErrorMessageEmail());

        }

    }

    @Test
    public void loginValidator_withEmptyEmail_isFalse() {

        String [] emptyValues = {EMTPY_VALUE, SPACE_VALUE};

        for (String values : emptyValues) {

            mLoginValidator.setEmail(values);
            mLoginValidator.setPassword(CORRECT_PASSWORD);

            boolean isValid = mLoginValidator.validate();
            assertFalse(isValid);
            assertEquals(R.string.error_empty_value, mLoginValidator.getErrorMessageEmail());

        }
    }

    @Test
    public void loginValidator_withNullEmail_isFalse() {

        String nullEmail = null;

        mLoginValidator.setPassword(CORRECT_PASSWORD);
        mLoginValidator.setEmail(nullEmail);
        boolean isValid = mLoginValidator.validate();

        assertFalse(isValid);
        assertEquals(R.string.error_empty_value, mLoginValidator.getErrorMessageEmail());
    }

    @Test
    public void loginValidator_exceededLengthEmail_isFalse() {

        String exceededLengthEmail = "jgacosta240288dg2015knowledge@dojogeek.io";

        mLoginValidator.setPassword(CORRECT_PASSWORD);
        mLoginValidator.setEmail(exceededLengthEmail);
        boolean isValid = mLoginValidator.validate();

        assertFalse(isValid);
        assertEquals(R.string.error_wrong_length_email, mLoginValidator.getErrorMessageEmail());

    }

    @Test
    public void loginValidator_withNullPassword() {

        String nullPassword = null;

        mLoginValidator.setEmail(CORRECT_EMAIL);
        mLoginValidator.setPassword(nullPassword);

        boolean isValid = mLoginValidator.validate();

        assertFalse(isValid);
        assertEquals(R.string.error_empty_value, mLoginValidator.getErrorMessagePassword());
    }

    @Test
    public void loginValidator_withEmptyPassword() {

        final String [] emptyValues = {EMTPY_VALUE, SPACE_VALUE};

        for (String values : emptyValues) {
            mLoginValidator.setEmail(CORRECT_EMAIL);
            mLoginValidator.setPassword(values);
            boolean isValid = mLoginValidator.validate();

            assertFalse(isValid);
            assertEquals(R.string.error_empty_value, mLoginValidator.getErrorMessagePassword());
        }
    }

    @Test
    public void loginValidator_exceededLengthPassword_isFalse() {

        final String password = "DojogeekJgAcosta240288_#$%??*)/%&";

        mLoginValidator.setEmail(CORRECT_EMAIL);
        mLoginValidator.setPassword(password);
        boolean isValid = mLoginValidator.validate();

        assertFalse(isValid);
        assertEquals(R.string.error_wrong_lenght_password, mLoginValidator.getErrorMessagePassword());
    }
}
