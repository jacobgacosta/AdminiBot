package io.dojogeek.adminibot.validators;


import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.utils.ValidatorsValues;

public class CompoundValidatorsFactory {

    static RequiredValueValidator requiredValueValidator = new RequiredValueValidator();

    public static CompoundValidator emailValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.EMAIL_MAX_LENGTH).withErroMessage(R.string.error_wrong_length_email));
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.EMAIL_REGEXP).withErroMessage(R.string.error_wrong_format_email));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator nameValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.NAME_MAX_LENGHT).withErroMessage(R.string.error_wrong_lenght_name));
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ONLY_LETTERS_AND_SPACES).withErroMessage(R.string.error_wrong_format_name));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator lastNameValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.LAST_NAME_MAX_LENGHT).withErroMessage(R.string.error_wrong_lenght_last_name));
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ONLY_LETTERS_AND_SPACES).withErroMessage(R.string.error_wrong_format_last_name));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator passwordValidator() {
        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.PASSWORD_MAX_LENGHT).withErroMessage(R.string.error_wrong_lenght_password));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator cashConceptValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.CASH_CONCEPT_LENGHT).
                withErroMessage(R.string.error_wrong_lenght_field));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator checkCurrentAccountValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ACCOUNT_REGEX).
                withErroMessage(R.string.error_wrong_account));

        return compoundValidator;
    }

    public static CompoundValidator checkNumberValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ACCOUNT_REGEX).
                withErroMessage(R.string.error_wrong_lenght_check_number));

        return compoundValidator;
    }

    public static CompoundValidator checkDateOfIssueValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator checkAmountValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.AMOUNT_REGEX).
                withErroMessage(R.string.error_wrong_incorrect_amount));

        return compoundValidator;
    }

    public static CompoundValidator checkIssuanceCodeValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.ISSUANCE_CODE_LENGHT).
                withErroMessage(R.string.error_wrong_lenght_issuance_code));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator foodCouponCodeValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ONLY_NUMBERS).
                withErroMessage(R.string.error_wrong_only_numbers));
        compoundValidator.addValidator(LenghtValidator.withMaxLength(ValidatorsValues.FOOD_COUPON_CODE_LENGHT).
                withErroMessage(R.string.error_wrong_lenght_field));


        return compoundValidator;
    }

    public static CompoundValidator cardNumberValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.
                withMinAndMaxLength(ValidatorsValues.ACCOUNT_NUMBER_MINIMUM_LENGTH,
                        ValidatorsValues.ACCOUNT_NUMBER_MAXIMUM_LENGTH).
                withErroMessage(R.string.error_wrong_length_card_number));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }
}
