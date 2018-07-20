package io.dojogeek.adminibot.views;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.adminibot.dtos.IncomeDto;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

import static android.app.Activity.RESULT_OK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class PaymentMethodsActivityTest {

    @Rule
    public IntentsTestRule<PaymentMethodsActivity> intentsTestRule = new IntentsTestRule(PaymentMethodsActivity.class);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private PaymentMethodsPresenter presenter;

    @Before
    public void setUp() {
        PaymentMethodsActivity paymentMethodsActivity = intentsTestRule.getActivity();
        paymentMethodsActivity.mPresenter = presenter;
    }

    @Test
    public void testIncomeConcept_isAccepted() {
        onView(withId(R.id.edit_concept_of_income)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_concept_of_income)).perform(typeText("This a test concept"));
        onView(withText(R.string.msg_accept)).perform(click());
        onView(withText("This a test concept")).check(matches(isDisplayed()));
    }

    @Test
    public void testIncomeConcept_errorIfAcceptWithEmptyConcept() {
        onView(withText(R.string.msg_accept)).perform(click());
        onView(withText(R.string.error_concept_of_income)).check(matches(isDisplayed()));
    }

    @Test
    public void testIncomeConcept_cancelFlow() {
        onView(withText(R.string.msg_cancel)).perform(click());

        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @Test
    public void testIncomeConcept_isNotCancelable() {
        onView(withId(R.id.edit_concept_of_income)).perform(pressBack()).check(matches(isDisplayed()));
    }

    @Test
    public void testCash_isLaunched() {
        this.fillIncomeConcept();

        onView(withText(R.string.msg_cash)).perform(click());

        intended(hasComponent(CashIncomeActivity.class.getName()));
    }

    @Test
    public void testFoodCoupon_isLaunched() {
        this.fillIncomeConcept();

        onView(withText(R.string.msg_food_coupons)).perform(click());

        intended(hasComponent(FoodCouponIncomeActivity.class.getName()));
    }

    @Test
    public void testRegistrationSomePaymentMethods() {
        this.fillIncomeConcept();

        Intent cashIntent = new Intent();
        cashIntent.putExtra("cash", new BigDecimal("87843.89"));
        Instrumentation.ActivityResult cashIncomeResult = new Instrumentation.ActivityResult(RESULT_OK, cashIntent);
        intending(hasComponent(CashIncomeActivity.class.getName())).respondWith(cashIncomeResult);

        Intent foodCouponIntent = new Intent();
        foodCouponIntent.putExtra("food_coupon", new BigDecimal("8744.90"));
        Instrumentation.ActivityResult foodCouponResult = new Instrumentation.ActivityResult(RESULT_OK, foodCouponIntent);
        intending(hasComponent(FoodCouponIncomeActivity.class.getName())).respondWith(foodCouponResult);

        DebitCardDto debitCardDto = new DebitCardDto();
        debitCardDto.setName("Santander Zero");
        debitCardDto.setAmount("12930");
        debitCardDto.setNumber("1234 5678 9109 1234");
        Intent debitCardIntent = new Intent();
        debitCardIntent.putExtra("debit_card", debitCardDto);
        Instrumentation.ActivityResult debitCardResult = new Instrumentation.ActivityResult(RESULT_OK, debitCardIntent);
        intending(hasComponent(DebitCardActivity.class.getName())).respondWith(debitCardResult);

        onView(withText(R.string.msg_cash)).perform(click());
        onView(withText(R.string.msg_food_coupons)).perform(click());
        onView(withText(R.string.msg_debit_card)).perform(click());
        onView(withId(R.id.text_total_amount)).check(matches(isDisplayed())).check(matches(withText("$109518.79")));
    }

    @Test
    public void testClickSaveButton_withNoPaymentMethodAdded() {
        this.fillIncomeConcept();

        onView(withId(R.id.button_save_payment_methods)).perform(click());
        onView(withText(R.string.msg_alert_empty_payment_methods)).check(matches(isDisplayed()));
        onView(withText(R.string.msg_accept)).check(matches(isDisplayed()));
    }

    @Test
    public void testDebitCard_registrationViewIsLaunched() {
        this.fillIncomeConcept();

        onView(withText(R.string.msg_debit_card)).perform(click());

        intended(hasComponent(DebitCardActivity.class.getName()));
    }

    @Test
    public void testClickSaveButton_processIncomes() {
        this.fillIncomeConcept();

        Intent foodCouponIntent = new Intent();
        foodCouponIntent.putExtra("total_food_coupons", new BigDecimal("8434.89"));
        Instrumentation.ActivityResult foodCouponResult = new Instrumentation.ActivityResult(RESULT_OK, foodCouponIntent);
        intending(hasComponent(FoodCouponIncomeActivity.class.getName())).respondWith(foodCouponResult);

        Intent cashIntent = new Intent();
        cashIntent.putExtra("total_cash", new BigDecimal("8434.89"));
        Instrumentation.ActivityResult cashResult = new Instrumentation.ActivityResult(RESULT_OK, cashIntent);
        intending(hasComponent(CashIncomeActivity.class.getName())).respondWith(cashResult);

        DebitCardDto debitCardDto = new DebitCardDto();
        debitCardDto.setAmount("98943");
        debitCardDto.setName("Santander");
        debitCardDto.setNumber("1234567890123456");

        Intent debitCardIntent = new Intent();
        debitCardIntent.putExtra("debit_card", debitCardDto);
        Instrumentation.ActivityResult debitCardResult = new Instrumentation.ActivityResult(RESULT_OK, debitCardIntent);
        intending(hasComponent(DebitCardActivity.class.getName())).respondWith(debitCardResult);

        onView(withText(R.string.msg_food_coupons)).perform(click());
        onView(withText(R.string.msg_cash)).perform(click());
        onView(withText(R.string.msg_debit_card)).perform(click());

        onView(withId(R.id.button_save_payment_methods)).perform(click());
        onView(withText("Concepto: This a test concept")).check(matches(isDisplayed()));
        onView(withText("Total: $115812.78")).check(matches(isDisplayed()));

        onView(withText("Aceptar")).perform(click());

        doNothing().when(presenter).registerIncome(any(IncomeDto.class));

        verify(presenter).registerIncome(any(IncomeDto.class));
    }

    @Test
    public void testClickEditButton_launchMovementsViewWithExtras() {
        this.fillIncomeConcept();

        onView(withText(R.string.msg_food_coupons)).perform(click());
        //onView(withId(R.id.edit_food_coupon_amount)).perform(typeText("17500.09"));
        onView(withText(R.string.msg_accept)).perform(click());

        onView(withText(R.string.msg_cash)).perform(click());
        //onView(withId(R.id.edit_cash_amount)).perform(typeText("17500"));
        onView(withText(R.string.msg_accept)).perform(click());

        DebitCardDto debitCardDto = new DebitCardDto();
        debitCardDto.setName("Santander Zero");
        debitCardDto.setAmount("12930");
        debitCardDto.setNumber("1234 5678 9109 1234");

        Intent intent = new Intent();
        intent.putExtra("debit_card", debitCardDto);

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(RESULT_OK, intent);
        intending(hasComponent(DebitCardActivity.class.getName())).respondWith(result);

        onView(withText(R.string.msg_debit_card)).perform(click());

        onView(withContentDescription(R.string.msg_action_bar_edit_action)).perform(click());

        intended(allOf(
                hasComponent(MovementsActivity.class.getName()),
                hasExtra("income_concept", "This a test concept"),
                hasExtra("cash", new BigDecimal("17500.00")),
                hasExtra("food_coupons", new BigDecimal("17500.09")),
                hasExtras(allOf(
                        hasEntry(equalTo("debit_card"), hasItem(debitCardDto))
                ))
        ));
    }

    private void fillIncomeConcept() {
        onView(withId(R.id.edit_concept_of_income)).perform(typeText("This a test concept"));
        onView(withText(R.string.msg_accept)).perform(click());
    }

}
