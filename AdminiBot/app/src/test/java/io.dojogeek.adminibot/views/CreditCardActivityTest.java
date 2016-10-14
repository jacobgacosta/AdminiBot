package io.dojogeek.adminibot.views;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.DatePickerFragment;
import io.dojogeek.adminibot.components.SpinnerWithInternalImage;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.models.CreditCardModel;
import io.dojogeek.adminibot.presenters.CreditCardPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.CreditCardValidator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Toast.class, CreditCardActivity.class, LaunchIntents.class})
public class CreditCardActivityTest {

    private static final int NO_RESOURCE = 0;
    private static String DATEPICKER_TAG = "datePicker";

    private EditText mCardName = mock(EditText.class);

    private EditText mCuttoffDate = mock(EditText.class);

    private EditText mCardNumber = mock(EditText.class);

    private EditText mCreditLimit = mock(EditText.class);

    private EditText mCurrentBalance = mock(EditText.class);

    private EditText mPayDayLimit = mock(EditText.class);

    @Mock
    private AppComponent mAppComponent;

    @Mock
    private AdminiBotComponent mAdminiBotComponent;

    @Mock
    private SpinnerWithInternalImage mSpinnerWithInternalImage;

//    @Mock
//    private LinearLayout mBrandsCards;

    @Mock
    private RadioGroup mCardBrands;

    @Mock
    private RadioButton mOption;

    @Mock
    private Button mButton;

    @Mock
    private CreditCardPresenter mCreditCardPresenter;

    @InjectMocks
    @Spy
    private CreditCardActivity mCreditCardActivity = new CreditCardActivity();

    @Test
    public void testGetLayout_returnCorrectLayout() {

        int actualLayout = mCreditCardActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(NO_RESOURCE)));
        assertEquals(R.layout.activity_credit_card, actualLayout);

    }

    @Test
    public void testExtendBaseActivity() {

        assertTrue(mCreditCardActivity instanceof BaseActivity);

    }

    @Test
    public void testSetupComponent_injectActivity() {

        doReturn(mAdminiBotComponent).when(mAppComponent).plus((AdminiBotModule) notNull());

        mCreditCardActivity.setupComponent(mAppComponent);

        verify(mAppComponent).plus((AdminiBotModule) notNull());
        verify(mAdminiBotComponent).inject(mCreditCardActivity);

    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mCardName).when(mCreditCardActivity).findViewById(R.id.card_name);
        doReturn(mCardNumber).when(mCreditCardActivity).findViewById(R.id.card_number);
        doReturn(mSpinnerWithInternalImage).when(mCreditCardActivity).findViewById(R.id.banks);
        doReturn(mCreditLimit).when(mCreditCardActivity).findViewById(R.id.credit_limit);
