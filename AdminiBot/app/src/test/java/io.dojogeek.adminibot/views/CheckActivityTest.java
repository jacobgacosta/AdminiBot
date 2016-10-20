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
import java.util.HashMap;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.SpinnerWithInternalImage;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.presenters.CheckPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.CheckValidator;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
@PrepareForTest({CheckActivity.class, Toast.class, LaunchIntents.class})
public class CheckActivityTest {

    private static int NO_LAYOUT = 0;

    private EditText mCheckNumber = mock(EditText.class);
    private EditText mAlias = mock(EditText.class);
    private EditText mAmount = mock(EditText.class);
    private SpinnerWithInternalImage mBank = mock(SpinnerWithInternalImage.class);

    @Mock
    private Button mAddCheck;

    @Mock
    private AppComponent mAppComponent;

    @Mock
    private CheckPresenter mCheckPresenter;

    @Spy
    @InjectMocks
    private CheckActivity mCheckActivity = new CheckActivity();

    @Test
    public void testExtendBaseActivity() {

        assertTrue(mCheckActivity instanceof BaseActivity);

    }

    @Test
    public void  testGetLayout_returnCorrectLayout() {

        int actualLayout = mCheckActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(NO_LAYOUT)));
        assertEquals(R.layout.activity_check, actualLayout);
    }

    @Test
    public void testSetupComponent_injectActivity() {

        AdminiBotComponent adminiBotComponent = mock(AdminiBotComponent.class);

        when(mAppComponent.plus((AdminiBotModule) notNull())).thenReturn(adminiBotComponent);

        mCheckActivity.setupComponent(mAppComponent);

        verify(adminiBotComponent).inject(mCheckActivity);
    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mCheckNumber).when(mCheckActivity).findViewById(R.id.check_number);
        doReturn(mAmount).when(mCheckActivity).findViewById(R.id.amount);
        doReturn(mAddCheck).when(mCheckActivity).findViewById(R.id.add_check);
        doReturn(mAlias).when(mCheckActivity).findViewById(R.id.check_alias);
//        doReturn(mIssuanceCode).when(mCheckActivity).findViewById(R.id.issuance_code);

        mCheckActivity.loadViews();

        verify(mCheckActivity).findViewById(R.id.check_alias);
        verify(mCheckActivity).findViewById(R.id.check_number);
        verify(mCheckActivity).findViewById(R.id.amount);
        verify(mCheckActivity).findViewById(R.id.add_check);

