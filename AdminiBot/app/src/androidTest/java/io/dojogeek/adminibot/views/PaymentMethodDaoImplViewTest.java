package io.dojogeek.adminibot.views;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by norveo on 3/15/17.
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class PaymentMethodDaoImplViewTest {

    @Rule
    public ActivityTestRule<PaymentMethodsActivity> mActivityRule =
            new ActivityTestRule(PaymentMethodsActivity.class);

    @Test
    public void show_spend_button() {


    }

}
