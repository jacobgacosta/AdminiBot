package io.dojogeek.adminibot.views;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasFlag;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DebitCardActivityTest {

    @Rule
    public IntentsTestRule<DebitCardActivity> intentsTestRule = new IntentsTestRule<>(DebitCardActivity.class);

    @Test
    public void testNewDebitCard_addOne() {
        onView(withId(R.id.edit_card_name)).perform(typeText("Debit card test name"));
        onView(withId(R.id.spinner_banks)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Santander"))).perform(click());
        onView(withId(R.id.spinner_banks)).check(matches(withSpinnerText(containsString("Santander"))));
        onView(withId(R.id.edit_card_number)).perform(typeText("1234567891011121")).perform(closeSoftKeyboard());
        onView(withId(R.id.edit_card_amount)).perform(typeText("47900"));
        onView(withId(R.id.btn_add_debit_card)).perform(closeSoftKeyboard()).perform(click());
    }

    @Test
    public void testNewDebitCard_showErrorsForEmptyFields() {
        this.saveDebitCard();
        this.checkError(R.id.edit_card_name, R.string.msg_error_empty_name);
        this.fillEditText(R.id.edit_card_name, "Card for test");

        this.saveDebitCard();
        this.checkError(R.id.edit_card_number, R.string.msg_error_empty_card_number);
        this.fillEditText(R.id.edit_card_number, "4423551623005711");

        this.saveDebitCard();
        this.checkError(R.id.edit_card_amount, R.string.msg_error_empty_card_amount);
        this.fillEditText(R.id.edit_card_amount, "17500");
        this.saveDebitCard();
        intended(allOf(hasFlag(FLAG_ACTIVITY_REORDER_TO_FRONT), hasExtra(equalTo("debit_card"), any(DebitCardDto.class))));
    }

    private void saveDebitCard() {
        onView(withId(R.id.btn_add_debit_card)).perform(closeSoftKeyboard()).perform(click());
    }

    private void checkError(int editTextId, int textError) {
        onView(withId(editTextId))
                .check(matches(hasErrorText(intentsTestRule
                        .getActivity()
                        .getResources()
                        .getString(textError))));
    }

    private void fillEditText(int editTextId, String text) {
        onView(withId(editTextId)).perform(typeText(text));
    }

}
