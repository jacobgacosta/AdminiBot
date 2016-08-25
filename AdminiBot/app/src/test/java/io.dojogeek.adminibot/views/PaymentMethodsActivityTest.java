package io.dojogeek.adminibot.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import io.dojogeek.adminibot.exceptions.ArgumentException;
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
    private AppComponent mAppComponent;

    @Mock
    private AdminiBotComponent mAdminiBotComponent;

    @Mock
    private TextView mLabel;

    @Mock
    private View mAddNewPaymentMethodView;

    @Mock
    private Button mAddNewPaymentMethodButton;

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
        doReturn(mPaymentMethods).when(mContainerPaymentMethods).findViewById(R.id.payment_methods);
        doReturn(mAddNewPaymentMethodView).when(mContainerPaymentMethods).
                findViewById(R.id.add_new_payment_method_option);
        doReturn(mAddNewPaymentMethodButton).when(mAddNewPaymentMethodView).findViewById(R.id.add_payment_method);

        mPaymentMethodsActivity.loadViews();

        verify(mPaymentMethodsActivity).findViewById(R.id.container_payment_methods);
        verify(mContainerPaymentMethods).findViewById(R.id.payment_methods);
        verify(mContainerPaymentMethods).findViewById(R.id.add_new_payment_method_option);
        verify(mAddNewPaymentMethodView).findViewById(R.id.add_payment_method);
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

        LayoutInflater layoutInflaterMock = mock(LayoutInflater.class);
        doReturn(layoutInflaterMock).when(mPaymentMethodsActivity).getLayoutInflater();

        ViewGroup viewGroupMock = mock(ViewGroup.class);
        when(layoutInflaterMock.inflate(R.layout.activity_add_new_payment_method,
                mPaymentMethods, false)).thenReturn(viewGroupMock);

        TextView textViewMock = mock(TextView.class);
        when(viewGroupMock.findViewById(R.id.notification_label)).thenReturn(textViewMock);

        Button buttonMock = mock(Button.class);
        when(viewGroupMock.findViewById(R.id.add_payment_method)).thenReturn(buttonMock);

        mPaymentMethodsActivity.prepareView(typePaymentMethodEnumList);

        verify(viewGroupMock).findViewById(R.id.notification_label);
        verify(viewGroupMock).findViewById(R.id.add_payment_method);
        verify(buttonMock).setOnClickListener(mPaymentMethodsActivity);
        verify(textViewMock).setVisibility(View.GONE);
        verify(layoutInflaterMock).inflate(R.layout.activity_add_new_payment_method,
                mPaymentMethods, false);
        verify(mPaymentMethods).addFooterView(viewGroupMock, null, false);
        verify(mPaymentMethods).setAdapter((PaymentMethodAdapter) anyObject());
        verify(mPaymentMethodsActivity).setTitle(R.string.title_activity_choice_payment_method);
    }


    @Test
    public void testShowTypesPaymentMethods_emptyListPaymentMethods() {

//        mockStatic(LaunchIntents.class);
//        PowerMockito.doNothing().when(LaunchIntents.class);
//
//        LaunchIntents.launchIntentClearTop(mPaymentMethodsActivity, Class.class);

        List<TypePaymentMethodEnum> emptyTypePaymentMethodEnumList = new ArrayList<>();

        mPaymentMethodsActivity.prepareView(emptyTypePaymentMethodEnumList);

//        verifyStatic(times(1));
//        LaunchIntents.launchIntentClearTop(mPaymentMethodsActivity,
//                AddPaymentMethodActivity.class);
        verify(mPaymentMethods).setVisibility(View.GONE);
        verify(mAddNewPaymentMethodView).setVisibility(View.VISIBLE);



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

        verify(mPaymentMethods).setOnItemClickListener(mPaymentMethodsActivity);
        verify(mAddNewPaymentMethodButton).setOnClickListener(mPaymentMethodsActivity);
    }

    @Test
    public void testOnItemClick_launchPaymentMethodsByType() throws ArgumentException {

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
