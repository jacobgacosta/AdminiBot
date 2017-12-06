package io.dojogeek.adminibot.views;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DebitCardActivityTest {

    @Rule
    public ActivityTestRule<DebitCardActivity> mActivityRule = new ActivityTestRule<>(DebitCardActivity.class);

    @Test
    public void testNewDebitCard_isAdded() {
        onView(withId(R.id.edit_card_name)).perform(typeText("Debit card test name"));
        onView(withId(R.id.spinner_banks)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Santander"))).perform(click());
        onView(withId(R.id.spinner_banks)).check(matches(withSpinnerText(containsString("Santander"))));
        onView(withId(R.id.edit_card_number)).perform(typeText("1234567891011121")).perform(closeSoftKeyboard());
        onView(withId(R.id.edit_card_amount)).perform(typeText("47900"));
        onView(withId(R.id.btn_add_debit_card)).perform(closeSoftKeyboard()).perform(click());
    }

}
