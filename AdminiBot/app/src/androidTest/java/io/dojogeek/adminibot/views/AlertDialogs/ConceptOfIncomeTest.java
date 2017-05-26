package io.dojogeek.adminibot.views.AlertDialogs;

import android.content.Context;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.views.PaymentMethodsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ConceptOfIncomeTest {

    @Rule
    public ActivityTestRule<PaymentMethodsActivity> mActivityRule = new ActivityTestRule<>(PaymentMethodsActivity.class);

    @Test
    public void testShowAlertDialog_inputConceptAndAccept()  {

        onView(withId(R.id.concept_of_income)).perform(typeText("INCOME TEST"), closeSoftKeyboard());
        onView(withText(R.string.concept_of_income_accept)).perform(click());
        onView(withId(R.id.concept_of_income)).check(doesNotExist());

        PaymentMethodsActivity paymentMethodsActivity = mActivityRule.getActivity();

        String value = paymentMethodsActivity.getApplicationContext()
                .getSharedPreferences(paymentMethodsActivity.getString(R.string.pm_preference_file_key), Context.MODE_PRIVATE)
                .getString(paymentMethodsActivity.getString(R.string.concept_of_income), null);

        assertEquals("INCOME TEST", value);
    }

    @Test
    public void testShowAlertDialog_errorIfAcceptWithEmptyConcept() {

        onView(withText(R.string.concept_of_income_accept)).perform(click());
        onView(withText(R.string.concept_of_income_error)).check(matches(isDisplayed()));

    }

    @Test
    public void testShowAlertDialog_cancelFlow()  {

        onView(withText(R.string.concept_of_income_cancel)).perform(click());
        assertTrue(mActivityRule.getActivity().isFinishing());

    }

    @Test
    public void testShowAlertDialog_isNotCancelable()  {

        onView(withId(R.id.concept_of_income)).perform(pressBack()).check(matches(isDisplayed()));

    }

}
