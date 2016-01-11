package io.dojogeek.adminibot.validators;

import java.util.regex.Pattern;

public class RegexValidator implements DataValidator<String> {


    private String regex;
    private int errorMsg;
    private String stringToValidate;

    public RegexValidator(String regex) {
        this.regex = regex;
    }

    public static RegexValidator withRegexp(String regex) {
        return new RegexValidator(regex);
    }

    public RegexValidator withErroMessage(int errorMessage) {
        this.errorMsg = errorMessage;
        return this;
    }

    @Override
    public boolean isValid(String s) {
        this.stringToValidate = s;
        if (s == null || s.isEmpty()) {
            return true;
        }

        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(s).matches();
    }

    @Override
    public int getErrorMsg() {
        return errorMsg;
    }
}
