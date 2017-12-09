package io.dojogeek.adminibot.validators;

public interface DataValidator<T> {

    boolean isValid(T t);

    int getErrorMsg();

}
