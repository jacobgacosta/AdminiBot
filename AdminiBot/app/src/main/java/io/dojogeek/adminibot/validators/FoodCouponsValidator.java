package io.dojogeek.adminibot.validators;

public class FoodCouponsValidator extends Validator {

    private static String CODE = "code";
    private static String AMOUNT = "amount";
    private static String ALIAS = "alias";

    public void setCode(String code) {
        this.dataToValidate.put(CODE, code);
    }

    public void setAmount(String amount) {
        this.dataToValidate.put(AMOUNT, amount);
    }

    public void setAlias(String alias) {
        this.dataToValidate.put(ALIAS, alias);
    }

    public boolean isValidCode() {
        return this.isValid(CODE);
    }

    public boolean isValidAmount() {
        return this.isValid(AMOUNT);
    }

    public boolean isValidAlias() {
        return this.isValid(ALIAS);
    }

    public int getErrorMessageCode() {
        return this.errorMessages.get(CODE);
    }

    public int getErrorMessageAmount() {
        return this.errorMessages.get(AMOUNT);
    }

    public int getErrorMessageAlias() {
        return this.errorMessages.get(ALIAS);
    }

    @Override
    protected void configureValidations() {
        this.validations.put(CODE, CompoundValidatorsFactory.foodCouponCodeValidator());
        this.validations.put(AMOUNT, CompoundValidatorsFactory.checkAmountValidator());
        this.validations.put(ALIAS, CompoundValidatorsFactory.cashConceptValidator());
    }
}
