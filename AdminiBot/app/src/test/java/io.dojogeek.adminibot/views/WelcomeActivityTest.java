package io.dojogeek.adminibot.views;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.WelcomeAdapter;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WelcomeActivity.class, Log.class})
public class WelcomeActivityTest {

    @Mock
    private ListView mItemsContainer;

    @Spy
    @InjectMocks
    private WelcomeActivity mActivity = new WelcomeActivity();

    @Test
    public void testWidgets_wereInstantiated() {

        ListView itemsContainer = mock(ListView.class);
        doReturn(itemsContainer).when(mActivity).findViewById(R.id.items_container);

        mActivity.instantiateViews();

        verify(mActivity).findViewById(R.id.items_container);

    }

    @Test
    public void testViewData_wasLoaded() throws Exception {

        doNothing().when(mActivity).setTitle(R.string.welcome_title);

        ArrayList items = mock(ArrayList.class);
        whenNew(ArrayList.class).withNoArguments().thenReturn(items);

        WelcomeAdapter adapter = mock(WelcomeAdapter.class);
        whenNew(WelcomeAdapter.class).withArguments(mActivity, items).thenReturn(adapter);

        mActivity.loadViewData();

        verify(mActivity).setTitle(R.string.welcome_title);
        verify(items).add(new Object[]{R.drawable.ic_cash, R.string.title1, R.string.description1, PaymentMethodsActivity.class});
        verify(items).add(new Object[]{R.drawable.ic_cash, R.string.title2, R.string.description2, CreditCardActivity.class});
        verify(mItemsContainer).setAdapter(adapter);

    }

    @Test
    public void testListenerForListView_wasSet() {

        mActivity.setListeners();

        verify(mItemsContainer).setOnItemClickListener(mActivity);

    }

    @Test
    public void testItemClick_optionOneSelected() throws Exception {

        Intent intent = mock(Intent.class);
        whenNew(Intent.class).withArguments(mActivity, PaymentMethodsActivity.class).thenReturn(intent);

        doNothing().when(mActivity).startActivity(intent);

        mActivity.onItemClick(mock(AdapterView.class), mock(View.class), 1, 0);

        verify(mActivity).startActivity(intent);

    }

    @Test
    public void testItemClick_optionTwoSelected() throws Exception {

        Intent intent = mock(Intent.class);
        whenNew(Intent.class).withArguments(mActivity, CreditCardActivity.class).thenReturn(intent);

        doNothing().when(mActivity).startActivity(intent);

        mActivity.onItemClick(mock(AdapterView.class), mock(View.class), 2, 0);

        verify(mActivity).startActivity(intent);

    }

    @Test
    public void testItemClick_defaultOption() {

        mockStatic(Log.class);
        when(Log.i(WelcomeActivity.TAG, "No actions for the selected option")).thenReturn(0);

        mActivity.onItemClick(mock(AdapterView.class), mock(View.class), 3, 0);

        verifyStatic();
        Log.i(WelcomeActivity.TAG, "No actions for the selected option");
    }

}
