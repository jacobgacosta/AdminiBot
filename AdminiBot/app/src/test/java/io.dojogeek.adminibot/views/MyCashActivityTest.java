package io.dojogeek.adminibot.views;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.inject.Inject;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyCashActivity.class)
public class MyCashActivityTest {

    @Mock
    private RecyclerView mRecyclerView;

    @InjectMocks
    @Spy
    private MyCashActivity mCashActivity = new MyCashActivity();

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int currentLayout = mCashActivity.getLayoutActivity();

        assertThat(currentLayout, is(R.layout.activity_my_cash));
    }

    @Test
    public void testSetupComponent_injectActivity() throws Exception {

        AppComponent appComponent = mock(AppComponent.class);

        AdminiBotModule adminiBotModule = mock(AdminiBotModule.class);
        whenNew(AdminiBotModule.class).withArguments(mCashActivity).thenReturn(adminiBotModule);

        AdminiBotComponent adminiBotComponent = mock(AdminiBotComponent.class);
        when(appComponent.plus(adminiBotModule)).thenReturn(adminiBotComponent);

        mCashActivity.setupComponent(appComponent);

        verify(appComponent).plus(adminiBotModule);
        verify(adminiBotComponent).inject(mCashActivity);
    }

    @Test
    public void testLoadViews_setViews() {

        doReturn(mRecyclerView).when(mCashActivity).findViewById(R.id.my_recycler_view);

        mCashActivity.loadViews();

        verify(mCashActivity).findViewById(R.id.my_recycler_view);
    }

}
