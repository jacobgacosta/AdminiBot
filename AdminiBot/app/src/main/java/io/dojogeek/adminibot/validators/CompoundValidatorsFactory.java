package io.dojogeek.adminibot.validators;


import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.utils.ValidatorsValues;

public class CompoundValidatorsFactory {

    static RequiredValueValidator requiredValueValidator = new RequiredValueValidator();

    public static CompoundValidator emailValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLenght(ValidatorsValues.EMAIL_MAX_LENGTH).withErroMessage(R.string.error_wrong_length_email));
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.EMAIL_REGEXP).withErroMessage(R.string.error_wrong_format_email));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator nameValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLenght(ValidatorsValues.NAME_MAX_LENGHT).withErroMessage(R.string.error_wrong_lenght_name));
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ONLY_LETTERS_AND_SPACES).withErroMessage(R.string.error_wrong_format_name));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

    public static CompoundValidator lastNameValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLenght(ValidatorsValues.LAST_NAME_MAX_LENGHT).withErroMessage(R.string.error_wrong_lenght_last_name));
        compoundValidator.addValidator(RegexValidator.withRegexp(ValidatorsValues.ONLY_LETTERS_AND_SPACES).withErroMessage(R.string.error_wrong_format_last_name));
        compoundValidator.addValidator(requiredValueValidator);

        return  compoundValidator;
    }

    public static CompoundValidator passwordValidator() {
        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLenght(ValidatorsValues.PASSWORD_MAX_LENGHT).withErroMessage(R.string.error_wrong_lenght_password));
        compoundValidator.addValidator(requiredValueValidator);

        return  compoundValidator;
    }

    public static CompoundValidator cashConceptValidator() {

        CompoundValidator compoundValidator = new CompoundValidator();
        compoundValidator.addValidator(LenghtValidator.withMaxLenght(ValidatorsValues.CASH_CONCEPT_LENGHT).withErroMessage(R.string.error_wrong_lenght_concept));
        compoundValidator.addValidator(requiredValueValidator);

        return compoundValidator;
    }

}
