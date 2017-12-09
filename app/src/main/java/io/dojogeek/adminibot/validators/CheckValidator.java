package io.dojogeek.adminibot.validators;

public class CheckValidator extends Validator{

    private static String CURRENT_ACCOUNT = "currentAccount";
    private static String CHECK_NUMBER = "checkNumber";
    private static String ALIAS = "alias";
    private static String DATE_OF_ISSUE = "dateOfIssue";
    private static String AMOUNT = "amount";
    private static String BANK = "bank";
    private static String ISSUANCE_CODE = "issuanceCode";

//    public void setCurrentAccount(String currentAccount) {
//        this.dataToValidate.put(CURRENT_ACCOUNT, currentAccount);¡
//    }

    public void setAlias(String alias) {
        this.dataToValidate.put(ALIAS, alias);
    }

    public void setCheckNumber(String checkNumber) {
        this.dataToValidate.put(CHECK_NUMBER, checkNumber);
    }

//    public void setPlaceAndDateOfIssue(String placeAndDateOfIssue) {
//        this.dataToValidate.put(DATE_OF_ISSUE, placeAndDateOfIssue);
//    }

    public void setAmount(String amount) {
        this.dataToValidate.put(AMOUNT, amount);
    }

//    public void setBank(String bank) {
//        this.dataToValidate.put(BANK, bank);
//    }

//    public void setIssuanceCode(String issuanceCode) {
//        this.dataToValidate.put(ISSUANCE_CODE, issuanceCode);
//    }

//    public boolean isValidCurrentAccount() {
//        return this.isValid(CURRENT_ACCOUNT);
//    }

    public boolean isValidCheckNumber() {
        return this.isValid(CHECK_NUMBER);
    }

//    public boolean isValidPlaceAndDateOfIssue() {
//        return this.isValid(DATE_OF_ISSUE);
//    }

    public boolean isValidAmount() {
        return this.isValid(AMOUNT);
    }

    public boolean isValidAlias() {
        return  this.isValid(ALIAS);
    }

//    public boolean isValidIssuanceCode() {
//        return this.isValid(ISSUANCE_CODE);
//    }

//    public boolean isValidBank() {
//        return this.isValid(BANK);
//    }

//    public int getErrorMessageIssuanceCode() {
//        return this.errorMessages.getAll(ISSUANCE_CODE);
//    }

//    public int getErrorMessageCurrentAccount() {
//        return this.errorMessages.getAll(CURRENT_ACCOUNT);
//    }

    public int getErrorMessageCheckNumber() {
        return this.errorMessages.get(CHECK_NUMBER);
    }

//    public int getErrorMessagePlaceAndDateOfIssue() {
//        return this.errorMessages.getAll(DATE_OF_ISSUE);
//    }

    public int getErrorMessageAmount() {
        return this.errorMessages.get(AMOUNT);
    }

    public int getErrorMessageAlias() {
        return this.errorMessages.get(ALIAS);
    }

//    public int getErrorMessageBank() {
//        return this.errorMessages.getAll(BANK);
//    }

    @Override
    protected void configureValidations() {

//        this.validations.put(CURRENT_ACCOUNT, CompoundValidatorsFactory.checkCurrentAccountValidator());
        this.validations.put(ALIAS, CompoundValidatorsFactory.cashConceptValidator());
        this.validations.put(CHECK_NUMBER, CompoundValidatorsFactory.checkNumberValidator());
//        this.validations.put(DATE_OF_ISSUE, CompoundValidatorsFactory.checkDateOfIssueValidator());
        this.validations.put(AMOUNT, CompoundValidatorsFactory.checkAmountValidator());
//        this.validations.put(BANK, CompoundValidatorsFactory.);
//        this.validations.put(ISSUANCE_CODE, CompoundValidatorsFactory.checkIssuanceCodeValidator());

    }
}