//        doReturn(mBrandsCards).when(mCreditCardActivity).findViewById(R.id.cards_brands);
        doReturn(mCardBrands).when(mCreditCardActivity).findViewById(R.id.cards_brands);


        doReturn(mCuttoffDate).when(mCreditCardActivity).findViewById(R.id.cuttoff_date);
        doReturn(mPayDayLimit).when(mCreditCardActivity).findViewById(R.id.pay_day_limit);
        doReturn(mCurrentBalance).when(mCreditCardActivity).findViewById(R.id.current_balance);
        doReturn(mButton).when(mCreditCardActivity).findViewById(R.id.create_credit_card);

        mCreditCardActivity.loadViews();

        verify(mCreditCardActivity).findViewById(R.id.card_name);
        verify(mCreditCardActivity).findViewById(R.id.card_number);
        verify(mCreditCardActivity).findViewById(R.id.banks);
        verify(mCreditCardActivity).findViewById(R.id.credit_limit);
        verify(mCreditCardActivity).findViewById(R.id.cards_brands);
        verify(mCreditCardActivity).findViewById(R.id.cuttoff_date);
        verify(mCreditCardActivity).findViewById(R.id.create_credit_card);
        verify(mCreditCardActivity).findViewById(R.id.pay_day_limit);
        verify(mCreditCardActivity).findViewById(R.id.current_balance);

    }

    @Test
    public void testAddListenerToView() {

        mCreditCardActivity.addListenersToViews();

        verify(mCuttoffDate).setOnClickListener(mCreditCardActivity);
        verify(mPayDayLimit).setOnClickListener(mCreditCardActivity);
        verify(mButton).setOnClickListener(mCreditCardActivity);
    }

    @Test
    public void testOnClickListener_launchCutoffDateDialog() throws Exception {

        View mockView = mock(View.class);

        int viewId = R.id.cuttoff_date;

        DatePickerFragment mockDatePickerFragment = mock(DatePickerFragment.class);

        when(mockView.getId()).thenReturn(viewId);
        whenNew(DatePickerFragment.class).withNoArguments().thenReturn(mockDatePickerFragment);
//        doNothing().when(mockDatePickerFragment).show(mCreditCardActivity.getSupportFragmentManager(), DATEPICKER_TAG);

        mCreditCardActivity.onClick(mockView);

        verify(mockView).getId();
        verify(mockDatePickerFragment).show(mCreditCardActivity.getSupportFragmentManager(), DATEPICKER_TAG);
        verify(mockDatePickerFragment).setIdWidgetContainerDate(R.id.cuttoff_date);

    }

    @Test
    public void testOnClickListener_launchPayDayLimitDialog() throws Exception {

        View mockView = mock(View.class);

        int viewId = R.id.pay_day_limit;

        DatePickerFragment mockDatePickerFragment = mock(DatePickerFragment.class);

        when(mockView.getId()).thenReturn(viewId);
        whenNew(DatePickerFragment.class).withNoArguments().thenReturn(mockDatePickerFragment);

        mCreditCardActivity.onClick(mockView);

        verify(mockView).getId();
        verify(mockDatePickerFragment).show(mCreditCardActivity.getSupportFragmentManager(), DATEPICKER_TAG);
        verify(mockDatePickerFragment).setIdWidgetContainerDate(R.id.pay_day_limit);

    }

    @Test
    public void testLoadDataView_setTitle() {

        Mockito.doNothing().when(mCreditCardActivity).setTitle(R.string.title_activity_add_card);

        mCreditCardActivity.loadDataView();

        verify(mCreditCardActivity).setTitle(R.string.title_activity_add_card);
    }

    @Test
    public void testSubmitCreditCard_successSubmit() throws Exception {

        int submitButton = R.id.create_credit_card;
        when(mButton.getId()).thenReturn(submitButton);

        Editable editableCardName = mock(Editable.class);
        when(mCardName.getText()).thenReturn(editableCardName);
        String cardName = "Test card";
        when(editableCardName.toString()).thenReturn(cardName);

        Editable editableCardNumber = mock(Editable.class);
        when(mCardNumber.getText()).thenReturn(editableCardNumber);
        String cardNumber = "879";
        when(editableCardNumber.toString()).thenReturn(cardNumber);

        int checkedRadioButtonId = R.id.visa;
        when(mCardBrands.getCheckedRadioButtonId()).thenReturn(checkedRadioButtonId);
//        doReturn(mOption).when(mCreditCardActivity).findViewById(checkedRadioButtonId);

        Long bankId = 1L;
        when(mSpinnerWithInternalImage.getSelectedItem()).thenReturn(bankId);

        Editable editableCreditLimit = mock(Editable.class);
        when(mCreditLimit.getText()).thenReturn(editableCreditLimit);
        String creditLimit = "98744.90";
        when(editableCreditLimit.toString()).thenReturn(creditLimit);

        Editable editableCurrentBalance = mock(Editable.class);
        when(mCurrentBalance.getText()).thenReturn(editableCurrentBalance);
        String currentBalance = "9894.76";
        when(editableCurrentBalance.toString()).thenReturn(currentBalance);

        Editable editableCuttoffDate = mock(Editable.class);
        when(mCuttoffDate.getText()).thenReturn(editableCuttoffDate);
        String cuttoffDate = "2016-9-12";
        when(editableCuttoffDate.toString()).thenReturn(cuttoffDate);

        Editable editablePayDayLimit = mock(Editable.class);
        when(mPayDayLimit.getText()).thenReturn(editablePayDayLimit);
        String payDayLimit = "2016-10-18";
        when(editablePayDayLimit.toString()).thenReturn(payDayLimit);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);
        whenNew(CreditCardValidator.class).withNoArguments().thenReturn(creditCardValidatorMock);
        when(creditCardValidatorMock.validate()).thenReturn(true);

        BankCardModel bankCardModelMock = mock(BankCardModel.class);
        whenNew(BankCardModel.class).withNoArguments().thenReturn(bankCardModelMock);

        CardDetailModel cardDetailModelMock = mock(CardDetailModel.class);
        whenNew(CardDetailModel.class).withNoArguments().thenReturn(cardDetailModelMock);

        CreditCardModel creditCardModelMock = mock(CreditCardModel.class);
        whenNew(CreditCardModel.class).withNoArguments().thenReturn(creditCardModelMock);

        mCreditCardActivity.onClick(mButton);

        verify(mButton).getId();
        verify(mCardName, times(2)).getText();
        verify(mCardNumber, times(2)).getText();
        verify(mCardBrands, times(2)).getCheckedRadioButtonId();
        verify(mSpinnerWithInternalImage, times(2)).getSelectedItem();
        verify(mCreditLimit, times(3)).getText();
        verify(mCurrentBalance, times(3)).getText();
        verify(mCuttoffDate, times(2)).getText();
        verify(mPayDayLimit, times(2)).getText();
        verify(creditCardValidatorMock).setCardNumber(cardNumber);
        verify(creditCardValidatorMock).setPaydayLimit(payDayLimit);
        verify(creditCardValidatorMock).setCuttoffDate(cuttoffDate);
        verify(creditCardValidatorMock).setCurrentBalance(currentBalance);
        verify(creditCardValidatorMock).setCardName(cardName);
        verify(creditCardValidatorMock).setCreditCardBrand("VISA");
        verify(creditCardValidatorMock).setCreditLimit(creditLimit);
        verify(bankCardModelMock).setName(cardName);
        verify(bankCardModelMock).setBrand("VISA");
        verify(bankCardModelMock).setNumber(cardNumber);
        verify(bankCardModelMock).setBankId(bankId);
        verify(bankCardModelMock).setAvailableCredit(calculateAvailableCredit(currentBalance, creditLimit));
        verify(bankCardModelMock).setCardType(CardTypeEnum.CREDIT_CARD);
        verify(cardDetailModelMock).setCuttoffDate(cuttoffDate);
        verify(cardDetailModelMock).setPayDayLimit(payDayLimit);
        verify(cardDetailModelMock).setCurrentBalance(new Double(currentBalance));
        verify(cardDetailModelMock).setCreditLimit(new Double(creditLimit));
        verify(creditCardModelMock).setBankCardModel(bankCardModelMock);
        verify(creditCardModelMock).setCardDetailModel(cardDetailModelMock);
        verify(mCreditCardPresenter).createCreditCard(creditCardModelMock);
    }

    @Test
    public void testLoadDataView_buildBanksSpinner() {

        Mockito.doNothing().when(mCreditCardActivity).setTitle(R.string.title_activity_add_card);

        mCreditCardActivity.loadDataView();

        verify(mCreditCardPresenter).loadBanks();
    }

    @Test
    public void testFillBankItemsSpinner() {

        Map<Long, Map<String, Integer>> items = new HashMap<>();

        mCreditCardActivity.fillBankItemsSpinner(items);

        verify(mSpinnerWithInternalImage).createSpinner(R.string.add_card_banks_hint, items);
    }

    @Test
    public void testSubmitCash_showMessageErrorCardNameField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);

        when(creditCardValidatorMock.isValidCardName()).thenReturn(false);

        when(creditCardValidatorMock.getCardNameError()).thenReturn(R.string.error_empty_value);

        String errorMessage = "Field error";
        doReturn(errorMessage).when(mCreditCardActivity).getString(R.string.error_empty_value);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidCardName();
        verify(mCardName).setError(errorMessage);
        verify(mCardName).requestFocus();

    }

    @Test
    public void testSubmitCash_showMessageErrorCardNumberField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);
        when(creditCardValidatorMock.isValidCardName()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardNumber()).thenReturn(false);

        when(creditCardValidatorMock.getCardNumberError()).thenReturn(R.string.error_empty_value);

        String errorMessage = "Field error";
        doReturn(errorMessage).when(mCreditCardActivity).getString(R.string.error_empty_value);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidCardNumber();
        verify(mCardNumber).setError(errorMessage);
        verify(mCardNumber).requestFocus();
    }

    @Test
    public void testSubmitCash_showMessageErrorCardBrandField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);

        when(creditCardValidatorMock.isValidCardName()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardNumber()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBrand()).thenReturn(false);

        when(creditCardValidatorMock.getCreditCardBrandError()).thenReturn(R.string.error_empty_value);

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCreditCardActivity,
                R.string.error_wrong_brand_card, Toast.LENGTH_LONG)).
                thenReturn(mockToast);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidCardBrand();
        verify(mCardBrands).requestFocus();
        verifyStatic(times(1));
        Toast.makeText(mCreditCardActivity, R.string.error_wrong_brand_card,
                Toast.LENGTH_LONG);
        verify(mockToast).show();

    }

    @Test
    public void testSubmitCash_showMessageErrorBankField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);

        when(creditCardValidatorMock.isValidCardName()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardNumber()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBrand()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBank()).thenReturn(false);

        when(creditCardValidatorMock.getBankError()).thenReturn(R.string.error_empty_value);

        String errorMessage = "Field error";
        doReturn(errorMessage).when(mCreditCardActivity).getString(R.string.error_empty_value);

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCreditCardActivity,
                R.string.error_wrong_bank, Toast.LENGTH_LONG)).
                thenReturn(mockToast);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidCardBank();
        verify(mSpinnerWithInternalImage).requestFocus();
        verifyStatic(times(1));
        Toast.makeText(mCreditCardActivity, R.string.error_wrong_bank,
                Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testSubmitCash_showMessageErrorCreditLimitField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);

        when(creditCardValidatorMock.isValidCardName()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardNumber()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBrand()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBank()).thenReturn(true);
        when(creditCardValidatorMock.isValidCreditLimit()).thenReturn(false);

        when(creditCardValidatorMock.getCreditLimitError()).thenReturn(R.string.error_empty_value);

        String errorMessage = "Field error";
        doReturn(errorMessage).when(mCreditCardActivity).getString(R.string.error_empty_value);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidCreditLimit();
        verify(mCreditLimit).setError(errorMessage);
        verify(mCreditLimit).requestFocus();

    }

    @Test
    public void testSubmitCash_showMessageErrorCurrentBalanceField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);

        when(creditCardValidatorMock.isValidCardName()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardNumber()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBrand()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBank()).thenReturn(true);
        when(creditCardValidatorMock.isValidCreditLimit()).thenReturn(true);
        when(creditCardValidatorMock.isValidCurrentBalance()).thenReturn(false);

        when(creditCardValidatorMock.getCurrentBalanceError()).thenReturn(R.string.error_empty_value);

        String errorMessage = "Field error";
        doReturn(errorMessage).when(mCreditCardActivity).getString(R.string.error_empty_value);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidCurrentBalance();
        verify(mCurrentBalance).setError(errorMessage);
        verify(mCurrentBalance).requestFocus();

    }

    @Test
    public void testSubmitCash_showMessageErrorCuttoffDateField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);

        when(creditCardValidatorMock.isValidCardName()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardNumber()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBrand()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBank()).thenReturn(true);
        when(creditCardValidatorMock.isValidCreditLimit()).thenReturn(true);
        when(creditCardValidatorMock.isValidCurrentBalance()).thenReturn(true);
        when(creditCardValidatorMock.isValidCuttoffDate()).thenReturn(false);

        when(creditCardValidatorMock.getCuttoffDateError()).thenReturn(R.string.error_empty_value);

        String errorMessage = "Field error";
        doReturn(errorMessage).when(mCreditCardActivity).getString(R.string.error_empty_value);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidCuttoffDate();
        verify(mCuttoffDate).setError(errorMessage);
        verify(mCuttoffDate).requestFocus();

    }

    @Test
    public void testSubmitCash_showMessageErrorPayDayLimitField() throws Exception {

        when(mButton.getId()).thenReturn(R.id.create_credit_card);

        CreditCardValidator creditCardValidatorMock = mock(CreditCardValidator.class);

        PowerMockito.doReturn(creditCardValidatorMock).
                when(mCreditCardActivity, method(CreditCardActivity.class, "applyFieldValidators")).withNoArguments();

        when(creditCardValidatorMock.validate()).thenReturn(false);

        when(creditCardValidatorMock.isValidCardName()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardNumber()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBrand()).thenReturn(true);
        when(creditCardValidatorMock.isValidCardBank()).thenReturn(true);
        when(creditCardValidatorMock.isValidCreditLimit()).thenReturn(true);
        when(creditCardValidatorMock.isValidCurrentBalance()).thenReturn(true);
        when(creditCardValidatorMock.isValidCuttoffDate()).thenReturn(true);
        when(creditCardValidatorMock.isValidPayDayLimit()).thenReturn(false);

        when(creditCardValidatorMock.getPayDayLimitError()).thenReturn(R.string.error_empty_value);

        String errorMessage = "Field error";
        doReturn(errorMessage).when(mCreditCardActivity).getString(R.string.error_empty_value);

        mCreditCardActivity.onClick(mButton);

        verify(creditCardValidatorMock).validate();
        verify(creditCardValidatorMock).isValidPayDayLimit();
        verify(mPayDayLimit).setError(errorMessage);
        verify(mPayDayLimit).requestFocus();

    }

    @Test
    public void testCloseConnections() {

        mCreditCardActivity.closeConnections();

        verify(mCreditCardPresenter).unnusedView();
    }

    @Test
    public void testSuccessfulInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCreditCardActivity,
                CreditCardActivity.SUCCESS_INSERTION_CREDIT_CARD, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mCreditCardActivity.notifySuccessfulInsertion();

        verifyStatic(times(1));
        Toast.makeText(mCreditCardActivity, CreditCardActivity.SUCCESS_INSERTION_CREDIT_CARD, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testErrorInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCreditCardActivity,
                CreditCardActivity.ERROR_INSERTION_CREDIT_CARD, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mCreditCardActivity.notifyErrorInsertion();

        verifyStatic(times(1));
        Toast.makeText(mCreditCardActivity, CreditCardActivity.ERROR_INSERTION_CREDIT_CARD, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testReturnToMyPaymentsMethods_invokeLaunchIntent() {

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mCreditCardActivity, Class.class);

        mCreditCardActivity.returnToMyPaymentsMethods();

        verifyStatic(times(1));

        LaunchIntents.launchIntentClearTop(mCreditCardActivity, MainActivity.class);

    }

    private double calculateAvailableCredit(String currentBalance, String creditLimit) {
        return new Double(creditLimit) - new Double(currentBalance);
    }
}