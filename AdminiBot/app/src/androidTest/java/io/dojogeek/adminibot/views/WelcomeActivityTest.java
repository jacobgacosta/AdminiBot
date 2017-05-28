package io.dojogeek.adminibot.views;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WelcomeActivityTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void testShowWelcome_showOptionList() {

        onView(withText(R.string.welcome_title)).check(matches(isDisplayed()));
        onView(withId(R.id.list_options)).check(matches(isDisplayed()));
        onView(withText(R.string.title1)).check(matches(isDisplayed()));
        onView(withText(R.string.description1)).check(matches(isDisplayed()));
        onView(withText(R.string.title2)).check(matches(isDisplayed()));
        onView(withText(R.string.description2)).check(matches(isDisplayed()));

    }

    @Test
    public void testShowWelcome_paymentMethodsAreDisplayed() {

        onView(withText(R.string.title1)).perform(click());
        onView(withId(R.id.list_options)).check(doesNotExist());
        onView(withId(R.id.edit_concept_of_income)).check(matches(isDisplayed()));

    }

}
