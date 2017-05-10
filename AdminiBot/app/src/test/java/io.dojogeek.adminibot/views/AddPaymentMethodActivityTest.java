package io.dojogeek.adminibot.views;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.ArgumentException;
import io.dojogeek.adminibot.utils.LaunchIntents;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LaunchIntents.class,  View.OnClickListener.class})
public class AddPaymentMethodActivityTest {

    @Mock
    private View mView;

    @Mock
    private ListView mPaymentMethodsList;

    @Mock
    private LinearLayout mAddPaymentMethod;

    @Mock
    private FloatingActionButton mAddNewPaymentMethodButton;

    @InjectMocks
    @Spy
    private AddPaymentMethodActivity mActivity = new AddPaymentMethodActivity();

    @Test
    public void testSetupComponent_injectActivity() {

        AppComponent appComponent = mock(AppComponent.class);
        AdminiBotComponent adminiBotComponent = mock(AdminiBotComponent.class);

        when(appComponent.plus((AdminiBotModule) notNull())).thenReturn(adminiBotComponent);

        mActivity.setupComponent(appComponent);

        verify(appComponent).plus((AdminiBotModule) notNull());
        verify(adminiBotComponent).inject(mActivity);
    }

    @Test
    public void testLoadViews_instantiateViews() {

        doReturn(mPaymentMethodsList).when(mActivity).findViewById(R.id.lst_payment_methods);
        doReturn(mAddNewPaymentMethodButton).when(mActivity).findViewById(R.id.btn_add_payment_method);

        mActivity.loadViews();

        verify(mActivity).findViewById(R.id.lst_payment_methods);
        verify(mActivity).findViewById(R.id.btn_add_payment_method);
    }

    @Test
    public void testDataView_listPaymentMethods() {

        doNothing().when(mActivity).setTitle(R.string.choise_payment_method);

        mActivity.loadDataView();

        verify(mAddNewPaymentMethodButton).setVisibility(View.GONE);
        verify(mPaymentMethodsList).setAdapter((PaymentMethodAdapter) notNull());
        verify(mActivity).setTitle(R.string.choise_payment_method);
    }

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int actualLayout = mActivity.getLayoutActivity();

        assertEquals(R.layout.activity_payment_methods, actualLayout);
    }

    @Test
    public void testOnItemClick_launchPaymentMethodsByType() throws ArgumentException {

        AdapterView adapterView = mock(AdapterView.class);

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mActivity, Class.class);

        for (TypePaymentMethodEnum typePaymentMethodEnum : TypePaymentMethodEnum.values()) {

            when(mView.getTag()).thenReturn(typePaymentMethodEnum);

            mActivity.onItemClick(adapterView, mView, 0, 0);

        }

        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mActivity, CardCreationActivity.class);
        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mActivity, CashActivity.class);
        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mActivity, FoodCouponsActivity.class);

        verify(mView, times(TypePaymentMethodEnum.values().length)).getTag();
    }

    @Test
    public void testOnItemClick_nonExistentId() throws Exception {

        AdapterView adapterView = mock(AdapterView.class);

        mockStatic(Log.class);

        PowerMockito.doReturn(0).when(Log.class, "v", anyString(), anyString());

        PowerMockito.when(mView.getId()).thenReturn(0);

        mActivity.onItemClick(adapterView, mView, 0, 0);

        verify(mView).getId();

        verifyStatic(times(1));
        Log.v(AddPaymentMethodActivity.TAG, "No events for this view");
    }

    @Test
    public void testAddListenersToView() {

        mActivity.addListenersToViews();

        verify(mPaymentMethodsList).setOnItemClickListener(mActivity);
    }
}