//        verify(mCheckActivity).findViewById(R.id.issuance_code);
    }

    @Test
    public void testLoadDataView_setTitle() {

        doNothing().when(mCheckActivity).setTitle(R.string.title_activity_add_check);

        mCheckActivity.loadDataView();

        verify(mCheckActivity).setTitle(R.string.title_activity_add_check);

    }

    @Ignore(value = "Spinner isn't used for now")
    @Test
    public void testLoadDataView_fillBankSpinner() throws Exception {

        HashMap mockHashMap = mock(HashMap.class);

        doNothing().when(mCheckActivity).setTitle(R.string.title_activity_add_check);
        whenNew(HashMap.class).withNoArguments().thenReturn(mockHashMap);

        mCheckActivity.loadDataView();

        verify(mockHashMap).put("ic_amex", R.string.banks_amex);
        verify(mockHashMap).put("ic_visa", R.string.banks_banamex);
        verify(mockHashMap).put("ic_mastercard", R.string.banks_bancoopel);
        verify(mBank).createSpinner(R.string.add_card_banks_hint, mockHashMap);
    }

        @Test
        public void testAddListenerToView() {

        mCheckActivity.addListenersToViews();

        verify(mAddCheck).setOnClickListener(mCheckActivity);
    }

    @Test
    public void testSubmitCheck_successSubmit() throws Exception {

        View mockView = mock(View.class);

        when(mockView.getId()).thenReturn(R.id.add_check);

        Editable checkAliasEditableMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(checkAliasEditableMock);
        String alias = "This an alias test";
        when(checkAliasEditableMock.toString()).thenReturn(alias);

        Editable checkNumberEditableMock = mock(Editable.class);
        when(mCheckNumber.getText()).thenReturn(checkNumberEditableMock);
        String checkNumber = "0006853";
        when(checkNumberEditableMock.toString()).thenReturn(checkNumber);

        Editable amountEditableMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(amountEditableMock);
        String amount = "984734.90";
        when(amountEditableMock.toString()).thenReturn(amount);

        CheckValidator checkValidatorMock = mock(CheckValidator.class);

        whenNew(CheckValidator.class).withNoArguments().thenReturn(checkValidatorMock);
        when(checkValidatorMock.validate()).thenReturn(true);

        OtherPaymentMethodModel otherPaymentMethodModelMock =
                mock(OtherPaymentMethodModel.class);
        whenNew(OtherPaymentMethodModel.class).withNoArguments().
                thenReturn(otherPaymentMethodModelMock);

        mCheckActivity.onClick(mockView);

        verify(mockView).getId();
        verify(mAlias, times(2)).getText();
        verify(mCheckNumber, times(2)).getText();
        verify(mAmount, times(2)).getText();
        verify(checkValidatorMock).setAmount(amount);
        verify(checkValidatorMock).setCheckNumber(checkNumber);
        verify(checkValidatorMock).setAlias(alias);
        verify(checkValidatorMock).validate();
        verify(otherPaymentMethodModelMock).setAvailableCredit(new BigDecimal(amount));
        verify(otherPaymentMethodModelMock).setName(alias);
        verify(otherPaymentMethodModelMock).setReferenceNumber(checkNumber);
        verify(otherPaymentMethodModelMock).setTypePaymentMethod(TypePaymentMethodEnum.CHEQUE);

        verify(mCheckPresenter).createCheck(otherPaymentMethodModelMock);
    }

    @Test
    public void testSubmitCheck_showMessageErrorAliasField() throws Exception {

        when(mAddCheck.getId()).thenReturn(R.id.add_check);

        Editable checkAliasEditableMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(checkAliasEditableMock);
        String alias = "This an alias test";
        when(checkAliasEditableMock.toString()).thenReturn(alias);

        Editable checkNumberEditableMock = mock(Editable.class);
        when(mCheckNumber.getText()).thenReturn(checkNumberEditableMock);
        String checkNumber = "0006853";
        when(checkNumberEditableMock.toString()).thenReturn(checkNumber);

        Editable amountEditableMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(amountEditableMock);
        String amount = "984734.90";
        when(amountEditableMock.toString()).thenReturn(amount);

        CheckValidator checkValidatorMock = mock(CheckValidator.class);
        whenNew(CheckValidator.class).withNoArguments().thenReturn(checkValidatorMock);

        boolean isNotValid = false;
        boolean isValid = true;

        when(checkValidatorMock.validate()).thenReturn(isNotValid);
        when(checkValidatorMock.isValidAlias()).thenReturn(isNotValid);
        when(checkValidatorMock.getErrorMessageAlias()).thenReturn(R.string.error_empty_value);
        when(checkValidatorMock.isValidAmount()).thenReturn(isValid);
        when(checkValidatorMock.isValidCheckNumber()).thenReturn(isValid);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mCheckActivity).getString(R.string.error_empty_value);

        mCheckActivity.onClick(mAddCheck);

        verify(mAddCheck).getId();
        verify(mAlias).getText();
        verify(mCheckNumber).getText();
        verify(mAmount).getText();
        verify(checkValidatorMock).setAlias(alias);
        verify(checkValidatorMock).setAmount(amount);
        verify(checkValidatorMock).setCheckNumber(checkNumber);
        verify(checkValidatorMock).validate();
        verify(checkValidatorMock).isValidAlias();
        verify(checkValidatorMock).getErrorMessageAlias();
        verify(checkValidatorMock, never()).isValidAmount();
        verify(checkValidatorMock, never()).isValidCheckNumber();
    }

    @Test
    public void testSubmitCheck_showMessageErrorCheckNumber() throws Exception {

        when(mAddCheck.getId()).thenReturn(R.id.add_check);

        Editable checkAliasEditableMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(checkAliasEditableMock);
        String alias = "This an alias test";
        when(checkAliasEditableMock.toString()).thenReturn(alias);

        Editable checkNumberEditableMock = mock(Editable.class);
        when(mCheckNumber.getText()).thenReturn(checkNumberEditableMock);
        String checkNumber = "0006853";
        when(checkNumberEditableMock.toString()).thenReturn(checkNumber);

        Editable amountEditableMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(amountEditableMock);
        String amount = "984734.90";
        when(amountEditableMock.toString()).thenReturn(amount);

        CheckValidator checkValidatorMock = mock(CheckValidator.class);
        whenNew(CheckValidator.class).withNoArguments().thenReturn(checkValidatorMock);

        boolean isNotValid = false;
        boolean isValid = true;

        when(checkValidatorMock.validate()).thenReturn(isNotValid);
        when(checkValidatorMock.isValidAlias()).thenReturn(isValid);
        when(checkValidatorMock.isValidCheckNumber()).thenReturn(isNotValid);
        when(checkValidatorMock.getErrorMessageCheckNumber()).
                thenReturn(R.string.error_empty_value);
        when(checkValidatorMock.isValidAmount()).thenReturn(isValid);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mCheckActivity).getString(R.string.error_empty_value);

        mCheckActivity.onClick(mAddCheck);

        verify(mAddCheck).getId();
        verify(mAlias).getText();
        verify(mCheckNumber).getText();
        verify(mAmount).getText();
        verify(checkValidatorMock).setAlias(alias);
        verify(checkValidatorMock).setAmount(amount);
        verify(checkValidatorMock).setCheckNumber(checkNumber);
        verify(checkValidatorMock).validate();
        verify(checkValidatorMock).isValidAlias();
        verify(checkValidatorMock).isValidCheckNumber();
        verify(checkValidatorMock).getErrorMessageCheckNumber();
        verify(checkValidatorMock, never()).isValidAmount();

    }

    @Test
    public void testSubmitCheck_showMessageErrorAmount() throws Exception {

        when(mAddCheck.getId()).thenReturn(R.id.add_check);

        Editable checkAliasEditableMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(checkAliasEditableMock);
        String alias = "This an alias test";
        when(checkAliasEditableMock.toString()).thenReturn(alias);

        Editable checkNumberEditableMock = mock(Editable.class);
        when(mCheckNumber.getText()).thenReturn(checkNumberEditableMock);
        String checkNumber = "0006853";
        when(checkNumberEditableMock.toString()).thenReturn(checkNumber);

        Editable amountEditableMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(amountEditableMock);
        String amount = "984734.90";
        when(amountEditableMock.toString()).thenReturn(amount);

        CheckValidator checkValidatorMock = mock(CheckValidator.class);
        whenNew(CheckValidator.class).withNoArguments().thenReturn(checkValidatorMock);

        boolean isNotValid = false;
        boolean isValid = true;

        when(checkValidatorMock.validate()).thenReturn(isNotValid);
        when(checkValidatorMock.isValidAlias()).thenReturn(isValid);
        when(checkValidatorMock.isValidCheckNumber()).thenReturn(isValid);
        when(checkValidatorMock.isValidAmount()).thenReturn(isNotValid);
        when(checkValidatorMock.getErrorMessageAmount()).
                thenReturn(R.string.error_empty_value);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mCheckActivity).getString(R.string.error_empty_value);

        mCheckActivity.onClick(mAddCheck);

        verify(mAddCheck).getId();
        verify(mAlias).getText();
        verify(mCheckNumber).getText();
        verify(mAmount).getText();
        verify(checkValidatorMock).setAlias(alias);
        verify(checkValidatorMock).setAmount(amount);
        verify(checkValidatorMock).setCheckNumber(checkNumber);
        verify(checkValidatorMock).validate();
        verify(checkValidatorMock).isValidAlias();
        verify(checkValidatorMock).isValidCheckNumber();
        verify(checkValidatorMock).isValidAmount();
        verify(checkValidatorMock).getErrorMessageAmount();

    }

    @Test
    public void testSuccessfulInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCheckActivity, CheckActivity.SUCCESS_INSERTION_CHECK, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mCheckActivity.notifySuccessfulInsertion();

        verifyStatic(times(1));
        Toast.makeText(mCheckActivity, CheckActivity.SUCCESS_INSERTION_CHECK, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testErrorInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mCheckActivity,
                CheckActivity.ERROR_INSERTION_CHECK, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mCheckActivity.notifyErrorInsertion();

        verifyStatic(times(1));
        Toast.makeText(mCheckActivity, CheckActivity.ERROR_INSERTION_CHECK, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testReturnToMyPaymentsMethods_invokeLaunchIntent() {

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mCheckActivity, Class.class);

        mCheckActivity.returnToMyPaymentsMethods();

        verifyStatic(times(1));

        LaunchIntents.launchIntentClearTop(mCheckActivity, MainActivity.class);
    }

    @Test
    public void testCloseConnections() {
        mCheckActivity.closeConnections();
        verify(mCheckPresenter).unnusedView();
    }
}
