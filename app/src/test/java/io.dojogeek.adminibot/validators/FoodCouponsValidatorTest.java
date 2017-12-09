package io.dojogeek.adminibot.validators;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.dojogeek.adminibot.R;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FoodCouponsValidatorTest {

    private static final String CORRECT_CODE = "1234567891234566";
    private static final String CORRECT_AMOUNT = "239.89";
    private static final String CORRECT_ALIAS = "This a test alias";

    @Test
    public void testFoodCouponModel_isValid() {

        FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE,
                CORRECT_AMOUNT, CORRECT_ALIAS);

        boolean isValid = foodCouponsValidator.validate();

        assertTrue(isValid);
    }

    @Test
    public void testFoodCouponModel_emptyCode_isFalse() {

        String [] codes = {"", "     "};

        for (String code : codes) {

            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(code,
                    CORRECT_AMOUNT, CORRECT_ALIAS);
            boolean isValid = foodCouponsValidator.validate();
            assertFalse(isValid);
        }
    }

    @Test
    public void testFoodCouponModel_codeTooLong_isFalse() {

        String code = "12345678901234567";

        FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(code,
                CORRECT_AMOUNT, CORRECT_ALIAS);

        boolean isValid = foodCouponsValidator.validate();

        assertFalse(isValid);
    }

    @Test
    public void testFoodCouponModel_codeWithNoOnlyNumbers_isFalse() {

        String [] codes = {"1234985kjkhjdf", "98798jk__=?", "987923@"};

        for (String code : codes) {
            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(code,
                    CORRECT_AMOUNT, CORRECT_ALIAS);
            boolean isValid = foodCouponsValidator.validate();
            assertFalse(isValid);
        }

    }

    @Test
    public void testFoodCouponModel_incorrectAmounts() {

        String [] tooLongAmount = {"", "    ", "."};

        for (String amount : tooLongAmount) {

            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE,
                    amount, CORRECT_ALIAS);

            boolean isValidCheck = foodCouponsValidator.validate();

            Assert.assertFalse(isValidCheck);
            Assert.assertThat(foodCouponsValidator.getErrorMessageAmount(),
                    CoreMatchers.is(R.string.error_empty_value));

        }
    }

    @Test
    public void testCashModel_emptyConcept_isNotValid() {

        String [] cocepts = {"", "   "};

        for (String concept : cocepts) {

            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE,
                    CORRECT_AMOUNT, concept);

            boolean isValid = foodCouponsValidator.validate();

            assertFalse(isValid);
            Assert.assertEquals(R.string.error_empty_value, foodCouponsValidator.getErrorMessageAlias());
        }

    }

    @Test
    public void testCashModel_conceptTooLong_isNotValid() {

        String tooLongConcept = "this a test concept description too long 1234567890";

        FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE,
                CORRECT_AMOUNT, tooLongConcept);

        boolean isValid = foodCouponsValidator.validate();

        assertFalse(isValid);
        Assert.assertEquals(R.string.error_wrong_lenght_field, foodCouponsValidator.getErrorMessageAlias());
    }

    /*@Test
    public void testFoodCouponModel_incorrectExpirationDates() {

        String [] dates = {"", "   ", null};

        for (String date : dates) {
            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE, CORRECT_AMOUNT, date);
            boolean isValid = foodCouponsValidator.validate();
            assertFalse(isValid);
            assertEquals(R.string.error_empty_value, foodCouponsValidator.getErrorMessageExpirationDate());
        }

    }*/

    private FoodCouponsValidator createFoodCouponsValidator(String code, String amount, String alias) {

        FoodCouponsValidator foodCouponsValidator = new FoodCouponsValidator();
        foodCouponsValidator.setCode(code);
        foodCouponsValidator.setAmount(amount);
        foodCouponsValidator.setAlias(alias);

        return foodCouponsValidator;
    }
}
