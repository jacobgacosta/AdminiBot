package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.validators.UserValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {

    private static final String CORRECT_NAME = "Jacob";
    private static final String CORRECT_LAST_NAME = "Guzman A";
    private static final String CORRECT__EMAIL = "jgacosta@dojogeek.io";
    private static final String EMTPY_VALUE = "";
    private static final String SPACE_VALUE = "  ";
    private UserValidator mUserValidator;

    @Before
    public void setup() {
        mUserValidator = new UserValidator();
    }

    @Test
    public void userValidator_userModel_isTrue() {

        UserValidator userValidator = createUserValidator(CORRECT__EMAIL, CORRECT_NAME, CORRECT_LAST_NAME);

        boolean isValid = userValidator.validate();
        
        assertTrue(isValid);
    }

    @Test
    public void userValidator_formatEmail_isFalse() {

        final String [] invalidEmails = {"jgacosta@", "jgacostadojogeek.io", "jgacosta@dojogeek.",
                "@dojogeek.io", "jgacosta", "jgacosta@dojogeek.@", "jgacosta@dojogeek._", EMTPY_VALUE,
                SPACE_VALUE};

        UserValidator userValidator;

        for (String email : invalidEmails) {
            userValidator = createUserValidator(email, CORRECT_NAME, CORRECT_LAST_NAME);
            boolean isValid = userValidator.validate();
            assertFalse(isValid);
        }

    }

    @Test
    public void userValidator_nameLenght_isFalse() {

        UserValidator userValidator = createUserValidator(CORRECT__EMAIL, "Jacooooooooooooooooooooooob", CORRECT_LAST_NAME);
        boolean isValid = userValidator.validate();
        assertFalse(isValid);
        assertEquals(R.string.error_wrong_lenght_name, userValidator.getErrorMessageName());
    }

    @Test
    public void userValidator_withEmptyName_isFalse() {

        final String [] emptyValues = {EMTPY_VALUE, SPACE_VALUE};

        for (String value : emptyValues) {
            UserValidator userValidator = createUserValidator(CORRECT__EMAIL, value, CORRECT_LAST_NAME);
            boolean isValid = userValidator.validate();
            assertFalse(isValid);
            assertEquals(R.string.error_empty_value, userValidator.getErrorMessageName());
        }

    }

    @Test
    public void userValidator_nameWithoutOnlyLetters_isFalse() {

        final String [] invalidNames = {"12345", "Jacob2", "j@cob", "Ja_cob",
                "J$b", "jacob!"};

        for (String value : invalidNames) {
            UserValidator userValidator = createUserValidator(CORRECT__EMAIL, value, CORRECT_LAST_NAME);
            boolean isValid = userValidator.validate();
            assertFalse(isValid);
            assertEquals(R.string.error_wrong_format_name, userValidator.getErrorMessageName());
        }

    }

    private UserValidator createUserValidator(String email, String name, String lastName) {

        UserValidator userValidator = new UserValidator();
        userValidator.setName(name);
        userValidator.setLastName(lastName);
        userValidator.setEmail(email);

        return userValidator;

    }

}
