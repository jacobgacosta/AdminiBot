package io.dojogeek.adminibot.views;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LaunchIntents.class, Log.class, PaymentMethodsActivity.class})
public class PaymentMethodsActivityTest {

    @Mock
    private View mView;

    @Mock
    private TextView mLabel;

    @Mock
    private AppComponent mAppComponent;

    @Mock
    private ListView mPaymentMethodsList;

    @Mock
    private View mAddNewPaymentMethodView;

    @Mock
    private TextView mNoPaymentMethodsLabel;

    @Mock
    private LinearLayout mAddPaymentMethodOption;

    @Mock
    private AdminiBotComponent mAdminiBotComponent;

    @Mock
    private RelativeLayout mContainerPaymentMethods;

    @Mock
    private FloatingActionButton mAddNewPaymentMethodButton;

    @Mock
    private PaymentMethodsPresenter mPaymentMethodsPresenter;

    @InjectMocks
    @Spy
    private PaymentMethodsActivity mActivity = new PaymentMethodsActivity();

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRequestPaymentMethodsToPresenter() {

        mActivity.loadDataView();

        verify(mPaymentMethodsPresenter).loadAvailablePaymentMethods();
    }

    @Test
    public void testLoadViews_instantiateViews() {

        doReturn(mNoPaymentMethodsLabel).when(mActivity).findViewById(R.id.txv_notification_label);
        doReturn(mAddNewPaymentMethodButton).when(mActivity).findViewById(R.id.btn_add_payment_method);
        doReturn(mContainerPaymentMethods).when(mActivity).findViewById(R.id.container_payment_methods);
        doReturn(mPaymentMethodsList).when(mContainerPaymentMethods).findViewById(R.id.lst_payment_methods);

        mActivity.loadViews();

        verify(mActivity).findViewById(R.id.txv_notification_label);
        verify(mActivity).findViewById(R.id.add_payment_method);
        verify(mActivity).findViewById(R.id.container_payment_methods);
        verify(mContainerPaymentMethods).findViewById(R.id.lst_payment_methods);
    }

    @Test
    public void testSetupComponent_injectActivity() {

        when(mAppComponent.plus((AdminiBotModule) notNull())).thenReturn(mAdminiBotComponent);
        Mockito.doNothing().when(mAdminiBotComponent).inject(mActivity);

        mActivity.setupComponent(mAppComponent);

        verify(mAppComponent).plus((AdminiBotModule) notNull());
        verify(mAdminiBotComponent).inject(mActivity);
    }

    @Test
    public void testFlowView_withPaymentMethods() throws Exception {

        List<TypePaymentMethodEnum> typePaymentMethodEnumList =
                ModelsFactory.createTypePaymentMethodEnumList();

        Mockito.doNothing().when(mActivity).setTitle(R.string.make_payment_with);

        PaymentMethodAdapter paymentMethodAdapterMock = mock(PaymentMethodAdapter.class);

        whenNew(PaymentMethodAdapter.class).withArguments(mActivity, typePaymentMethodEnumList).
                thenReturn(paymentMethodAdapterMock);

        mActivity.showRegistered(typePaymentMethodEnumList);

        verify(mPaymentMethodsList).setAdapter(paymentMethodAdapterMock);

        verify(mActivity).setTitle(R.string.make_payment_with);
    }

    @Test
    public void testFlowView_withNoPaymentMethods() {

        doNothing().when(mActivity).setTitle(R.string.add_payment_method);

        mActivity.showRegistered(new ArrayList<TypePaymentMethodEnum>());

        verify(mActivity).setTitle(R.string.add_payment_method);
        verify(mPaymentMethodsList).setVisibility(View.GONE);
        verify(mNoPaymentMethodsLabel).setVisibility(View.VISIBLE);
    }

    @Test
    public void testOnclickListener_nonExistentId() throws Exception {

        mockStatic(Log.class);

        PowerMockito.doReturn(0).when(Log.class, "v", anyString(), anyString());

        when(mView.getId()).thenReturn(0);

        mActivity.onClick(mView);

        verify(mView).getId();

        verifyStatic(times(1));
        Log.v(PaymentMethodsActivity.TAG, "No events for this view");
    }

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        assertThat(mActivity.getLayoutActivity(), is(R.layout.activity_payment_methods));
    }

    @Test
    public void testAddListenersToViews() {

        mActivity.addListenersToViews();

        verify(mPaymentMethodsList).setOnItemClickListener(mActivity);
        verify(mAddNewPaymentMethodButton).setOnClickListener(mActivity);
    }

    @Test
    public void testOnItemClick_launchPaymentMethodsByType() throws Exception {

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class, "launchIntentClearTop",
                any(Context.class), any(Class.class));

        TypePaymentMethodEnum [] paymentMethods = {TypePaymentMethodEnum.CREDIT_CARD,
                TypePaymentMethodEnum.CASH, TypePaymentMethodEnum.FOOD_COUPONS};

        for (TypePaymentMethodEnum typePaymentMethodEnum : paymentMethods) {

            when(mView.getTag()).thenReturn(typePaymentMethodEnum);

            mActivity.onItemClick(Mockito.mock(AdapterView.class), mView, 0, 0);
        }

        verifyStatic();
        LaunchIntents.launchIntentClearTop(mActivity, MyCreditCardsActivity.class);
        verifyStatic();
        LaunchIntents.launchIntentClearTop(mActivity, MyCashActivity.class);
        verifyStatic();
        LaunchIntents.launchIntentClearTop(mActivity, MyFoodCouponsActivity.class);

        verify(mView, times(paymentMethods.length)).getTag();
    }

    @Test
    public void testCloseConnections_notifyToPresenter() {

        mActivity.closeConnections();

        verify(mPaymentMethodsPresenter).closeConnections();
    }

    @Test
    public void testOnclickListener_addPaymentMethod() throws Exception {

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class, "launchIntentClearTop",
                any(Context.class), any(Class.class));

        when(mView.getId()).thenReturn(R.id.btn_add_payment_method);

        mActivity.onClick(mView);

        verify(mView).getId();

        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mActivity, AddPaymentMethodActivity.class);
    }
}
