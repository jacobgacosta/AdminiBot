package io.dojogeek.adminibot.views;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.presenters.CashPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.CashValidator;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CashActivity.class, Toast.class, LaunchIntents.class})
public class CashActivityTest {

    private EditText mCashAlias = mock(EditText.class);
    private EditText mCashAmount = mock(EditText.class);

    @Mock
    private CashPresenter mCashPresenter;

    @Mock
    private Button mAddCash;

    @Mock
    private AppComponent mAppComponent;

    @Mock
    private AdminiBotComponent mAdminiBotComponent;

    @Spy
    @InjectMocks
    private CashActivity mCashActivity = new CashActivity();

    @Test
    public void  testGetLayout_returnCorrectLayout() {

        int actualLayout = mCashActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(0)));
        assertThat(actualLayout, is(R.layout.activity_cash));
    }

    @Test
    public void testSetupComponent_injectActivity() {

        when(mAppComponent.plus((AdminiBotModule) notNull())).thenReturn(mAdminiBotComponent);

        mCashActivity.setupComponent(mAppComponent);

        verify(mAdminiBotComponent).inject(mCashActivity);
    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mCashAlias).when(mCashActivity).findViewById(R.id.cash_alias);
        doReturn(mCashAmount).when(mCashActivity).findViewById(R.id.cash_amount);
        doReturn(mAddCash).when(mCashActivity).findViewById(R.id.add_cash);

        mCashActivity.loadViews();

        verify(mCashActivity).findViewById(R.id.cash_alias);
        verify(mCashActivity).findViewById(R.id.cash_amount);
        verify(mCashActivity).findViewById(R.id.add_cash);

    }

    @Test
    public void testLoadDataView_setTitle() {

        doNothing().when(mCashActivity).setTitle(R.string.title_activity_add_cash);

        mCashActivity.loadDataView();

        verify(mCashActivity).setTitle(R.string.title_activity_add_cash);

    }

    @Test
    public void testSubmitCash_successSubmit() throws Exception {

        when(mAddCash.getId()).thenReturn(R.id.add_cash);

        Editable mockAliasEditable = mock(Editable.class);

        when(mCashAlias.getText()).thenReturn(mockAliasEditable);

        String alias = "This is an alias test";

        when(mockAliasEditable.toString()).thenReturn(alias);

        Editable mockAmountEditable = mock(Editable.class);

        when(mCashAmount.getText()).thenReturn(mockAmountEditable);

        String amount = "87634.89";

        when(mockAmountEditable.toString()).thenReturn(amount);

        CashModel mockCashModel = mock(CashModel.class);

        whenNew(CashModel.class).withNoArguments().thenReturn(mockCashModel);

        BigDecimal mockBigDecimal = mock(BigDecimal.class);

        whenNew(BigDecimal.class).withArguments(amount).thenReturn(mockBigDecimal);

        mCashActivity.onClick(mAddCash);

        verify(mockCashModel).setAlias(alias);
        verify(mockCashModel).setAmount(mockBigDecimal);
        verify(mCashPresenter).createCash(mockCashModel);

    }

    @Test
    public void testSubmitCash_emptyAliasField() throws Exception {

        when(mAddCash.getId()).thenReturn(R.id.add_cash);

        CashValidator mockCashValidator = mock(CashValidator.class);

        whenNew(CashValidator.class).withNoArguments().thenReturn(mockCashValidator);

        Editable mockAliasEditable = mock(Editable.class);
        String concept = "";

        when(mCashAlias.getText()).thenReturn(mockAliasEditable);
        when(mockAliasEditable.toString()).thenReturn(concept);

        Editable mockAmountEditable = mock(Editable.class);
        String amount = "124567.89";

        when(mCashAmount.getText()).thenReturn(mockAmountEditable);
        when(mockAmountEditable.toString()).thenReturn(amount);

        boolean isValid = true;
        boolean isNotValid = false;

        when(mockCashValidator.validate()).thenReturn(isNotValid);
        when(mockCashValidator.isValidConcept()).thenReturn(isNotValid);
        when(mockCashValidator.getErrorMessageConcept()).thenReturn(R.string.error_empty_value);
        when(mockCashValidator.isValidAmount()).thenReturn(isValid);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mCashActivity).getString(R.string.error_empty_value);

        mCashActivity.onClick(mAddCash);

        verify(mAddCash).getId();
        verify(mockCashValidator).setConcept(concept);
        verify(mockCashValidator).setAmount(amount);
        verify(mockCashValidator).validate();
        verify(mockCashValidator).isValidConcept();
        verify(mCashAlias).setError(errorMessage);
        verify(mCashAlias).requestFocus();


    }

    @Test
    public void testSubmitCash_emptyAmountField() throws Exception {

        when(mAddCash.getId()).thenReturn(R.id.add_cash);

        CashValidator mockCashValidator = mock(CashValidator.class);

        whenNew(CashValidator.class).withNoArguments().thenReturn(mockCashValidator);

        Editable mockAliasEditable = mock(Editable.class);
        String concept = "this a test concept";

        when(mCashAlias.getText()).thenReturn(mockAliasEditable);
        when(mockAliasEditable.toString()).thenReturn(concept);

        Editable mockAmountEditable = mock(Editable.class);
        String amount = "";

        when(mCashAmount.getText()).thenReturn(mockAmountEditable);
        when(mockAmountEditable.toString()).thenReturn(amount);

        boolean isValid = true;
        boolean isNotValid = false;

        when(mockCashValidator.validate()).thenReturn(isNotValid);
        when(mockCashValidator.isValidConcept()).thenReturn(isValid);
        when(mockCashValidator.isValidAmount()).thenReturn(isNotValid);
        when(mockCashValidator.getErrorMessageAmount()).thenReturn(R.string.error_empty_value);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mCashActivity).getString(R.string.error_empty_value);

        mCashActivity.onClick(mAddCash);

        verify(mAddCash).getId();
        verify(mockCashValidator).setConcept(concept);
        verify(mockCashValidator).setAmount(amount);
        verify(mockCashValidator).validate();
        verify(mockCashValidator).isValidAmount();
        verify(mCashAmount).setError(errorMessage);
        verify(mCashAmount).requestFocus();
    }

    @Test
    public void testAddListenerToView() {

        mCashActivity.addListenersToViews();

        verify(mAddCash).setOnClickListener(mCashActivity);
    }

    @Test
    public void testSuccessfulInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCashActivity,
                CashActivity.SUCCESS_INSERTION_CASH, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mCashActivity.notifySuccessfulInsertion();

        verifyStatic(times(1));
        Toast.makeText(mCashActivity, CashActivity.SUCCESS_INSERTION_CASH, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testErrorInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCashActivity,
                CashActivity.ERROR_INSERTION_CASH, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mCashActivity.notifyErrorInsertion();

        verifyStatic(times(1));
        Toast.makeText(mCashActivity, CashActivity.ERROR_INSERTION_CASH, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testReturnToMyPaymentsMethods_invokeLaunchIntent() {

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mCashActivity, Class.class);

        mCashActivity.returnToMyPaymentsMethods();

        verifyStatic(times(1));

        LaunchIntents.launchIntentClearTop(mCashActivity, PaymentMethodsActivity.class);
    }

}
