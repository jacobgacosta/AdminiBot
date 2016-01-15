package io.dojogeek.adminibot.validators;

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

        if (value != null && !value.trim().isEmpty() && value.length() <= maxLenght) {
            return true;
        }

        return false;
    }

    @Override
    public int getErrorMsg() {
        return errorMsg;
    }
}
