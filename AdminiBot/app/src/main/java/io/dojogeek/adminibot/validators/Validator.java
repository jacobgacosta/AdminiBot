package io.dojogeek.adminibot.validators;
;
import java.util.HashMap;
import java.util.Map;

public abstract class Validator {

    protected Map<String, Object> dataToValidate = new HashMap<>();
    protected Map<String, DataValidator> validations = new HashMap<>();
    protected Map<String, Integer> errorMessages = new HashMap<>();

    protected abstract void configureValidations();

    public boolean validate() {

        configureValidations();

        clearStackErrors();

        String key;
        DataValidator dataValidator;

        for (Map.Entry<String, DataValidator> entry : validations.entrySet()) {

            key = entry.getKey();

            dataValidator = entry.getValue();

            boolean isValidData = dataValidator.isValid(getValueToValidate(key));

            if (!isValidData) {
                errorMessages.put(key, dataValidator.getErrorMsg());
            }
        }

        return isValid();
    }

    public boolean isError(String key) {
        return !errorMessages.containsKey(key);
    }

    private boolean isValid() {
        return errorMessages.isEmpty();
    }

    private Object getValueToValidate(String key) {
        return dataToValidate.get(key);
    }

    private void clearStackErrors() {
        errorMessages.clear();
    }

}
