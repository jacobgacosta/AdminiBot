package io.dojogeek.adminibot.validators;

import java.util.Map;

public class UserValidator extends Validator {

    protected static final String EMAIL = "email";
    protected RequiredValueValidator requiredValueValidator = new RequiredValueValidator();

    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";

    public void setName(String name) {
        dataToValidate.put(NAME, name);
    }

    public void setLastName(String lastName) {
        dataToValidate.put(LAST_NAME, lastName);
    }

    public void setEmail(String email) {
        dataToValidate.put(EMAIL, email);
    }

    public boolean isValidName() {
        return this.isError(NAME);
    }

    public boolean isValidLastName() {
        return this.isError(LAST_NAME);
    }

    public boolean isValidEmail() {
        return this.isError(EMAIL);
    }

    public int getErrorMessageName() {
        return this.errorMessages.get(NAME);
    }

    public int getErrorMessageLastName() {
        return this.errorMessages.get(LAST_NAME);
    }

    public int getErrorMessageEmail() {
        return this.errorMessages.get(EMAIL);
    }

    @Override
    protected Map<String, DataValidator> configureValidations() {

        this.validations.put(NAME, CompoundValidatorsFactory.nameValidator());

        this.validations.put(LAST_NAME, requiredValueValidator);

        this.validations.put(EMAIL, CompoundValidatorsFactory.emailValidator());

        return validations;
    }
}
