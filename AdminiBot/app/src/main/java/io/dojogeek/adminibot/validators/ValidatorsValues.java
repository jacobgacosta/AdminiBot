package io.dojogeek.adminibot.validators;

public interface ValidatorsValues {

    String EMAIL_REGEXP = "^([a-zA-Z0-9_\\.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+";
    String ONLY_LETTERS = "[a-zA-Z]+";
    int EMAIL_MAX_LENGTH = 40;
    int NAME_MAX_LENGHT = 15;

}
