package io.dojogeek.adminibot.views;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.design.widget.FloatingActionButton;

import org.junit.Test;
import org.mockito.Spy;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.List;
import java.util.ArrayList;

import dagger.AppComponent;
import dagger.AdminiBotModule;
import io.dojogeek.adminibot.R;
import dagger.AdminiBotComponent;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.ArgumentException;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.presenters.PaymentMethodsPresenter;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.anyObject;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LaunchIntents.class, Log.class, View.OnClickListener.class})
public class PaymentMethodsActivityTest {

    @Mock
    private RelativeLayout mContainerPaymentMethods;

    @Mock
    private LinearLayout mAddPaymentMethodOption;

    @Mock
    private ListView mPaymentMethodsList;

    @Mock
    private PaymentMethodsPresenter mPaymentMethodsPresenter;

    @Mock
    private View mView;

    @Mock
    private AppComponent mAppComponent;

    @Mock
    private AdminiBotComponent mAdminiBotComponent;

    @Mock
    private TextView mLabel;

    @Mock
    private View mAddNewPaymentMethodView;

    @Mock
    private TextView mNoPaymentMethodsLabel;

    @Mock
    private FloatingActionButton mAddNewPaymentMethodButton;

    @InjectMocks
    @Spy
    private PaymentMethodsActivity mPaymentMethodsActivity = new PaymentMethodsActivity();

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
    }

   @Test
   public void testLoadInitialData_loadPaymentMethods() {

       mPaymentMethodsActivity.loadDataView();

       verify(mPaymentMethodsPresenter).loadPaymentMethods();
   }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mContainerPaymentMethods).when(mPaymentMethodsActivity).findViewById(R.id.container_payment_methods);
        doReturn(mPaymentMethodsList).when(mContainerPaymentMethods).findViewById(R.id.payment_methods);
        doReturn(mNoPaymentMethodsLabel).when(mPaymentMethodsActivity).findViewById(R.id.notification_label);
        doReturn(mAddNewPaymentMethodButton).when(mPaymentMethodsActivity).findViewById(R.id.add_payment_method);

        mPaymentMethodsActivity.loadViews();

        verify(mPaymentMethodsActivity).findViewById(R.id.container_payment_methods);
        verify(mContainerPaymentMethods).findViewById(R.id.payment_methods);
        verify(mPaymentMethodsActivity).findViewById(R.id.notification_label);
        verify(mPaymentMethodsActivity).findViewById(R.id.add_payment_method);
    }

    @Test
    public void testSetupComponent_injectActivity() {

        when(mAppComponent.plus((AdminiBotModule) notNull())).thenReturn(mAdminiBotComponent);
        Mockito.doNothing().when(mAdminiBotComponent).inject(mPaymentMethodsActivity);

        mPaymentMethodsActivity.setupComponent(mAppComponent);

        verify(mAppComponent).plus((AdminiBotModule) notNull());
        verify(mAdminiBotComponent).inject(mPaymentMethodsActivity);
    }

    @Test
    public void testShowTypesPaymentMethods_listWithPaymentMethods() {

        List<TypePaymentMethodEnum> typePaymentMethodEnumList = ModelsFactory.createTypePaymentMethodEnumList();
        Mockito.doNothing().when(mPaymentMethodsActivity).setTitle(R.string.title_choice_payment_method);

        mPaymentMethodsActivity.prepareView(typePaymentMethodEnumList);

        verify(mPaymentMethodsList).setAdapter((PaymentMethodAdapter) anyObject());
        verify(mPaymentMethodsActivity).setTitle(R.string.title_choice_payment_method);
    }


    @Test
    public void testShowTypesPaymentMethods_emptyList() {

        doNothing().when(mPaymentMethodsActivity).setTitle(R.string.title_add_payment_method);

        List<TypePaymentMethodEnum> emptyTypePaymentMethodEnumList = new ArrayList<>();

        mPaymentMethodsActivity.prepareView(emptyTypePaymentMethodEnumList);

        verify(mPaymentMethodsActivity).setTitle(R.string.title_add_payment_method);
        verify(mPaymentMethodsList).setVisibility(View.GONE);
        verify(mNoPaymentMethodsLabel).setVisibility(View.VISIBLE);
    }

    @Test
    public void testOnclickListener_nonExistentId() throws Exception {

        mockStatic(Log.class);
        PowerMockito.doReturn(0).when(Log.class);

        Log.v(PaymentMethodsActivity.TAG, "No events for this view");

        when(mView.getId()).thenReturn(R.id.payment_methods);

        mPaymentMethodsActivity.onClick(mView);

        verify(mView).getId();
        verifyStatic(times(1));

        Log.v(PaymentMethodsActivity.TAG, "No events for this view");

    }

    @Test
    public void testGetLayoutActivity_correctLayout() {

        assertThat(mPaymentMethodsActivity.getLayoutActivity(), is(R.layout.activity_payment_methods));

    }

    @Test
    public void testAddListenersToViews() {

        mPaymentMethodsActivity.addListenersToViews();

        verify(mPaymentMethodsList).setOnItemClickListener(mPaymentMethodsActivity);
        verify(mAddNewPaymentMethodButton).setOnClickListener(mPaymentMethodsActivity);
    }

    @Test
    public void testOnItemClick_launchPaymentMethodsByType() throws ArgumentException {

        AdapterView adapterView = Mockito.mock(AdapterView.class);

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mPaymentMethodsActivity, MyCreditCardsActivity.class);

        TypePaymentMethodEnum [] paymentMethods = {TypePaymentMethodEnum.CARD,
                TypePaymentMethodEnum.CASH, TypePaymentMethodEnum.FOOD_COUPONS};

        for (TypePaymentMethodEnum typePaymentMethodEnum : paymentMethods) {

            when(mView.getTag()).thenReturn(typePaymentMethodEnum);

            mPaymentMethodsActivity.onItemClick(adapterView, mView, 0, 0);
        }

        verifyStatic();
        LaunchIntents.launchIntentClearTop(mPaymentMethodsActivity, MyCreditCardsActivity.class);
        verifyStatic();
        LaunchIntents.launchIntentClearTop(mPaymentMethodsActivity, MyCashActivity.class);
        verifyStatic();
        LaunchIntents.launchIntentClearTop(mPaymentMethodsActivity, MyFoodCouponsActivity.class);

        verify(mView, times(paymentMethods.length)).getTag();
    }

    @Test
    public void testCloseConnections() {

        mPaymentMethodsActivity.closeConnections();

        verify(mPaymentMethodsPresenter).unnusedView();
    }

    @Test
    public void testOnclickListener_addMore() throws Exception {

        mockStatic(LaunchIntents.class);
        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(eq(mPaymentMethodsActivity), any(Class.class));

        when(mView.getId()).thenReturn(R.id.add_payment_method);

        mPaymentMethodsActivity.onClick(mView);

        verify(mView).getId();
        verifyStatic(times(1));

        LaunchIntents.launchIntentClearTop(mPaymentMethodsActivity,
                AddPaymentMethodActivity.class);

    }
}
