package io.dojogeek.adminibot.validators;

import org.junit.Test;

import io.dojogeek.adminibot.R;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CreditCardValidatorTest {

    private static String CORRECT_CARD_NAME = "This is a correct name";
    private static String CORRECT_BANK = "1";
    private static String CORRECT_CARD_NUMBER = "1234567890123456";
    private static String CORRECT_CREDIT_CARD_BRAND = "American Express";
    private static String CORRECT_CREDIT_LIMIT = "46000";
    private static String CORRECT_CURRENT_BALANCE = "32800";
    private static String CORRECT_CUTTOFF_DATE = "2016-10-25";
    private static String CORRECT_PAYDAY_LIMIT = "2016-11-18";

    @Test
    public void testCreditCardModel_isValid() {

        CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                CORRECT_BANK, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

        boolean isValidCreditCard = creditCardValidator.validate();

        assertTrue(isValidCreditCard);

    }

    @Test
    public void testCreditCardModel_emptyCardName_isNotValid() {

        String [] emptyNames = {"", "    "};

        for (String name :  emptyNames) {
            CreditCardValidator creditCardValidator = createCreditCardValidator(name,
                    CORRECT_BANK, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                    CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

            boolean isValidCreditCard = creditCardValidator.validate();

            assertFalse(isValidCreditCard);
            assertEquals(R.string.error_empty_value, creditCardValidator.getCardNameError());
        }

    }

    @Test
    public void testCreditCardModel_cardNameTooLong_isNotValid() {

        String nameTooLong = "This name is too too too too too too too too long =)";

        CreditCardValidator creditCardValidator = createCreditCardValidator(nameTooLong,
                CORRECT_BANK, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

        boolean isValidCreditCard = creditCardValidator.validate();

        assertFalse(isValidCreditCard);
        assertEquals(R.string.error_wrong_lenght_field, creditCardValidator.getCardNameError());
    }

    @Test
    public void testCreditCardModel_cardNumberWithSmallLength_isNotValid() {

        String cardNumberSmallLength = "01";

        CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                CORRECT_BANK, cardNumberSmallLength, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

        boolean isValidCreditCard = creditCardValidator.validate();

        assertFalse(isValidCreditCard);
        assertEquals(R.string.error_wrong_length_card_number, creditCardValidator.getCardNumberError());
    }

    @Test
    public void testCreditCardModel_noCardBrandSelected_isNoValid() {

        String cardBrand = "";

        CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                CORRECT_BANK, CORRECT_CARD_NUMBER, cardBrand, CORRECT_CREDIT_LIMIT,
                CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

        boolean isValidCreditCard = creditCardValidator.validate();

        assertFalse(isValidCreditCard);
        assertEquals(R.string.error_empty_value, creditCardValidator.getCreditCardBrandError());
    }

    @Test
    public void testCreditCardModel_noBankSelected_isNoValid() {

        String bankId = "";

        CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                bankId, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

        boolean isValidCreditCard = creditCardValidator.validate();

        assertFalse(isValidCreditCard);
        assertEquals(R.string.error_empty_value, creditCardValidator.getBankError());

    }

    @Test
    public void testCreditCardModel_malformedCreditLimit_isNoValid() {

        String [] amounts = {"12345678901", ".", ".900", ".90", "123456789011.90",
                "123456789.900", "1234567.901", "78..90", "23.", "11111111..", "123456789011.90.",
                "123456789011.90.0", "123456789011.90..", "123456789011.90.90.10", "", "    "};

        for (String amount : amounts) {

            CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                    CORRECT_BANK, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, amount,
                    CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

            boolean isValidCreditCard = creditCardValidator.validate();

            assertFalse(isValidCreditCard);
            assertEquals(R.string.error_wrong_incorrect_amount, creditCardValidator.getCreditLimitError());

        }
    }

    @Test
    public void testCreditCardModel_malformedCurrentBalance_isNoValid() {

        String [] amounts = {"12345678901", ".", ".900", ".90", "123456789011.90",
                "123456789.900", "1234567.901", "78..90", "23.", "11111111..", "123456789011.90.",
                "123456789011.90.0", "123456789011.90..", "123456789011.90.90.10", "", "    "};

        for (String amount : amounts) {

            CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                    CORRECT_BANK, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                    amount, CORRECT_CUTTOFF_DATE, CORRECT_PAYDAY_LIMIT);

            boolean isValidCreditCard = creditCardValidator.validate();

            assertFalse(isValidCreditCard);
            assertEquals(R.string.error_wrong_incorrect_amount, creditCardValidator.getCurrentBalanceError());

        }
    }

    @Test
    public void testCreditCardModel_emptyCuttoffDate_isNotValid() {

        String [] emptyCuttoffDates = {"", "    "};

        for (String cuttoffDate :  emptyCuttoffDates) {
            CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                    CORRECT_BANK, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                    CORRECT_CURRENT_BALANCE, cuttoffDate, CORRECT_PAYDAY_LIMIT);

            boolean isValidCreditCard = creditCardValidator.validate();

            assertFalse(isValidCreditCard);
            assertEquals(R.string.error_empty_value, creditCardValidator.getCuttoffDateError());
        }

    }

    @Test
    public void testCreditCardModel_emptyPayDayLimit_isNotValid() {

        String [] emptyPayDayLimits = {"", "    "};

        for (String payDayLimit :  emptyPayDayLimits) {
            CreditCardValidator creditCardValidator = createCreditCardValidator(CORRECT_CARD_NAME,
                    CORRECT_BANK, CORRECT_CARD_NUMBER, CORRECT_CREDIT_CARD_BRAND, CORRECT_CREDIT_LIMIT,
                    CORRECT_CURRENT_BALANCE, CORRECT_CUTTOFF_DATE, payDayLimit);

            boolean isValidCreditCard = creditCardValidator.validate();

            assertFalse(isValidCreditCard);
            assertEquals(R.string.error_empty_value, creditCardValidator.getPayDayLimitError());
        }

    }

    private CreditCardValidator createCreditCardValidator(String cardName, String bank,
                                                    String cardNumber, String creditCardBrand,
                                                    String creditLimit, String currentBalance,
                                                    String cuttoffDate, String paydayLimit) {

        CreditCardValidator creditCardValidator = new CreditCardValidator();
        creditCardValidator.setCardName(cardName);
        creditCardValidator.setBank(bank);
        creditCardValidator.setCardNumber(cardNumber);
        creditCardValidator.setCreditCardBrand(creditCardBrand);
        creditCardValidator.setCreditLimit(creditLimit);
        creditCardValidator.setCurrentBalance(currentBalance);
        creditCardValidator.setCuttoffDate(cuttoffDate);
        creditCardValidator.setPaydayLimit(paydayLimit);

        return creditCardValidator;
    }
}
