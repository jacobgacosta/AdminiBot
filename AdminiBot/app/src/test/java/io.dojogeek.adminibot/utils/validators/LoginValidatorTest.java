package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.validators.LoginValidator;

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

}
