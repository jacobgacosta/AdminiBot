package io.dojogeek.adminibot.views;

import android.app.Instrumentation;
import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;
import io.dojogeek.adminibot.R;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WelcomeActivityTest {

    @Rule
    public IntentsTestRule<WelcomeActivity> mActivityRule = new IntentsTestRule<>(WelcomeActivity.class);

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
        intending(hasComponent(PaymentMethodsActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(RESULT_OK, new Intent()));

        onView(withText(R.string.title_option_1)).perform(click());

        intended(hasComponent(PaymentMethodsActivity.class.getName()));
    }

}
