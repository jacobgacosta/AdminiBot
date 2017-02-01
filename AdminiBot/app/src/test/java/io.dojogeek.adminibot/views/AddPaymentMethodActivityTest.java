package io.dojogeek.adminibot.views;

import android.support.design.widget.FloatingActionButton;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
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
    private ListView mPaymentMethodsList;

    @Mock
    private LinearLayout mAddPaymentMethod;

    @Mock
    private View mView;

    @Mock
    private FloatingActionButton mAddNewPaymentMethodButton;

    @InjectMocks
    @Spy
    private AddPaymentMethodActivity mAddPaymentMethodActivity = new AddPaymentMethodActivity();

    @Test
    public void testSetupComponent_injectActivity() {

        AppComponent appComponent = mock(AppComponent.class);
        AdminiBotComponent adminiBotComponent = mock(AdminiBotComponent.class);

        when(appComponent.plus((AdminiBotModule) notNull())).thenReturn(adminiBotComponent);

        mAddPaymentMethodActivity.setupComponent(appComponent);

        verify(appComponent).plus((AdminiBotModule) notNull());
        verify(adminiBotComponent).inject(mAddPaymentMethodActivity);

    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mPaymentMethodsList).when(mAddPaymentMethodActivity).findViewById(R.id.payment_methods);
        doReturn(mAddNewPaymentMethodButton).when(mAddPaymentMethodActivity).findViewById(R.id.add_payment_method);

        mAddPaymentMethodActivity.loadViews();

        verify(mAddPaymentMethodActivity).findViewById(R.id.payment_methods);
        verify(mAddPaymentMethodActivity).findViewById(R.id.add_payment_method);

    }

    @Test
    public void testLoadDataView_listPaymentMethods() {

        doNothing().when(mAddPaymentMethodActivity).setTitle(R.string.title_payment_methods);

        mAddPaymentMethodActivity.loadDataView();

        verify(mAddNewPaymentMethodButton).setVisibility(View.GONE);
        verify(mPaymentMethodsList).setAdapter((PaymentMethodAdapter) notNull());
        verify(mAddPaymentMethodActivity).setTitle(R.string.title_payment_methods);
    }

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int actualLayout = mAddPaymentMethodActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(0)));
        assertThat(actualLayout, is(R.layout.activity_payment_methods));
    }

    @Test
    public void testOnItemClick_launchPaymentMethodsByType() throws ArgumentException {

        AdapterView adapterView = mock(AdapterView.class);

        mockStatic(LaunchIntents.class);

        PowerMockito.doNothing().when(LaunchIntents.class);

        LaunchIntents.launchIntentClearTop(mAddPaymentMethodActivity, Class.class);


        for (TypePaymentMethodEnum typePaymentMethodEnum : TypePaymentMethodEnum.values()) {

            when(mView.getTag()).thenReturn(typePaymentMethodEnum);

            mAddPaymentMethodActivity.onItemClick(adapterView, mView, 0, 0);

        }

        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mAddPaymentMethodActivity, CardCreationActivity.class);
        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mAddPaymentMethodActivity, CashActivity.class);
        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mAddPaymentMethodActivity, FoodCouponsActivity.class);

        verify(mView, times(TypePaymentMethodEnum.values().length)).getTag();

    }

    @Test
    public void testAddListenerToView() {

        mAddPaymentMethodActivity.addListenersToViews();

        verify(mPaymentMethodsList).setOnItemClickListener(mAddPaymentMethodActivity);
    }

}
