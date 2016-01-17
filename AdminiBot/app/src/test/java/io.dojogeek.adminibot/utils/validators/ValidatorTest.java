package io.dojogeek.adminibot.utils.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.dojogeek.adminibot.utils.ValidatorsValues;
import io.dojogeek.adminibot.validators.DataValidator;
import io.dojogeek.adminibot.validators.LenghtValidator;
import io.dojogeek.adminibot.validators.RegexValidator;
import io.dojogeek.adminibot.validators.RequiredValueValidator;
import io.dojogeek.adminibot.validators.Validator;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {

    private Validator mValidator;

    @Test
    public void validator_correctValidator_returnTrue() {

        Map<String, Object> valuesToValidate = createCorrectValuesToValidate();

        Map<String, DataValidator> validators = createValidatorsMap();

        Validator validator = createConfigureValidator(validators, valuesToValidate);
        boolean isValid = validator.validate();

        assertTrue(isValid);

    }

    @Test
    public void validator_withFailedValidator_isFalse() {

        Map<String, DataValidator> validators = createValidatorsMap();

        Map<String, Object> valuesToValidate = createInvalidValuesToValidate();

        Validator validator = createConfigureValidator(validators, valuesToValidate);
        boolean isValid = validator.validate();

        assertFalse(isValid);
        assertFalse(validator.isError("onlyLettersAndSpaces"));
        assertFalse(validator.isError("onlyNumbers"));
        assertFalse(validator.isError("maxLength"));
        assertFalse(validator.isError("requiredValue"));

    }

    private Validator createConfigureValidator(final Map<String, DataValidator> validators,
                                               final Map<String, Object> valuesToValidate) {

        mValidator = new Validator() {
            @Override
            protected void configureValidations() {
                validations = validators;
                dataToValidate = valuesToValidate;
            }
        };

        return mValidator;
    }

    private Map<String, DataValidator> createValidatorsMap() {

        RequiredValueValidator requiredValueValidator = new RequiredValueValidator();

        Map<String, DataValidator> validations = new HashMap<>();
        validations.put("onlyLettersAndSpaces", RegexValidator.withRegexp(ValidatorsValues.ONLY_LETTERS_AND_SPACES));
        validations.put("onlyNumbers", RegexValidator.withRegexp(ValidatorsValues.ONLY_NUMBERS));
        validations.put("maxLength", LenghtValidator.withMaxLenght(3));
        validations.put("requiredValue", requiredValueValidator);

        return validations;
    }

    private Map<String, Object> createCorrectValuesToValidate() {

        Map<String, Object> valuesToValidate = new HashMap<>();
        valuesToValidate.put("onlyLettersAndSpaces", "Dojogeek");
        valuesToValidate.put("onlyNumbers", "23456789");
        valuesToValidate.put("maxLength", "RFC");
        valuesToValidate.put("requiredValue", "DG Knowledge");

        return  valuesToValidate;
    }

    private Map<String, Object> createInvalidValuesToValidate() {

        Map<String, Object> valuesToValidate = new HashMap<>();
        valuesToValidate.put("onlyLettersAndSpaces", "Dojogeek #1");
        valuesToValidate.put("onlyNumbers", "GUAJ23456789");
        valuesToValidate.put("maxLength", "RFCD");
        valuesToValidate.put("requiredValue", "");

        return  valuesToValidate;

    }

}
