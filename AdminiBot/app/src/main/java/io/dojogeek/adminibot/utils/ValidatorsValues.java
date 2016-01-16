package io.dojogeek.adminibot.utils;

public interface ValidatorsValues {

    String EMAIL_REGEXP = "^([a-zA-Z0-9_\\.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+";
    String ONLY_LETTERS_AND_SPACES = "[a-zA-Z\\s']+";
    int EMAIL_MAX_LENGTH = 40;
    int NAME_MAX_LENGHT = 15;
    int LAST_NAME_MAX_LENGHT = 30;
    int PASSWORD_MAX_LENGHT = 30;

}
