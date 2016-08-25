package io.dojogeek.adminibot.views;

import android.widget.Button;

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
import io.dojogeek.adminibot.utils.LaunchIntents;

import static org.junit.Assert.assertEquals;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest({LaunchIntents.class})
public class AddNewPaymentMethodActivityTest {

    @Mock
    private Button mAddNewPaymentMethod;

    @InjectMocks
    @Spy
    private AddNewPaymentMethodActivity mAddNewPaymentMethodActivity = new AddNewPaymentMethodActivity();

    @Test
    public void testExtendBaseActivity() {

        assertTrue(mAddNewPaymentMethodActivity instanceof BaseActivity);

    }

    @Test
    public void testSetupComponent_injectActivity() {

        AppComponent appComponent = mock(AppComponent.class);
        AdminiBotComponent adminiBotComponent = mock(AdminiBotComponent.class);

        when(appComponent.plus((AdminiBotModule) notNull())).thenReturn(adminiBotComponent);

        mAddNewPaymentMethodActivity.setupComponent(appComponent);

        verify(appComponent).plus((AdminiBotModule) notNull());
        verify(adminiBotComponent).inject(mAddNewPaymentMethodActivity);
    }

    @Test
    public void  testGetLayout_returnCorrectLayout() {

        int actualLayout = mAddNewPaymentMethodActivity.getLayoutActivity();

        assertEquals(R.layout.activity_add_new_payment_method, actualLayout);

    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mAddNewPaymentMethod).when(mAddNewPaymentMethodActivity).
                findViewById(R.id.add_payment_method);

        mAddNewPaymentMethodActivity.loadViews();

        verify(mAddNewPaymentMethodActivity).findViewById(R.id.add_payment_method);
    }

    @Test
    public void testLoadDataView_setTitle() {

        doNothing().when(mAddNewPaymentMethodActivity).setTitle(R.string.title_activity_add_payment_method);

        mAddNewPaymentMethodActivity.loadDataView();

        verify(mAddNewPaymentMethodActivity).setTitle(R.string.title_activity_add_payment_method);

    }

    @Test
    public void testAddListenerToView() {

        mAddNewPaymentMethodActivity.addListenersToViews();

        verify(mAddNewPaymentMethod).setOnClickListener(mAddNewPaymentMethodActivity);

    }

    @Test
    public void testOnclick_AddNewPaymentMethod() {

        when(mAddNewPaymentMethod.getId()).thenReturn(R.id.add_payment_method);

        mockStatic(LaunchIntents.class);
        PowerMockito.doNothing().when(LaunchIntents.class);
        LaunchIntents.launchIntentClearTop(mAddNewPaymentMethodActivity, Class.class);

        mAddNewPaymentMethodActivity.onClick(mAddNewPaymentMethod);

        verify(mAddNewPaymentMethod).getId();

        verifyStatic(times(1));
        LaunchIntents.launchIntentClearTop(mAddNewPaymentMethodActivity, AddPaymentMethodActivity.class);
    }
}
