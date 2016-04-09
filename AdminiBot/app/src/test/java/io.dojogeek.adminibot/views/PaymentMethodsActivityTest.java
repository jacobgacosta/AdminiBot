package io.dojogeek.adminibot.views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LaunchIntents.class, Log.class, View.OnClickListener.class})
public class PaymentMethodsActivityTest {

    @Mock
    private LinearLayout mContainerPaymentMethods;

    @Mock
    private LinearLayout mAddPaymentMethodOption;

    @Mock
    private ListView mPaymentMethods;

    @Mock
    private PaymentMethodsPresenter mPaymentMethodsPresenter;

    @Mock
    private View mView;

    @Mock
    private Button mAddPaymentMethodButton;

    @Mock
    private AppComponent mAppComponent;

    @Mock
    private AdminiBotComponent mAdminiBotComponent;

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

        Mockito.doReturn(mContainerPaymentMethods).when(mPaymentMethodsActivity).findViewById(R.id.container_payment_methods);
        Mockito.doReturn(mAddPaymentMethodOption).when(mContainerPaymentMethods).findViewById(R.id.add_payment_method_container);
        Mockito.doReturn(mPaymentMethods).when(mContainerPaymentMethods).findViewById(R.id.payment_methods);

        mPaymentMethodsActivity.loadViews();

        verify(mPaymentMethodsActivity).findViewById(R.id.container_payment_methods);
        verify(mContainerPaymentMethods).findViewById(R.id.add_payment_method_container);
        verify(mContainerPaymentMethods).findViewById(R.id.payment_methods);
        verify(mContainerPaymentMethods).findViewById(R.id.add_payment_method);
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
        Mockito.doNothing().when(mPaymentMethodsActivity).setTitle(R.string.title_activity_choice_payment_method);

        mPaymentMethodsActivity.showTypesPaymentMethods(typePaymentMethodEnumList);

        verify(mPaymentMethods).setVisibility(View.VISIBLE);
        verify(mAddPaymentMethodOption).setVisibility(View.GONE);
        verify(mPaymentMethods).setAdapter((PaymentMethodAdapter) anyObject());
        verify(mPaymentMethodsActivity).setTitle(R.string.title_activity_choice_payment_method);
    }

    @Test
    public void testShowTypesPaymentMethods_emptyListPaymentMethods() {

        List<TypePaymentMethodEnum> typePaymentMethodEnumList = new ArrayList<>();
        Mockito.doNothing().when(mPaymentMethodsActivity).setTitle(R.string.title_activity_add_payment_method);

        mPaymentMethodsActivity.showTypesPaymentMethods(typePaymentMethodEnumList);

        verify(mAddPaymentMethodOption).setVisibility(View.VISIBLE);
        verify(mPaymentMethods).setVisibility(View.GONE);
        verify(mPaymentMethodsActivity).setTitle(R.string.title_activity_add_payment_method);

    }

    @Test
    public void testOnclickListener_newPaymentMethod() throws Exception {

        mockStatic(LaunchIntents.class);
        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(eq(mPaymentMethodsActivity), any(Class.class));

        when(mView.getId()).thenReturn(R.id.add_payment_method);

        mPaymentMethodsActivity.onClick(mView);

        verify(mView).getId();
        verifyStatic(times(1));

        LaunchIntents.launchIntentClearTop(eq(mPaymentMethodsActivity), any(Class.class));

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
    public void addListenersToViews() {

        mPaymentMethodsActivity.addListenersToViews();

        verify(mAddPaymentMethodButton).setOnClickListener(mPaymentMethodsActivity);
        verify(mPaymentMethods).setOnItemClickListener(mPaymentMethodsActivity);
    }

    @Test
    public void testOnItemClick_launchPaymentMethodsByType() {

        AdapterView adapterView = Mockito.mock(AdapterView.class);

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentWithExtraValues(mPaymentMethodsActivity, MyPaymentMethodsActivity.class, new HashMap<String, Serializable>());

        when(mView.getTag()).thenReturn(TypePaymentMethodEnum.CASH);

        mPaymentMethodsActivity.onItemClick(adapterView, mView, 0, 0);

        verifyStatic(times(1));

        Map<String, Serializable> flags = new HashMap<>();
        flags.put("EXTRA_SESSION_ID", TypePaymentMethodEnum.CASH);

        LaunchIntents.launchIntentWithExtraValues(mPaymentMethodsActivity, MyPaymentMethodsActivity.class, flags);

        verify(mView).getTag();
    }

    @Test
    public void testCloseConnections() {

        mPaymentMethodsActivity.closeConnections();

        verify(mPaymentMethodsPresenter).unnusedView();
    }
}
