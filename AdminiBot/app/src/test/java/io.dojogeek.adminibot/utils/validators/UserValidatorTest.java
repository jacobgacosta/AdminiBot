package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.validators.UserValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {

    private static final String EMTPY_VALUE = "";
    private static final String SPACE_VALUE = "  ";
    private UserValidator mUserValidator;

    @Before
    public void setup() {
        mUserValidator = new UserValidator();
    }

    @Test
    public void userValidator_userModel_isTrue() {

        UserValidator userValidator = createUserValidator("jgacosta@dojogeek.io", "Jacob", "G. Acosta");

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
            userValidator = createUserValidator(email, "Jacob", "G. Acosta");
            boolean isValid = userValidator.validate();
            assertFalse(isValid);
        }

    }

    @Test
    public void userValidator_nameLenght_isFalse() {

        UserValidator userValidator = createUserValidator("jgacosta@dojogeek.io",
                "Jacooooooooooooooooooooooob", "G. Acosta");

        boolean isValid = userValidator.validate();

        assertFalse(isValid);

    }

    private UserValidator createUserValidator(String email, String name, String lastName) {

        UserValidator userValidator = new UserValidator();
        userValidator.setName(name);
        userValidator.setLastName(lastName);
        userValidator.setEmail(email);

        return userValidator;

    }

}
