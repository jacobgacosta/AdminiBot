package io.dojogeek.adminibot.views;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DebitCardsActivityTest {

    @Rule
    public IntentsTestRule<DebitCardsActivity> mActivityRule = new IntentsTestRule<>(DebitCardsActivity.class, false, false);

    @Test
    public void testDebitCardsList_theRegisteredCardsAreShown() {
        DebitCardDto debitCardDto = new DebitCardDto();
        debitCardDto.setAmount("17500");
        debitCardDto.setNumber("1234567891011121");
        debitCardDto.setName("Santander");

        ArrayList<DebitCardDto> debitCardDtos = new ArrayList<>();
        debitCardDtos.add(debitCardDto);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, DebitCardsActivity.class);
        intent.putExtra("debit_card", debitCardDtos);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.text_card_number)).check(matches(isDisplayed())).check(matches(withText("1234    5678    9101    1121")));
        onView(withId(R.id.text_card_balance)).check(matches(isDisplayed())).check(matches(withText("$17500")));
    }

}
