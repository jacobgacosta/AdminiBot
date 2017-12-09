package io.dojogeek.adminibot.validators;


import io.dojogeek.adminibot.R;

public class RequiredValueValidator implements DataValidator<String> {

    @Override
    public boolean isValid(String s) {

        return s != null && !s.trim().isEmpty();

    }

    @Override
    public int getErrorMsg() {
        return R.string.error_empty_value;
    }
}
