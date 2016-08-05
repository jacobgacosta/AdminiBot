package io.dojogeek.adminibot.validators;

public class CashValidator extends Validator {

    private static String CONCEPT = "concept";
    private static String AMOUNT = "amount";

    public void setConcept(String concept) {
        this.dataToValidate.put(CONCEPT, concept);
    }

    public void setAmount(String amount) {
        this.dataToValidate.put(AMOUNT, amount);
    }

    public boolean isValidConcept() {
        return this.isValid(CONCEPT);
    }

    public boolean isValidAmount() {
        return this.isValid(AMOUNT);
    }

    public int getErrorMessageConcept() {
        return this.errorMessages.get(CONCEPT);
    }

    public int getErrorMessageAmount() {
        return this.errorMessages.get(AMOUNT);
    }

    @Override
    protected void configureValidations() {
        this.validations.put(CONCEPT, CompoundValidatorsFactory.cashConceptValidator());
        this.validations.put(AMOUNT, CompoundValidatorsFactory.requiredValueValidator);
    }
}
