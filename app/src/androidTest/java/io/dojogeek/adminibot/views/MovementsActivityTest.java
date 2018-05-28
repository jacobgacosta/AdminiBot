package io.dojogeek.adminibot.views;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class MovementsActivityTest {

    @Rule
    public IntentsTestRule<MovementsActivity> mActivityRule = new IntentsTestRule<>(MovementsActivity.class, false, false);

    @Test
    public void testEditIncomeConcept_incomeConceptIsShownToEdit() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("income_concept", "This a comment test");
        intent.putExtra("cash", new BigDecimal(17400));
        intent.putExtra("food_coupons", new BigDecimal(3800));

        DebitCardDto debitCard = new DebitCardDto();
        debitCard.setAmount("12000");
        debitCard.setNumber("1234567890112131");
        debitCard.setName("Santander");

        ArrayList<DebitCardDto> debitCardList = new ArrayList<>();
        debitCardList.add(debitCard);

        intent.putExtra("debit_card", debitCardList);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.edit_concept_of_income))
                .check(matches(isDisplayed()))
                .check(matches(withText("This a comment test")));
    }

    @Test
    public void testIncomeList_theIncomesAreShown() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("income_concept", "This a comment test");
        intent.putExtra("cash", new BigDecimal(17400));
        intent.putExtra("food_coupons", new BigDecimal(3800));

        DebitCardDto debitCard = new DebitCardDto();
        debitCard.setAmount("12000");
        debitCard.setNumber("1234567890112131");
        debitCard.setName("Santander");

        ArrayList<DebitCardDto> debitCardList = new ArrayList<>();
        debitCardList.add(debitCard);

        intent.putExtra("debit_card", debitCardList);

        mActivityRule.launchActivity(intent);

        DataInteraction dataInteraction = onData(anything()).inAdapterView(withId(R.id.list_incomes_movements));
        dataInteraction
                .atPosition(0)
                .onChildView(withId(R.id.text_income_total_amount))
                .check(matches(withText("$17400.00")));
        dataInteraction
                .atPosition(1)
                .onChildView(withId(R.id.text_income_total_amount))
                .check(matches(withText("$3800.00")));
        dataInteraction
                .atPosition(2)
                .onChildView(withId(R.id.text_income_total_amount))
                .check(matches(withText("$12000.00")));

    }

    @Test
    public void testEditIncomeConcept_errorIfAcceptWithEmptyConcept() {
        mActivityRule.launchActivity(null);

        onView(withId(R.id.button_update_movements))
                .perform(click());

        onView(withText(R.string.error_concept_of_income)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditCashAmount_showAmountToEdit() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("cash", new BigDecimal(17400));

        mActivityRule.launchActivity(intent);

        onView(withText(R.string.msg_cash)).perform(click());
        onView(withId(R.id.edit_cash_amount)).check(matches(withText("17400")));
    }

    @Test
    public void testEditCashAmount_showEmptyEditTextIfAmountIsZero() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("cash", new BigDecimal(0));

        mActivityRule.launchActivity(intent);

        onView(withText(R.string.msg_cash)).perform(click());
        onView(withId(R.id.edit_cash_amount)).check(matches(withText("")));
    }

    @Test
    public void testEditFoodCoupons_showAmountToEdit() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("food_coupons", new BigDecimal(17400));

        mActivityRule.launchActivity(intent);

        onView(withText(R.string.msg_food_coupons)).perform(click());
        onView(withId(R.id.edit_food_coupon_amount)).check(matches(withText("17400")));
    }

    @Test
    public void testEditFoodCoupons_showEmptyEditTextIfAmountIsZero() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("food_coupons", new BigDecimal(0));

        mActivityRule.launchActivity(intent);

        onView(withText(R.string.msg_food_coupons)).perform(click());
        onView(withId(R.id.edit_food_coupon_amount)).check(matches(withText("")));
    }

    @Test
    public void testEditBankCard_theRegisteredCardsIsLaunched() {
        DebitCardDto debitCardDto = new DebitCardDto();
        debitCardDto.setName("Santander Zero");
        debitCardDto.setNumber("1234567891011121");
        debitCardDto.setAmount("45000");

        ArrayList<DebitCardDto> debitCardDtos = new ArrayList<>();
        debitCardDtos.add(debitCardDto);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("debit_card", debitCardDtos);

        mActivityRule.launchActivity(intent);

        onView(withText(R.string.msg_debit_card)).perform(click());

        intended(allOf(
                hasComponent(DebitCardsActivity.class.getName()),
                hasExtras(allOf(
                        hasEntry(equalTo("debit_card"), hasItem(debitCardDto))
                ))
        ));
    }

}
