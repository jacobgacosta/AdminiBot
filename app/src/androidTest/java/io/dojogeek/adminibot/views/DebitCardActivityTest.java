package io.dojogeek.adminibot.views;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.MediumTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DebitCardActivityTest {

    @Rule
    public IntentsTestRule<DebitCardActivity> intentsTestRule = new IntentsTestRule<>(DebitCardActivity.class);

    @Test
    public void testNewDebitCard_addOne() {
        onView(withId(R.id.edit_card_name)).perform(typeText("Card for test"));
        onView(withId(R.id.spinner_banks)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Santander"))).perform(click());
        onView(withId(R.id.spinner_banks)).check(matches(withSpinnerText(containsString("Santander"))));
        onView(withId(R.id.edit_card_number)).perform(typeText("1234567891011121")).perform(closeSoftKeyboard());
        onView(withId(R.id.edit_card_amount)).perform(typeText("47900"));

        this.saveDebitCard();

        assertEquals(RESULT_OK, intentsTestRule.getActivityResult().getResultCode());
        DebitCardDto debitCardDto = (DebitCardDto) intentsTestRule.getActivityResult().getResultData().getParcelableExtra("debit_card");
        assertEquals("Card for test", debitCardDto.getName());
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
