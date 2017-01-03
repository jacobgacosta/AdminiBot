package io.dojogeek.adminibot.views;

import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
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
import static org.mockito.Mockito.never;
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

        CashValidator cashValidatorMock = mock(CashValidator.class);

        whenNew(CashValidator.class).withNoArguments().thenReturn(cashValidatorMock);

        when(cashValidatorMock.validate()).thenReturn(true);

        OtherPaymentMethodModel mockOtherPaymentMethodModel = mock(OtherPaymentMethodModel.class);

        whenNew(OtherPaymentMethodModel.class).withNoArguments().thenReturn(mockOtherPaymentMethodModel);

        BigDecimal mockBigDecimal = mock(BigDecimal.class);

        whenNew(BigDecimal.class).withArguments(amount).thenReturn(mockBigDecimal);

        mCashActivity.onClick(mAddCash);

        verify(mCashAlias, times(2)).getText();
        verify(mCashAmount, times(2)).getText();
        verify(cashValidatorMock).setAmount(amount);
        verify(cashValidatorMock).setConcept(alias);
        verify(cashValidatorMock).validate();
        verify(mockOtherPaymentMethodModel).setName(alias);
        verify(mockOtherPaymentMethodModel).setAvailableCredit(mockBigDecimal);
        verify(mockOtherPaymentMethodModel).setTypePaymentMethod(TypePaymentMethodEnum.CASH);
        verify(mockOtherPaymentMethodModel).setReferenceNumber("N/A");
        verify(mCashPresenter).createCash(mockOtherPaymentMethodModel);

    }

    @Test
    public void testSubmitCash_showMessageErrorAliasField() throws Exception {

        when(mAddCash.getId()).thenReturn(R.id.add_cash);

        Editable mockAliasEditable = mock(Editable.class);
        String concept = "";
        when(mCashAlias.getText()).thenReturn(mockAliasEditable);
        when(mockAliasEditable.toString()).thenReturn(concept);

        Editable mockAmountEditable = mock(Editable.class);
        String amount = "124567.89";
        when(mCashAmount.getText()).thenReturn(mockAmountEditable);
        when(mockAmountEditable.toString()).thenReturn(amount);

        CashValidator cashValidatorMock = mock(CashValidator.class);
        whenNew(CashValidator.class).withNoArguments().thenReturn(cashValidatorMock);

        boolean isValid = true;
        boolean isNotValid = false;

        when(cashValidatorMock.validate()).thenReturn(isNotValid);
        when(cashValidatorMock.isValidConcept()).thenReturn(isNotValid);
        when(cashValidatorMock.getErrorMessageConcept()).thenReturn(R.string.error_empty_value);
        when(cashValidatorMock.isValidAmount()).thenReturn(isValid);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mCashActivity).getString(R.string.error_empty_value);

        mCashActivity.onClick(mAddCash);

        verify(mAddCash).getId();
        verify(mCashAlias).getText();
        verify(mCashAmount).getText();
        verify(cashValidatorMock).setConcept(concept);
        verify(cashValidatorMock).setAmount(amount);
        verify(cashValidatorMock).validate();
        verify(cashValidatorMock).isValidConcept();
        verify(cashValidatorMock, never()).isValidAmount();
        verify(cashValidatorMock).getErrorMessageConcept();
        verify(mCashAlias).setError(errorMessage);
        verify(mCashAlias).requestFocus();
    }

    @Test
    public void testSubmitCash_showMessageErrorAmountField() throws Exception {

        when(mAddCash.getId()).thenReturn(R.id.add_cash);

        Editable mockAliasEditable = mock(Editable.class);
        String concept = "this a test concept";
        when(mCashAlias.getText()).thenReturn(mockAliasEditable);
        when(mockAliasEditable.toString()).thenReturn(concept);

        Editable mockAmountEditable = mock(Editable.class);
        String amount = "";
        when(mCashAmount.getText()).thenReturn(mockAmountEditable);
        when(mockAmountEditable.toString()).thenReturn(amount);

        CashValidator cashValidatorMock = mock(CashValidator.class);
        whenNew(CashValidator.class).withNoArguments().thenReturn(cashValidatorMock);

        boolean isValid = true;
        boolean isNotValid = false;

        when(cashValidatorMock.validate()).thenReturn(isNotValid);
        when(cashValidatorMock.isValidConcept()).thenReturn(isValid);
        when(cashValidatorMock.isValidAmount()).thenReturn(isNotValid);
        when(cashValidatorMock.getErrorMessageAmount()).thenReturn(R.string.error_empty_value);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mCashActivity).getString(R.string.error_empty_value);

        mCashActivity.onClick(mAddCash);

        verify(mAddCash).getId();
        verify(mCashAlias).getText();
        verify(mCashAmount).getText();
        verify(cashValidatorMock).setConcept(concept);
        verify(cashValidatorMock).setAmount(amount);
        verify(cashValidatorMock).validate();
        verify(cashValidatorMock).isValidConcept();
        verify(cashValidatorMock).isValidAmount();
        verify(cashValidatorMock).getErrorMessageAmount();
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

        verifyStatic();

        LaunchIntents.launchIntentClearTop(mCashActivity, MainActivity.class);
    }
    @Test
    public void testCloseConnections() {
        mCashActivity.closeConnections();
        verify(mCashPresenter).unnusedView();
    }
}
