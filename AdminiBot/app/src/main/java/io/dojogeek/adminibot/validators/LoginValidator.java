package io.dojogeek.adminibot.validators;

import java.util.Map;

public class LoginValidator extends UserValidator {

    private static final String PASSWORD = "password";

    public void setPassword(String password) {
        dataToValidate.put(PASSWORD, password);
    }

    public boolean isValidPassword() {
        return this.isValid(PASSWORD);
    }

    public int getErrorMessagePassword() {
        return this.errorMessages.get(PASSWORD);
    }

    @Override
    protected void configureValidations() {

        this.validations.put(EMAIL, CompoundValidatorsFactory.emailValidator());

        this.validations.put(PASSWORD, CompoundValidatorsFactory.passwordValidator());

    }
}
