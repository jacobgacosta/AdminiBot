package io.dojogeek.adminibot.views;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dagger.AdminiBotComponent;
import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.adapters.SimpleItemAdapter;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.presenters.CashPresenter;
import io.dojogeek.adminibot.presenters.MyCashPresenter;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyCashActivity.class)
public class MyCashActivityTest {

    @Mock
    private RecyclerView mRecyclerView;

    @Mock
    private TextView mTotalCash;

    @Mock
    public MyCashPresenter mMyCashPresenter;

    @Mock
    public FloatingActionButton mSpend;

    @InjectMocks
    @Spy
    private MyCashActivity mCashActivity = new MyCashActivity();

    @Test
    public void testGetLayoutActivity_returnCorrectLayout() {

        int currentLayout = mCashActivity.getLayoutActivity();

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

    @Test
    public void testLoadDataView_buildToolbar() {

        Toolbar toolbarMock = mock(Toolbar.class);
        doReturn(toolbarMock).when(mCashActivity).findViewById(R.id.toolbar_with_image);

        doNothing().when(mCashActivity).setSupportActionBar(toolbarMock);

        ActionBar actionBarMock = mock(ActionBar.class);
        doReturn(actionBarMock).when(mCashActivity).getSupportActionBar();

        doNothing().when(actionBarMock).setDisplayHomeAsUpEnabled(true);

        ImageView imageViewMock = mock(ImageView.class);
//        doReturn(imageViewMock).when(toolbarMock).findViewById(R.id.toolbar_icon);

        Resources resources = mock(Resources.class);
        doReturn(resources).when(mCashActivity).getResources();

        Drawable drawableMock = mock(Drawable.class);
        doReturn(drawableMock).when(resources).getDrawable(R.drawable.ic_cash);

        TextView textViewMock = mock(TextView.class);
//        doReturn(textViewMock).when(toolbarMock).findViewById(R.id.toolbar_title);

        mCashActivity.loadDataView();

        verify(mCashActivity).findViewById(R.id.toolbar_with_image);
        verify(mCashActivity).setSupportActionBar(toolbarMock);
        verify(mCashActivity).getSupportActionBar();
        verify(actionBarMock).setDisplayHomeAsUpEnabled(true);
//        verify(toolbarMock).findViewById(R.id.toolbar_icon);
        verify(mCashActivity).getResources();
        verify(resources).getDrawable(R.drawable.ic_cash);
//        verify(toolbarMock).findViewById(R.id.toolbar_title);
        verify(imageViewMock).setImageDrawable(drawableMock);
        verify(textViewMock).setText(R.string.title_my_cash_activity);

    }

    @Test
    public void testAddListenerToView() {

    }

    @Test
    public void testLoadDataView_showCreditCardsList() throws Exception {

        PowerMockito.doNothing().when(mCashActivity, "setToolBarData");

        mCashActivity.loadDataView();

        verify(mMyCashPresenter).obtainCash();

    }

    @Test
    public void testUnnusedView_closeConnections() {

        mCashActivity.closeConnections();

        verify(mMyCashPresenter).unnusedView();
    }

    @Test
    public void test_listMyCash() throws Exception {

        LinearLayoutManager linearLayoutManagerMock = mock(LinearLayoutManager.class);
        whenNew(LinearLayoutManager.class).withArguments(mCashActivity).thenReturn(linearLayoutManagerMock);

        List<DtoSimpleAdapter> simpleAdapterList = new ArrayList<>();

        SimpleItemAdapter simpleItemAdapterMock = mock(SimpleItemAdapter.class);
        whenNew(SimpleItemAdapter.class).withArguments(mCashActivity, simpleAdapterList).
                thenReturn(simpleItemAdapterMock);

        mCashActivity.listMyCash(simpleAdapterList);

        verify(mRecyclerView).setHasFixedSize(true);
        verify(mRecyclerView).setLayoutManager(linearLayoutManagerMock);
        verify(mRecyclerView).setAdapter(simpleItemAdapterMock);
    }

    @Test
    public void testShowTotalCash_setValueToView() {

        BigDecimal totalCash = new BigDecimal(873.90);

        mCashActivity.showTotalCash(totalCash);

        verify(mTotalCash).setText(totalCash.toString());

    }

    @Test
    public void testOnCreateOptionsMenu_showOptionsMenu() throws Exception {

        Menu menuMock = mock(Menu.class);

        MenuInflater menuInflaterMock = mock(MenuInflater.class);
        doReturn(menuInflaterMock).when(mCashActivity).getMenuInflater();

        boolean isEnabled = mCashActivity.onCreateOptionsMenu(menuMock);

        assertTrue(isEnabled);
        verify(menuInflaterMock).inflate(R.menu.menu_payment_method, menuMock);
    }
}
