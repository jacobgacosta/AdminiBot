package io.dojogeek.adminibot.views;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class MovementsActivityTest {

    @Rule
    public ActivityTestRule<MovementsActivity> mActivityRule = new ActivityTestRule<>(MovementsActivity.class, false, false);

    @Test
    public void testEditIncomeConcept_incomeConceptIsShownToEdit() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MovementsActivity.class);
        intent.putExtra("income_concept", "This a comment test");

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.edit_income_concept))
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

}
