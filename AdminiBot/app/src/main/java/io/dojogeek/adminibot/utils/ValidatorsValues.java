package io.dojogeek.adminibot.utils;

public interface ValidatorsValues {

    String EMAIL_REGEXP = "^([a-zA-Z0-9_\\.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+";
    String ONLY_LETTERS_AND_SPACES = "[a-zA-Z\\s']+";
    String ONLY_NUMBERS = "[0-9]+";
    int EMAIL_MAX_LENGTH = 40;
    int NAME_MAX_LENGHT = 15;
    int LAST_NAME_MAX_LENGHT = 30;
    int PASSWORD_MAX_LENGHT = 30;
    int CASH_CONCEPT_LENGHT = 50;
    int ACCOUNT_LENGHT = 18;
    int DATE_LENGHT = 10;
    int ISSUANCE_CODE_LENGHT = 18;
    String AMOUNT_REGEX = "\\d[0-9]{0,10}(\\.{1}[0-9]{1,2})";
    String ACCOUNT_REGEX = "\\d[0-9]{3,17}";
    int FOOD_COUPON_CODE_LENGHT = 16;
    int ACCOUNT_NUMBER_MINIMUM_LENGTH = 3;
    int ACCOUNT_NUMBER_MAXIMUM_LENGTH = 16;

}
