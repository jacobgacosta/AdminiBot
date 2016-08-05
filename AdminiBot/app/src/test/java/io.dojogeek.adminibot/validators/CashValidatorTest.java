package io.dojogeek.adminibot.validators;

import junit.framework.Assert;

import org.junit.Test;

import io.dojogeek.adminibot.R;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class CashValidatorTest {

    private static String CORRECT_CONCEPT = "This is a test concept";
    private static String CORRECT_AMOUNT = "67899.90";

    @Test
    public void testCash_cashModel_isValid() {

        CashValidator cashValidator = createCashValidator(CORRECT_CONCEPT, CORRECT_AMOUNT);

        boolean isValid = cashValidator.validate();

        assertTrue(isValid);
    }

    @Test
    public void testCash_emptyConcept_isNotValid() {

        String emptyConcept = "";

        CashValidator cashValidator = createCashValidator(emptyConcept, CORRECT_AMOUNT);

        boolean isValid = cashValidator.validate();

        assertFalse(isValid);
        assertEquals(R.string.error_empty_value, cashValidator.getErrorMessageConcept());
    }

    @Test
    public void testCash_emptyAmount_isNotValid() {

        String emptyAmount = "";

        CashValidator cashValidator = createCashValidator(CORRECT_CONCEPT, emptyAmount);

        boolean isValid = cashValidator.validate();

        assertFalse(isValid);
        assertEquals(R.string.error_empty_value, cashValidator.getErrorMessageAmount());
    }

    @Test
    public void testCash_conceptTooLong_isNotValid() {

        String tooLongConcept = "this a test concept description too long 1234567890";

        CashValidator cashValidator = createCashValidator(tooLongConcept, CORRECT_AMOUNT);

        boolean isValid = cashValidator.validate();

        assertFalse(isValid);
        assertEquals(R.string.error_wrong_lenght_concept, cashValidator.getErrorMessageConcept());
    }

    private CashValidator createCashValidator(String concept, String amount) {

        CashValidator cashValidator = new CashValidator();
        cashValidator.setConcept(concept);
        cashValidator.setAmount(amount);

        return cashValidator;
    }
}
