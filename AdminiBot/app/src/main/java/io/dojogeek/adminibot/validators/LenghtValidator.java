package io.dojogeek.adminibot.validators;

import android.util.Log;

import io.dojogeek.adminibot.exceptions.ValidatorNullValueException;

public class LenghtValidator implements DataValidator<String> {

    private int maxLenght = 0;
    private int errorMsg;

    public LenghtValidator(int maxLenght) {
        this.maxLenght = maxLenght;
    }

    public static LenghtValidator withMaxLenght(int maxLenght) {
        return new LenghtValidator(maxLenght);
    }

    public LenghtValidator withErroMessage(int errorMessage) {
        this.errorMsg = errorMessage;
        return this;
    }

    @Override
    public boolean isValid(String value) {

        if (value == null) {
            throw new ValidatorNullValueException("The value can't be null");
        } else if (value.length() <= maxLenght) {
            return  true;
        }

        return false;
    }

    @Override
    public int getErrorMsg() {
        return errorMsg;
    }
}
