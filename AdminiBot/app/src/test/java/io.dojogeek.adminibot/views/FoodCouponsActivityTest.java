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
import io.dojogeek.adminibot.presenters.FoodCouponPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.FoodCouponsValidator;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
@PrepareForTest({FoodCouponsActivity.class, Toast.class, LaunchIntents.class})
public class FoodCouponsActivityTest {

    private static int NO_LAYOUT = 0;

    private EditText mNumber = mock(EditText.class);
    private EditText mAmount = mock(EditText.class);
    private EditText mAlias = mock(EditText.class);

    @Mock
    private Button mAddFoodCoupon;

    @Mock
    private FoodCouponPresenter mFoodCouponPresenter;

    @InjectMocks
    @Spy
    private FoodCouponsActivity mFoodCouponsActivity = new FoodCouponsActivity();

    @Test
    public void testExtendBaseActivity() {

        assertTrue(mFoodCouponsActivity instanceof BaseActivity);

    }

    @Test
    public void  testGetLayout_returnCorrectLayout() {

        int actualLayout = mFoodCouponsActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(NO_LAYOUT)));
        assertThat(actualLayout, is(R.layout.activity_food_coupons));
    }

    @Test
    public void testSetupComponent_injectActivity() {

        AppComponent mockAppComponent = mock(AppComponent.class);

        AdminiBotComponent mockAdminiBotComponent = mock(AdminiBotComponent.class);

        when(mockAppComponent.plus((AdminiBotModule) notNull())).thenReturn(mockAdminiBotComponent);

        mFoodCouponsActivity.setupComponent(mockAppComponent);

        verify(mockAppComponent).plus((AdminiBotModule) notNull());
        verify(mockAdminiBotComponent).inject(mFoodCouponsActivity);
    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mNumber).when(mFoodCouponsActivity).findViewById(R.id.food_coupon_number);
        doReturn(mAmount).when(mFoodCouponsActivity).findViewById(R.id.food_coupon_amount);
        doReturn(mAlias).when(mFoodCouponsActivity).findViewById(R.id.food_coupon_alias);
        doReturn(mAddFoodCoupon).when(mFoodCouponsActivity).findViewById(R.id.add_food_coupond);

        mFoodCouponsActivity.loadViews();

        verify(mFoodCouponsActivity).findViewById(R.id.food_coupon_number);
        verify(mFoodCouponsActivity).findViewById(R.id.food_coupon_amount);
        verify(mFoodCouponsActivity).findViewById(R.id.food_coupon_alias);
        verify(mFoodCouponsActivity).findViewById(R.id.add_food_coupond);
    }

    @Test
    public void testLoadDataView_setTitle() {

        doNothing().when(mFoodCouponsActivity).setTitle(R.string.title_activity_add_food_coupon);

        mFoodCouponsActivity.loadDataView();

        verify(mFoodCouponsActivity).setTitle(R.string.title_activity_add_food_coupon);

    }

    @Test
    public void testSubmitFoodCoupon_successSubmit() throws Exception {

        when(mAddFoodCoupon.getId()).thenReturn(R.id.add_food_coupond);

        String alias = "Work food coupon";
        Editable editableAliasMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(editableAliasMock);
        when(editableAliasMock.toString()).thenReturn(alias);

        String code = "1234567891234567";
        Editable editableCodeMock = mock(Editable.class);
        when(mNumber.getText()).thenReturn(editableCodeMock);
        when(editableCodeMock.toString()).thenReturn(code);

        String amount = "9875.90";
        Editable editableAmountMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(editableAmountMock);
        when(editableAmountMock.toString()).thenReturn(amount);

        FoodCouponsValidator foodCouponsValidatorMock = mock(FoodCouponsValidator.class);
        whenNew(FoodCouponsValidator.class).withNoArguments().thenReturn(foodCouponsValidatorMock);
        when(foodCouponsValidatorMock.validate()).thenReturn(true);

        OtherPaymentMethodModel otherPaymentMethodModelMock = mock(OtherPaymentMethodModel.class);
        whenNew(OtherPaymentMethodModel.class).withNoArguments().thenReturn(otherPaymentMethodModelMock);

        BigDecimal mockBigDecimal = mock(BigDecimal.class);
        whenNew(BigDecimal.class).withArguments(amount).thenReturn(mockBigDecimal);

        mFoodCouponsActivity.onClick(mAddFoodCoupon);

        verify(mAddFoodCoupon).getId();
        verify(mNumber, times(2)).getText();
        verify(mAmount, times(2)).getText();
        verify(mAlias, times(2)).getText();
        verify(foodCouponsValidatorMock).setAlias(alias);
        verify(foodCouponsValidatorMock).setCode(code);
        verify(foodCouponsValidatorMock).setAmount(amount);
        verify(foodCouponsValidatorMock).validate();
        verify(otherPaymentMethodModelMock).setReferenceNumber(code);
        verify(otherPaymentMethodModelMock).setTypePaymentMethod(TypePaymentMethodEnum.FOOD_COUPONS);
        verify(otherPaymentMethodModelMock).setName(alias);
        verify(otherPaymentMethodModelMock).setAvailableCredit(mockBigDecimal);
        verify(mFoodCouponPresenter).createFoodCoupon(otherPaymentMethodModelMock);
    }

    @Test
    public void testAddListenerToView() {

        mFoodCouponsActivity.addListenersToViews();

        verify(mAddFoodCoupon).setOnClickListener(mFoodCouponsActivity);
    }

    @Test
    public void testSuccessfulInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mFoodCouponsActivity,
                FoodCouponsActivity.SUCCESS_INSERTION_FOOD_COUPON, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mFoodCouponsActivity.notifySuccessfulInsertion();

        verifyStatic(times(1));
        Toast.makeText(mFoodCouponsActivity, FoodCouponsActivity.SUCCESS_INSERTION_FOOD_COUPON, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testErrorInsertion_invokeToask() {

        mockStatic(Toast.class);

        Toast mockToast = mock(Toast.class);

        when(Toast.makeText(mFoodCouponsActivity,
                FoodCouponsActivity.ERROR_INSERTION_FOOD_COUPON, Toast.LENGTH_LONG)).thenReturn(mockToast);

        mFoodCouponsActivity.notifyErrorInsertion();

        verifyStatic(times(1));
        Toast.makeText(mFoodCouponsActivity, FoodCouponsActivity.ERROR_INSERTION_FOOD_COUPON, Toast.LENGTH_LONG);
        verify(mockToast).show();
    }

    @Test
    public void testReturnToMyPaymentsMethods_invokeLaunchIntent() {

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mFoodCouponsActivity, Class.class);

        mFoodCouponsActivity.returnToMyPaymentsMethods();

        verifyStatic(times(1));

        LaunchIntents.launchIntentClearTop(mFoodCouponsActivity, MainActivity.class);
    }

    @Test
    public void testSubmitCash_showMessageErrorAliasField() throws Exception {

        when(mAddFoodCoupon.getId()).thenReturn(R.id.add_food_coupond);


        String emptyAlias = "";
        Editable editableAliasMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(editableAliasMock);
        when(editableAliasMock.toString()).thenReturn(emptyAlias);

        String emptyCode = "";
        Editable editableCodeMock = mock(Editable.class);
        when(mNumber.getText()).thenReturn(editableCodeMock);
        when(editableCodeMock.toString()).thenReturn(emptyCode);

        String emptyAmount = "";
        Editable editableAmountMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(editableAmountMock);
        when(editableAmountMock.toString()).thenReturn(emptyAmount);

        FoodCouponsValidator foodCouponsValidatorMock = mock(FoodCouponsValidator.class);
        whenNew(FoodCouponsValidator.class).withNoArguments().thenReturn(foodCouponsValidatorMock);
        when(foodCouponsValidatorMock.validate()).thenReturn(false);

        when(foodCouponsValidatorMock.isValidAlias()).thenReturn(false);
        when(foodCouponsValidatorMock.getErrorMessageAlias()).thenReturn(R.string.error_empty_value);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mFoodCouponsActivity).getString(R.string.error_empty_value);

        mFoodCouponsActivity.onClick(mAddFoodCoupon);

        verify(mAddFoodCoupon).getId();
        verify(mNumber).getText();
        verify(mAmount).getText();
        verify(mAlias).getText();
        verify(mAlias).setError(errorMessage);
        verify(mAlias).requestFocus();

    }

    @Test
    public void testSubmitCash_showMessageErrorCode() throws Exception {

        when(mAddFoodCoupon.getId()).thenReturn(R.id.add_food_coupond);

        String emptyAlias = "";
        Editable editableAliasMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(editableAliasMock);
        when(editableAliasMock.toString()).thenReturn(emptyAlias);

        String emptyCode = "";
        Editable editableCodeMock = mock(Editable.class);
        when(mNumber.getText()).thenReturn(editableCodeMock);
        when(editableCodeMock.toString()).thenReturn(emptyCode);

        String emptyAmount = "";
        Editable editableAmountMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(editableAmountMock);
        when(editableAmountMock.toString()).thenReturn(emptyAmount);

        FoodCouponsValidator foodCouponsValidatorMock = mock(FoodCouponsValidator.class);
        whenNew(FoodCouponsValidator.class).withNoArguments().thenReturn(foodCouponsValidatorMock);
        when(foodCouponsValidatorMock.validate()).thenReturn(false);

        when(foodCouponsValidatorMock.isValidAlias()).thenReturn(true);
        when(foodCouponsValidatorMock.isValidCode()).thenReturn(false);
        when(foodCouponsValidatorMock.getErrorMessageCode()).thenReturn(R.string.error_empty_value);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mFoodCouponsActivity).getString(R.string.error_empty_value);

        mFoodCouponsActivity.onClick(mAddFoodCoupon);

        verify(mAddFoodCoupon).getId();
        verify(mNumber).getText();
        verify(mAmount).getText();
        verify(mAlias).getText();
        verify(mNumber).setError(errorMessage);
        verify(mNumber).requestFocus();

    }

    @Test
    public void testSubmitCash_showMessageErrorAmount() throws Exception {

        when(mAddFoodCoupon.getId()).thenReturn(R.id.add_food_coupond);

        String emptyAlias = "";
        Editable editableAliasMock = mock(Editable.class);
        when(mAlias.getText()).thenReturn(editableAliasMock);
        when(editableAliasMock.toString()).thenReturn(emptyAlias);

        String emptyCode = "";
        Editable editableCodeMock = mock(Editable.class);
        when(mNumber.getText()).thenReturn(editableCodeMock);
        when(editableCodeMock.toString()).thenReturn(emptyCode);

        String emptyAmount = "";
        Editable editableAmountMock = mock(Editable.class);
        when(mAmount.getText()).thenReturn(editableAmountMock);
        when(editableAmountMock.toString()).thenReturn(emptyAmount);

        FoodCouponsValidator foodCouponsValidatorMock = mock(FoodCouponsValidator.class);
        whenNew(FoodCouponsValidator.class).withNoArguments().thenReturn(foodCouponsValidatorMock);
        when(foodCouponsValidatorMock.validate()).thenReturn(false);

        when(foodCouponsValidatorMock.isValidAlias()).thenReturn(true);
        when(foodCouponsValidatorMock.isValidCode()).thenReturn(true);
        when(foodCouponsValidatorMock.isValidAmount()).thenReturn(false);
        when(foodCouponsValidatorMock.getErrorMessageAmount()).thenReturn(R.string.error_empty_value);

        String errorMessage = "El campo es requerido";

        doReturn(errorMessage).when(mFoodCouponsActivity).getString(R.string.error_empty_value);

        mFoodCouponsActivity.onClick(mAddFoodCoupon);

        verify(mAddFoodCoupon).getId();
        verify(mNumber).getText();
        verify(mAmount).getText();
        verify(mAlias).getText();
        verify(mAmount).setError(errorMessage);
        verify(mAmount).requestFocus();

    }

    @Test
    public void testCloseConnections() {

        mFoodCouponsActivity.closeConnections();

        verify(mFoodCouponPresenter).unnusedView();
    }
}
