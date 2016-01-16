package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.validators.LenghtValidator;

public class LenghtValidatorTest {

    @Test
    public void lengthValidator_correctValue_isTrue() {

        int maxLength = 8;
        String value = "Dojogeek";

        LenghtValidator lenghtValidator = new LenghtValidator(maxLength);
        lenghtValidator.isValid(value);
    }

}
