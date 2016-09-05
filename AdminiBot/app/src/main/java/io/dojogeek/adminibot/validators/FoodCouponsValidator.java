package io.dojogeek.adminibot.validators;

public class FoodCouponsValidator extends Validator {

    private static String CODE = "code";
    private static String AMOUNT = "amount";
    private static String EXPIRATION_DATE = "expirationDate";

    public void setCode(String code) {
        this.dataToValidate.put(CODE, code);
    }

    public void setAmount(String amount) {
        this.dataToValidate.put(AMOUNT, amount);
    }

    public void setExpirationDate(String expirationDate) {
        this.dataToValidate.put(EXPIRATION_DATE, expirationDate);
    }

    public boolean isValidCode() {
        return this.isValid(CODE);
    }

    public boolean isValidAmount() {
        return this.isValid(AMOUNT);
    }

    public boolean isValidExpirationDate() {
        return this.isValid(EXPIRATION_DATE);
    }

    public int getErrorMessageCode() {
        return this.errorMessages.get(CODE);
    }

    public int getErrorMessageAmount() {
        return this.errorMessages.get(AMOUNT);
    }

    public int getErrorMessageExpirationDate() {
        return this.errorMessages.get(EXPIRATION_DATE);
    }

    @Override
    protected void configureValidations() {
        this.validations.put(CODE, CompoundValidatorsFactory.foodCouponCodeValidator());
        this.validations.put(AMOUNT, CompoundValidatorsFactory.checkAmountValidator());
        this.validations.put(EXPIRATION_DATE, CompoundValidatorsFactory.requiredValueValidator);
    }
}
