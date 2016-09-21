package io.dojogeek.adminibot.validators;

import android.util.Log;

import io.dojogeek.adminibot.exceptions.ValidatorNullValueException;

public class LenghtValidator implements DataValidator<String> {

    private int maxLength = 0;
    private int minLength = 0;
    private int errorMsg;

    public LenghtValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    public LenghtValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public static LenghtValidator withMaxLength(int maxLenght) {
        return new LenghtValidator(0, maxLenght);
    }

    public static LenghtValidator withMinAndMaxLength(int minLength, int maxLength) {
        return new LenghtValidator(minLength, maxLength);
    }

    public LenghtValidator withErroMessage(int errorMessage) {
        this.errorMsg = errorMessage;
        return this;
    }

    @Override
    public boolean isValid(String value) {

        if (value == null) {
            throw new ValidatorNullValueException("The value can't be null");
        } else if (value.trim().length() >= minLength &&
                value.trim().length() <= maxLength) {
            return  true;
        }

        return false;
    }

    @Override
    public int getErrorMsg() {
        return errorMsg;
    }
}
