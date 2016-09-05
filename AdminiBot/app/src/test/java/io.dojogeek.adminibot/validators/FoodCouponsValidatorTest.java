package io.dojogeek.adminibot.validators;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.views.FoodCoupons;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class FoodCouponsValidatorTest {

    private static final String CORRECT_CODE = "1234567891234566";
    private static final String CORRECT_AMOUNT = "239.89";
    private static final String CORRECT_EXPIRATION_DATE = "24/02/1988";

    @Test
    public void testFoodCouponModel_isValid() {

        FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE,
                CORRECT_AMOUNT, CORRECT_EXPIRATION_DATE);

        boolean isValid = foodCouponsValidator.validate();

        assertTrue(isValid);
    }

    @Test
    public void testFoodCouponModel_emptyCode_isFalse() {

        String [] codes = {"", "     "};

        for (String code : codes) {

            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(code,
                    CORRECT_AMOUNT, CORRECT_EXPIRATION_DATE);
            boolean isValid = foodCouponsValidator.validate();
            assertFalse(isValid);
            assertThat(foodCouponsValidator.getErrorMessageCode(), is(R.string.error_wrong_only_numbers));
        }
    }

    @Test
    public void testFoodCouponModel_codeTooLong_isFalse() {

        String code = "12345678901234567";

        FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(code,
                CORRECT_AMOUNT, CORRECT_EXPIRATION_DATE);

        boolean isValid = foodCouponsValidator.validate();

        assertFalse(isValid);
        assertThat(foodCouponsValidator.getErrorMessageCode(), is(R.string.error_wrong_lenght_field));
    }

    @Test
    public void testFoodCouponModel_codeWithNoOnlyNumbers_isFalse() {

        String [] codes = {"1234985kjkhjdf", "98798jk__=?", "987923@"};

        for (String code : codes) {
            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(code,
                    CORRECT_AMOUNT, CORRECT_EXPIRATION_DATE);
            boolean isValid = foodCouponsValidator.validate();
            assertFalse(isValid);
            assertThat(foodCouponsValidator.getErrorMessageCode(), is(R.string.error_wrong_only_numbers));
        }

    }

    @Test
    public void testFoodCouponModel_incorrectAmounts() {

        String [] tooLongAmount = {"12345678901", ".", ".900", ".90", "123456789011.90",
                "123456789.900", "1234567.901", "78..90", "23.", "11111111..", "", "123456789011.90.",
                "123456789011.90.0", "123456789011.90..", "123456789011.90.90.10", null};

        for (String amount : tooLongAmount) {

            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE,
                    amount, CORRECT_EXPIRATION_DATE);

            boolean isValidCheck = foodCouponsValidator.validate();

            Assert.assertFalse(isValidCheck);
            Assert.assertThat(foodCouponsValidator.getErrorMessageAmount(),
                    CoreMatchers.is(R.string.error_wrong_incorrect_amount));

        }
    }

    @Test
    public void testFoodCouponModel_incorrectExpirationDates() {

        String [] dates = {"", "   ", null};

        for (String date : dates) {
            FoodCouponsValidator foodCouponsValidator = createFoodCouponsValidator(CORRECT_CODE, CORRECT_AMOUNT, date);
            boolean isValid = foodCouponsValidator.validate();
            assertFalse(isValid);
            assertEquals(R.string.error_empty_value, foodCouponsValidator.getErrorMessageExpirationDate());
        }

    }

    private FoodCouponsValidator createFoodCouponsValidator(String code, String amount, String expirationDate) {

        FoodCouponsValidator foodCouponsValidator = new FoodCouponsValidator();
        foodCouponsValidator.setCode(code);
        foodCouponsValidator.setAmount(amount);
        foodCouponsValidator.setExpirationDate(expirationDate);

        return foodCouponsValidator;
    }
}
