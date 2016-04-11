package io.dojogeek.adminibot.views;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.PaymentMethodAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class AddPaymentMethodActivityTest {

    @Mock
    private ListView mPaymentMethodsList;

    @Mock
    private LinearLayout mContainerPaymentMethods;

    @Mock
    private LinearLayout mAddPaymentMethodOption;

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
        doReturn(mAddPaymentMethodOption).when(mAddPaymentMethodActivity).findViewById(R.id.add_payment_method_container);

        mAddPaymentMethodActivity.loadViews();

        verify(mAddPaymentMethodActivity).findViewById(R.id.payment_methods);
        verify(mAddPaymentMethodActivity).findViewById(R.id.add_payment_method_container);
    }

    @Test
    public void testLoadDataView_listPaymentMethods() {

        doReturn(mContainerPaymentMethods).when(mAddPaymentMethodActivity).findViewById(R.id.container_payment_methods);
        doReturn(mAddPaymentMethodOption).when(mAddPaymentMethodActivity).findViewById(R.id.add_payment_method_container);

        mAddPaymentMethodActivity.loadDataView();

        verify(mPaymentMethodsList).setAdapter((PaymentMethodAdapter) notNull());
        verify(mPaymentMethodsList).setVisibility(View.VISIBLE);
        verify(mAddPaymentMethodOption).setVisibility(View.GONE);
    }

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int actualLayout = mAddPaymentMethodActivity.getLayoutActivity();

        assertThat(actualLayout, is(not(0)));
        assertThat(actualLayout, is(R.layout.activity_payment_methods));
    }

}
