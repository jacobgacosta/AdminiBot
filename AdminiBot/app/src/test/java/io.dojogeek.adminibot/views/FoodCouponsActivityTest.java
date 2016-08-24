package io.dojogeek.adminibot.views;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.DatePickerFragment;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FoodCouponsActivity.class)
public class FoodCouponsActivityTest {

    private static String DATEPICKER_TAG = "datePicker";
    private static int NO_LAYOUT = 0;

    private EditText mCode = mock(EditText.class);
    private EditText mAmount = mock(EditText.class);
    private EditText mExpirationDate = mock(EditText.class);

    private Button mAddFoodCoupon;

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

        doReturn(mCode).when(mFoodCouponsActivity).findViewById(R.id.food_coupon_number);
        doReturn(mAmount).when(mFoodCouponsActivity).findViewById(R.id.food_coupon_amount);
        doReturn(mExpirationDate).when(mFoodCouponsActivity).findViewById(R.id.food_coupon_expiration_date);
        doReturn(mAddFoodCoupon).when(mFoodCouponsActivity).findViewById(R.id.add_food_coupond);

        mFoodCouponsActivity.loadViews();

        verify(mFoodCouponsActivity).findViewById(R.id.food_coupon_number);
        verify(mFoodCouponsActivity).findViewById(R.id.food_coupon_amount);
        verify(mFoodCouponsActivity).findViewById(R.id.food_coupon_expiration_date);
        verify(mFoodCouponsActivity).findViewById(R.id.add_food_coupond);
    }

    @Test
    public void testLoadDataView_setTitle() {

        doNothing().when(mFoodCouponsActivity).setTitle(R.string.title_activity_add_food_coupon);

        mFoodCouponsActivity.loadDataView();

        verify(mFoodCouponsActivity).setTitle(R.string.title_activity_add_food_coupon);

    }

    @Test
    public void testOnClickListener_launchCutoffDateDialog() throws Exception {

        View mockView = mock(View.class);

        int viewId =  R.id.food_coupon_expiration_date;

        DatePickerFragment mockDatePickerFragment = mock(DatePickerFragment.class);

        when(mockView.getId()).thenReturn(viewId);
        whenNew(DatePickerFragment.class).withNoArguments().thenReturn(mockDatePickerFragment);

        mFoodCouponsActivity.onClick(mockView);

        verify(mockView).getId();
        verify(mockDatePickerFragment).show(mFoodCouponsActivity.getSupportFragmentManager(), DATEPICKER_TAG);
        verify(mockDatePickerFragment).setIdWidgetContainerDate(R.id.food_coupon_expiration_date);

    }

    @Test
    public void testAddListenerToView() {

        mFoodCouponsActivity.addListenersToViews();

        verify(mExpirationDate).setOnClickListener(mFoodCouponsActivity);
    }
}
