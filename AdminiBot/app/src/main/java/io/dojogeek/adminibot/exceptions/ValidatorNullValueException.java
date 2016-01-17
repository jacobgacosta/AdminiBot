package io.dojogeek.adminibot.exceptions;

public class ValidatorNullValueException extends RuntimeException {

    public ValidatorNullValueException() {}

    public ValidatorNullValueException(String message) {
        super(message);
    }

}
