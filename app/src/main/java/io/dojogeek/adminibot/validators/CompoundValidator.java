package io.dojogeek.adminibot.validators;

import java.util.ArrayList;
import java.util.List;

public class CompoundValidator implements DataValidator<String> {

    protected List<DataValidator> validations = new ArrayList<>();
    private int errorMessage = 0;

    public void addValidator(DataValidator dataValidator) {
        validations.add(dataValidator);
    }

    @Override
    public boolean isValid(String s) {

        for (DataValidator validator : validations) {

            if (!validator.isValid(s)) {
                errorMessage = validator.getErrorMsg();
            }
        }

        return errorMessage == 0;
    }

    @Override
    public int getErrorMsg() {
        return errorMessage;

    }

}
