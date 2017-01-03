package io.dojogeek.adminibot.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import io.dojogeek.adminibot.R;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
//@PrepareForTest(CheckActivity.class)
public class CheckValidatorTest {

    private static String CORRECT_ALIAS = "This an test alias";
    private static String CORRECT_AMOUNT = "8723.90";
    private static String CORRECT_BANK = "Santander";
    private static String CORRECT_CHECK_NUMBER = "123456789";
    private static String CORRECT_CURRENT_ACCOUNT = "129078";
    private static String CORRECT_ISSUANCE_CODE = "123456789";
    private static String CORRECT_DATE_OF_ISSUE= "24/02/1988";

    @Test
    public void testCheckModel_isValid() {

        CheckValidator checkValidator = createCheckValidator(CORRECT_ALIAS, CORRECT_AMOUNT, CORRECT_BANK,
                CORRECT_CHECK_NUMBER, CORRECT_CURRENT_ACCOUNT, CORRECT_DATE_OF_ISSUE);

        boolean isValidCheck = checkValidator.validate();

        assertTrue(isValidCheck);

    }

    @Test
    public void testCheckModel_incorrectAmounts() {

        String [] tooLongAmount = {"12345678901", ".", ".900", ".90", "123456789011.90",
                "123456789.900", "1234567.901", "78..90", "23.", "11111111..", "", "123456789011.90.",
                "123456789011.90.0", "123456789011.90..", "123456789011.90.90.10", null};

        for (String amount : tooLongAmount) {
            CheckValidator checkValidator = createCheckValidator(CORRECT_ALIAS, amount, CORRECT_BANK,
                    CORRECT_CHECK_NUMBER, CORRECT_CURRENT_ACCOUNT, CORRECT_DATE_OF_ISSUE);

            boolean isValidCheck = checkValidator.validate();

            assertFalse(isValidCheck);
            assertThat(checkValidator.getErrorMessageAmount(), is(R.string.error_wrong_incorrect_amount));

        }
    }

//    @Test
//    public void testCheckModel_incorrectCurrentAccounts() {
//
//        String [] incorrectCurrentAccounts = {"123", "1234567890123456789", "", null, "kjhka2345", "/&%$·",
//                "00980945F"};
//
//        for (String currentAccount : incorrectCurrentAccounts) {
//            CheckValidator checkValidator = createCheckValidator(CORRECT_AMOUNT, CORRECT_BANK, CORRECT_CHECK_NUMBER,
//                    currentAccount, CORRECT_DATE_OF_ISSUE);
//
//            boolean isValidCheck = checkValidator.validate();
//
//            assertFalse(isValidCheck);
//            assertThat(checkValidator.getErrorMessageCurrentAccount(), is(R.string.error_wrong_account));
//        }
//    }

    @Test
    public void testCheckModel_incorrectCheckNumbers() {

        String [] incorrectCheckNumber = {"1234567890123456780", "(/&%$·", "", null, "98437984734P",
                "_"};

        for (String checkNumber :  incorrectCheckNumber) {
            CheckValidator checkValidator = createCheckValidator(CORRECT_ALIAS, CORRECT_AMOUNT, CORRECT_BANK,
                    checkNumber, CORRECT_CURRENT_ACCOUNT, CORRECT_DATE_OF_ISSUE);

            boolean isValidCheck = checkValidator.validate();

            assertFalse(isValidCheck);
            assertThat(checkValidator.getErrorMessageCheckNumber(), is(R.string.error_wrong_lenght_check_number));
        }
    }

    @Test
    public void testCheckAlias_emptyAlias() {

        String alias = "";

        CheckValidator checkValidator = createCheckValidator(alias, CORRECT_AMOUNT, CORRECT_BANK,
                CORRECT_CHECK_NUMBER, CORRECT_CURRENT_ACCOUNT, CORRECT_DATE_OF_ISSUE);

        boolean isValidCheck = checkValidator.validate();

        assertFalse(isValidCheck);
        assertEquals(R.string.error_empty_value, checkValidator.getErrorMessageAlias());
    }

    @Test
    public void testCheckAlias_tooLongAlias() {

        String alias = "This alias is very very very long for be correct =(";

        CheckValidator checkValidator = createCheckValidator(alias, CORRECT_AMOUNT, CORRECT_BANK,
                CORRECT_CHECK_NUMBER, CORRECT_CURRENT_ACCOUNT, CORRECT_DATE_OF_ISSUE);

        boolean isValidCheck = checkValidator.validate();

        assertFalse(isValidCheck);
        assertEquals(R.string.error_wrong_lenght_field, checkValidator.getErrorMessageAlias());
    }


//    @Test
//    public void testCheckModel_emptyDate() {
//
//        String date = "";
//
//        CheckValidator checkValidator = createCheckValidator(CORRECT_AMOUNT, CORRECT_BANK, CORRECT_CHECK_NUMBER,
//                CORRECT_CURRENT_ACCOUNT, date);
//
//        boolean isValidCheck = checkValidator.validate();
//
//        assertFalse(isValidCheck);
//        assertThat(checkValidator.getErrorMessagePlaceAndDateOfIssue(), is(R.string.error_empty_value));
//
//    }

    private CheckValidator createCheckValidator(String alias, String amount, String bank, String checkNumber,
                                                String currentAccount, String dateOfIssue) {

        CheckValidator checkValidator = new CheckValidator();
        checkValidator.setAlias(alias);
        checkValidator.setAmount(amount);
//        checkValidator.setBank(bank);
        checkValidator.setCheckNumber(checkNumber);
//        checkValidator.setCurrentAccount(currentAccount);
//        checkValidator.setIssuanceCode(issuanceCode);
//        checkValidator.setPlaceAndDateOfIssue(dateOfIssue);

        return checkValidator;

    }
}
