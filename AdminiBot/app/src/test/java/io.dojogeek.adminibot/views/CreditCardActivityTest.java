package io.dojogeek.adminibot.views;

import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.DatePickerFragment;
import io.dojogeek.adminibot.components.SpinnerWithInternalImage;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CreditCardActivity.class)
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

    @Mock
    private LinearLayout mBrandsCards;

    @Mock
    private ImageView mCardBrand;

    @Mock
    private Button mButton;

    @InjectMocks
    @Spy
    private CreditCardActivity mCreditCardActivity = new CreditCardActivity();

    @Test
    public void  testGetLayout_returnCorrectLayout() {

        int actualLayout = mCreditCardActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(NO_RESOURCE)));

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
        doReturn(mBrandsCards).when(mCreditCardActivity).findViewById(R.id.cards_brands);
        doReturn(mCardBrand).when(mCreditCardActivity).findViewById(R.id.visa);
        doReturn(mCardBrand).when(mCreditCardActivity).findViewById(R.id.mastercard);
        doReturn(mCardBrand).when(mCreditCardActivity).findViewById(R.id.amex);
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
        verify(mCreditCardActivity).findViewById(R.id.visa);
        verify(mCreditCardActivity).findViewById(R.id.mastercard);
        verify(mCreditCardActivity).findViewById(R.id.amex);
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
    }

    @Test
    public void testOnClickListener_launchCutoffDateDialog() throws Exception {

        View mockView = mock(View.class);

        int viewId =  R.id.cuttoff_date;

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

        int viewId =  R.id.pay_day_limit;

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
}
