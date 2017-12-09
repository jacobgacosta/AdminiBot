package io.dojogeek.adminibot.views;

import android.support.test.espresso.intent.Intents;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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
        onView(withText(R.string.title_welcome)).check(matches(isDisplayed()));
        onView(withId(R.id.list_options)).check(matches(isDisplayed()));
        onView(withText(R.string.title_option_1)).check(matches(isDisplayed()));
        onView(withText(R.string.msg_description_1)).check(matches(isDisplayed()));
        onView(withText(R.string.title_option_2)).check(matches(isDisplayed()));
        onView(withText(R.string.msg_description_2)).check(matches(isDisplayed()));
        onView(withText(R.string.title_option_3)).check(matches(isDisplayed()));
        onView(withText(R.string.msg_description_3)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowWelcome_incomeRegistrationIsLaunched() {
        Intents.init();// start recording the fired intents

        onView(withText(R.string.title_option_1)).perform(click());

        intended(hasComponent(PaymentMethodsActivity.class.getName()));
        Intents.release(); // clear the Intents state and stop recording intents
    }

}
